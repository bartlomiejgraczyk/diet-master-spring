package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.CreateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.GetIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.UpdateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.CreateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.IngredientService;
import pl.tul.zzpj.dietmaster.model.exception.NutrientDuplicateException;
import pl.tul.zzpj.dietmaster.model.exception.exists.IngredientExistsException;

import javax.ws.rs.NotFoundException;
import java.util.List;

@RestController
@RequestMapping(path = "ingredients")
@AllArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<GetIngredientDto>> getAllIngredients() {
        List<GetIngredientDto> ingredients = ingredientService.getAllIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping(path = "{category}")
    public ResponseEntity<?> getCategoryIngredients(@PathVariable String category) {
        try {
            List<GetIngredientDto> ingredients = ingredientService.getIngredientsOfCategory(category);
            return ResponseEntity.ok(ingredients);
        } catch (EnumConstantNotPresentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> updateIngredient(@RequestBody UpdateIngredientDto updateIngredientDto) {
        try {
            ingredientService.updateIngredient(updateIngredientDto);
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (IngredientExistsException | NutrientDuplicateException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
        return ResponseEntity.ok("Ingredient updated");
    }

    /*@PostMapping
    public ResponseEntity<String> addIngredient(@RequestBody CreateIngredientDto createIngredientDto) {
        try {
            ingredientService.createIngredient(createIngredientDto);
        } catch (IngredientExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Ingredient added");
    }

    @PostMapping(path = "{id}")
    public ResponseEntity<String> addNutrient(
        @PathVariable Long id,
        @RequestBody CreateIngredientNutritionDto nutritionDto
    ) {
        //TODO
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable Long id) {
        try {
            ingredientService.deleteIngredient(id);
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return ResponseEntity.ok("Ingredient deleted");
    }

    @DeleteMapping(path = "{id}/{name}/{category}")
    public ResponseEntity<String> deleteNutrient(
        @PathVariable Long id,
        @PathVariable String name,
        @PathVariable String category
    ) {
        try {
            ingredientService.deleteIngredient(id);
        } catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return ResponseEntity.ok("Ingredient deleted");
    }*/
}
