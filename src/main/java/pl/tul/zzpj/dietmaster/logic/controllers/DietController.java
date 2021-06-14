package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.diet.CreateDietRequest;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.DietService;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.exception.EnumNameNotEqualException;
import pl.tul.zzpj.dietmaster.model.mappers.RequestDietMapper;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.diet.UpdateDietRequest;
import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "diets")
@AllArgsConstructor
public class DietController {

    private final DietService dietService;
    private final RequestDietMapper mapper;

    @PostMapping
    public ResponseEntity<?> addDiet(@RequestBody CreateDietRequest createDietRequest) {
        try {
            Diet newDiet = mapper.newDietFromDto(createDietRequest);
            dietService.addDiet(newDiet);
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Diet created!");
    }

    @GetMapping//(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllAvailableDiets() {
        try {
            List<Diet> diets = dietService.getAllAvailableDiets();
            return ResponseEntity.ok(diets.stream().map(mapper::dietDTOFromEntity).collect(Collectors.toList()));
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
    }

    @GetMapping(path = "type/{type}")
    public ResponseEntity<?> getDietsByType(@PathVariable String type) {
        try {
            return ResponseEntity.ok(dietService.getDietsByType(type).stream().map(mapper::dietDTOFromEntity).collect(Collectors.toList()));
        } catch (EnumNameNotEqualException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> updateDiet(@RequestBody UpdateDietRequest updateDietRequest) {
        try {
            dietService.updateDiet(updateDietRequest);
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Diet updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiet(@PathVariable Long id) {
        try {
            dietService.deleteDiet(id);
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Diet deleted!");
    }
}
