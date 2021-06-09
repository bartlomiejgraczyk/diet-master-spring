package pl.tul.zzpj.dietmaster.ingredient.mealingredient;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tul.zzpj.dietmaster.exception.AppBaseException;
import pl.tul.zzpj.dietmaster.meal.MealService;
import pl.tul.zzpj.dietmaster.meal.UpdateMealRequest;

@RestController
@RequestMapping(path = "mealingredients")
@AllArgsConstructor
public class MealIngredientController {

    private final MealIngredientService mealIngredientService;

    @PutMapping
    public ResponseEntity<?> updateMealIngredient(@RequestBody UpdateMealIngredientRequest updateMealIngredientRequest){
       try {
           mealIngredientService.update(updateMealIngredientRequest);
       } catch (AppBaseException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
       }
       return ResponseEntity.status(HttpStatus.OK).build();
    }

}
