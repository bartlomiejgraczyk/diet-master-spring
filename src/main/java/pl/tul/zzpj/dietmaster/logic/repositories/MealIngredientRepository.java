package pl.tul.zzpj.dietmaster.logic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.model.entities.MealIngredient;

@Repository
public interface MealIngredientRepository extends JpaRepository<MealIngredient, Long> {

    boolean existsByIngredient(Ingredient ingredient);

}
