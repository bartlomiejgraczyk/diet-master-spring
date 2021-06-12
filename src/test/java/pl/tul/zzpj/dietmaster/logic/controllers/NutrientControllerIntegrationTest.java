package pl.tul.zzpj.dietmaster.logic.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.CreateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.repositories.NutrientRepository;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles("dev")
@SpringBootTest
class NutrientControllerIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private NutrientRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        repository.saveAll(
            Lists.newArrayList(
                new Nutrient("First Nutrient", "Desc_1", NutrientCategory.FAT),
                new Nutrient("Second Nutrient", "Desc_2", NutrientCategory.MINERAL),
                new Nutrient("Third Nutrient", "Desc_3", NutrientCategory.MINERAL)
            )
        );
    }

    @Test
    void getAllNutrients() throws Exception {
        mvc.perform(get("/nutrients"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name", is("First Nutrient")))
            .andExpect(jsonPath("$[1].description", is("Desc_2")))
            .andExpect(jsonPath("$[2].category", is("MINERAL")));
    }

    @Test
    void getCategoryNutrients() throws Exception {
        mvc.perform(get("/nutrients/MINERAL"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].name", is("Second Nutrient")))
            .andExpect(jsonPath("$[1].description", is("Desc_3")));
    }

    @Test
    void updateNutrient() throws Exception {
        Long id = repository.findAll().get(0).getId();
        UpdateNutrientDto okUpdate = new UpdateNutrientDto(id, null, "Desc_n", NutrientCategory.VITAMIN);
        UpdateNutrientDto wrongName = new UpdateNutrientDto(id, "Second Nutrient", "Desc_2", NutrientCategory.VITAMIN);
        UpdateNutrientDto wrongId = new UpdateNutrientDto(300L, null, "Desc_2", NutrientCategory.VITAMIN);

        String serializedOk = objectMapper.writeValueAsString(okUpdate);
        String serializedName = objectMapper.writeValueAsString(wrongName);
        String serializedId = objectMapper.writeValueAsString(wrongId);

        mvc.perform(put("/nutrients/")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(serializedOk))
            .andExpect(status().isOk())
            .andExpect(content().string("Nutrient updated"));

        mvc.perform(put("/nutrients")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(serializedName))
            .andExpect(status().isConflict())
            .andExpect(content().string("Nutrient Second Nutrient already exists in the database."));

        mvc.perform(put("/nutrients")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(serializedId))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Optional<Nutrient> nutrient = repository.findById(id);
        assertTrue(nutrient.isPresent());
        Nutrient extracted = nutrient.get();

        assertEquals(extracted.getName(), "First Nutrient");
        assertEquals(extracted.getDescription(), "Desc_n");
        assertEquals(extracted.getCategory(), NutrientCategory.VITAMIN);
    }

    @Test
    void addNutrient() throws Exception {
        CreateNutrientDto okCreate = new CreateNutrientDto("Fourth Nutrient", "Desc_4", NutrientCategory.PROTEIN);
        CreateNutrientDto duplicateName = new CreateNutrientDto("Second Nutrient", "Desc_4", NutrientCategory.PROTEIN);

        String serializedOk = objectMapper.writeValueAsString(okCreate);
        String serializedName = objectMapper.writeValueAsString(duplicateName);

        List<Nutrient> nutrients = repository.findAll();

        mvc.perform(post("/nutrients")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(serializedOk))
            .andExpect(status().isCreated())
            .andExpect(content().string("Nutrient added"));

        mvc.perform(post("/nutrients")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(serializedName))
            .andExpect(status().isConflict())
            .andExpect(content().string("Nutrient Second Nutrient already exists in the database."));

        List<Nutrient> addedNutrients = repository.findAll();
        Nutrient newest = addedNutrients.get(addedNutrients.size() - 1);

        assertTrue(nutrients.size() < addedNutrients.size());
        assertEquals(newest.getName(), "Fourth Nutrient");
        assertEquals(newest.getDescription(), "Desc_4");
        assertEquals(newest.getCategory(), NutrientCategory.PROTEIN);
    }

    @Test
    void deleteNutrient() throws Exception {
        mvc.perform(delete("/nutrients/300"))
            .andExpect(status().isNotFound());

        List<Nutrient> nutrients = repository.findAll();
        Long id = nutrients.get(0).getId();

        mvc.perform(delete("/nutrients/" + id))
            .andExpect(status().isOk())
            .andExpect(content().string("Nutrient deleted"));

        List<Nutrient> reducedNutrients = repository.findAll();
        assertTrue(nutrients.size() > reducedNutrients.size());
        assertTrue(reducedNutrients.stream().noneMatch(n -> n.getId().equals(id)));
    }
}