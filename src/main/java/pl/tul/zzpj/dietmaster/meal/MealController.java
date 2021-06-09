package pl.tul.zzpj.dietmaster.meal;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.diet.CreateDietRequest;
import pl.tul.zzpj.dietmaster.diet.DietService;
import pl.tul.zzpj.dietmaster.diet.RequestDietMapper;
import pl.tul.zzpj.dietmaster.diet.UpdateDietRequest;
import pl.tul.zzpj.dietmaster.exception.AppBaseException;

@RestController
@RequestMapping(path = "meals")
@AllArgsConstructor
public class MealController {

    private final MealService mealService;

    @PutMapping
    public ResponseEntity<?> updateMeal(@RequestBody UpdateMealRequest updateMealRequest){
       try {
           mealService.updateMeal(updateMealRequest);
       } catch (AppBaseException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
       }
       return ResponseEntity.status(HttpStatus.OK).build();
    }

}
