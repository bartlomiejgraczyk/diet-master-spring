package pl.tul.zzpj.dietmaster.logic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.IngredientCategory;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByCategory(IngredientCategory category);

    Ingredient findDistinctByNameAndCategory(String name, IngredientCategory category);

}
