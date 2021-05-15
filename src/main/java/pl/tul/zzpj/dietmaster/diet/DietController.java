package pl.tul.zzpj.dietmaster.diet;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tul.zzpj.dietmaster.account.AccountService;
import pl.tul.zzpj.dietmaster.diet.dietset.DietSet;
import pl.tul.zzpj.dietmaster.exception.AppBaseException;
import pl.tul.zzpj.dietmaster.exception.UserNotFoundException;
import pl.tul.zzpj.dietmaster.ingredient.IngredientService;
import pl.tul.zzpj.dietmaster.meal.Meal;
import pl.tul.zzpj.dietmaster.meal.MealService;
import pl.tul.zzpj.dietmaster.meal.MealType;
import pl.tul.zzpj.dietmaster.registration.RequestAccountMapper;

import java.util.ArrayList;
import java.util.HashSet;

@RestController
@RequestMapping(path = "diets")
@AllArgsConstructor
public class DietController {

    private final DietService dietService;
    private final AccountService acService;
    private final IngredientService ingredientService;
    private final RequestDietMapper mapper;

    @PostMapping
    public ResponseEntity<?> addDiet(@RequestBody CreateDietRequest createDietRequest)  {
        try {
            var ac = acService.loadUserByUsername(createDietRequest.getAuthor());
            dietService.addDiet(mapper.requestToDiet(createDietRequest, ac, ingredientService));
        } catch (AppBaseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getCode());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
