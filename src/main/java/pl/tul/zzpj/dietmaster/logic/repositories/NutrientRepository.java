package pl.tul.zzpj.dietmaster.logic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;

@Repository
public interface NutrientRepository extends JpaRepository<Nutrient, Long> {
}
