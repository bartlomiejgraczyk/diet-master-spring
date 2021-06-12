package pl.tul.zzpj.dietmaster.logic.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.Measurement;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categories.NutrientCategory;

import java.sql.Date;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    List<Measurement> findByClient_Email(String clientEmail);

    boolean existsMeasurementByClientAndDate(@NonNull Account client, @NonNull Date date);
}
