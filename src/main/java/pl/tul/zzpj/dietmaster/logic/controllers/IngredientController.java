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
import pl.tul.zzpj.dietmaster.model.exception.notfound.IngredientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.NutrientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.used.IngredientUsedInMealException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
        } catch (IngredientNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (IngredientExistsException | NutrientDuplicateException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
        return ResponseEntity.ok("Ingredient updated");
    }

    @PostMapping
    public ResponseEntity<String> addIngredient(@RequestBody CreateIngredientDto createIngredientDto) {
        try {
            ingredientService.createIngredient(createIngredientDto);
        } catch (IngredientExistsException | NutrientDuplicateException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch (NutrientNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (ConstraintViolationException exception) {
            return ResponseEntity.badRequest().body(unwrapMessage(exception));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Ingredient added");
    }

    @PostMapping(path = "{id}")
    public ResponseEntity<String> addNutrient(
        @PathVariable Long id,
        @RequestBody CreateIngredientNutritionDto nutritionDto
    ) {
        try {
            ingredientService.createNutrient(nutritionDto, id);
        } catch (IngredientNotFoundException | NutrientNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (ConstraintViolationException exception) {
            return ResponseEntity.badRequest().body(unwrapMessage(exception));
        }
        return ResponseEntity.ok("Nutrient added to ingredient");
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable Long id) {
        try {
            ingredientService.deleteIngredient(id);
        } catch (IngredientNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        } catch (IngredientUsedInMealException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        }
        return ResponseEntity.ok("Ingredient deleted");
    }

    @DeleteMapping(path = "{id}/{name}")
    public ResponseEntity<String> deleteNutrient(@PathVariable Long id, @PathVariable String name) {
        try {
            ingredientService.deleteNutrient(id, name);
        } catch (IngredientNotFoundException | NutrientNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return ResponseEntity.ok("Nutrient of ingredient deleted");
    }

    private String unwrapMessage(ConstraintViolationException exception) {
        Object violation = exception.getConstraintViolations().toArray()[0];
        return ((ConstraintViolation<?>) violation).getMessage();
    }
}
