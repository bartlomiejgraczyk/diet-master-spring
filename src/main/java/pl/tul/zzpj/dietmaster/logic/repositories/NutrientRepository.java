package pl.tul.zzpj.dietmaster.logic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import java.util.List;

@Repository
public interface NutrientRepository extends JpaRepository<Nutrient, Long> {

    List<Nutrient> findByCategory(NutrientCategory category);

    Nutrient findDistinctByName(String name);
}
