package pl.tul.zzpj.dietmaster.logic.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.DietitianClient;

import java.util.Optional;

@Repository
public interface DietitianClientRepository extends JpaRepository<DietitianClient, Long> {
    Optional<DietitianClient> findFirstByDietitianAndClient(@NonNull Account dietitian, @NonNull Account client);
}
