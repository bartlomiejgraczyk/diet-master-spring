package pl.tul.zzpj.dietmaster.logic.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.entities.Meal;
import pl.tul.zzpj.dietmaster.model.exception.AppBaseException;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.meal.UpdateMealRequest;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MealService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "meals")
@AllArgsConstructor
public class MealController {

    private final MealService mealService;

    @PutMapping
    public ResponseEntity<?> updateMeal(@RequestBody UpdateMealRequest updateMealRequest) {
        try {
            mealService.updateMeal(updateMealRequest);
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(path = "/diet/{id}")
    public ResponseEntity<?> getMealsByDietId(@PathVariable long id) {
        try {
            List<Meal> meals = mealService.getMealsByDietId(id);
            return ResponseEntity.ok(meals);
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getMealById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(mealService.getMealById(id));
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getCode());
        }

    }

}
