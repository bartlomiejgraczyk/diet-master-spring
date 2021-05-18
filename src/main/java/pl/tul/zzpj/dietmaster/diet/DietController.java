package pl.tul.zzpj.dietmaster.diet;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tul.zzpj.dietmaster.account.AccountService;
import pl.tul.zzpj.dietmaster.diet.dietset.DietSet;
import pl.tul.zzpj.dietmaster.exception.AppBaseException;
import pl.tul.zzpj.dietmaster.exception.DietNotFoundException;
import pl.tul.zzpj.dietmaster.exception.UserNotFoundException;
import pl.tul.zzpj.dietmaster.ingredient.IngredientService;
import pl.tul.zzpj.dietmaster.meal.Meal;
import pl.tul.zzpj.dietmaster.meal.MealService;
import pl.tul.zzpj.dietmaster.meal.MealType;
import pl.tul.zzpj.dietmaster.registration.RequestAccountMapper;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;

@RestController
@RequestMapping(path = "diets")
@AllArgsConstructor
public class DietController {

    private final DietService dietService;
    private final RequestDietMapper mapper;

    @PostMapping
    public ResponseEntity<?> addDiet(@RequestBody CreateDietRequest createDietRequest)  {
        try {
            dietService.addDiet(mapper.newDietFromDto(createDietRequest));
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Diet created!");
    }

    @PutMapping
    public ResponseEntity<?> updateDiet(@RequestBody UpdateDietRequest updateDietRequest){
       try {
           dietService.updateDiet(updateDietRequest);
       } catch (AppBaseException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
       }
       return ResponseEntity.status(HttpStatus.OK).build();
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
