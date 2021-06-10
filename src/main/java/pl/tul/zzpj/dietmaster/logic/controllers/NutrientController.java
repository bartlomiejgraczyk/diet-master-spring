package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.CreateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.GetNutrientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.nutrient.UpdateNutrientDto;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.NutrientService;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.exception.exists.NutrientExistsException;

import javax.ws.rs.NotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "nutrients")
@AllArgsConstructor
public class NutrientController {

    private final NutrientService nutrientService;

    @GetMapping
    public ResponseEntity<List<GetNutrientDto>> getAllNutrients(){
        List<GetNutrientDto> nutrients = nutrientService.getAllNutrients();
        return new ResponseEntity<>(nutrients, HttpStatus.OK);
    }

    @GetMapping(path = "/{category}")
    public ResponseEntity<?> getCategoryNutrients(@PathVariable String category){
        try {
            List<GetNutrientDto> nutrients = nutrientService.getNutrientsOfCategory(category);
            return ResponseEntity.ok(nutrients);
        } catch (EnumConstantNotPresentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> updateNutrient(@RequestBody UpdateNutrientDto updateNutrientDto) {
        try {
            nutrientService.updateNutrient(updateNutrientDto);
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (NutrientExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
        return ResponseEntity.ok("Nutrient updated");
    }

    @PostMapping
    public ResponseEntity<String> addNutrient(@RequestBody CreateNutrientDto createNutrientDto) {
        try {
            nutrientService.createNutrient(createNutrientDto);
        } catch (NutrientExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Nutrient added");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteNutrient(@PathVariable Long id) {
        try {
            nutrientService.deleteNutrient(id);
        }catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return ResponseEntity.ok("Nutrient deleted");
    }
}
