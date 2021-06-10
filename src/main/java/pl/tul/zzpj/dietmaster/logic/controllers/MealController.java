package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.UpdateMealRequest;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MealService;

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
