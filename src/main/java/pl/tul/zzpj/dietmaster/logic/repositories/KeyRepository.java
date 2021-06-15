package pl.tul.zzpj.dietmaster.logic.repositories;

import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.Key;

import java.util.Optional;

@Repository
public interface KeyRepository extends JpaRepository<Key, Long> {
    boolean existsKeyByDietitianAndKeyString(@NonNull Account dietitian, @NonNull @Length(min = 5, max = 15) String keyString);

    Optional<Key> findFirstByKeyStringAndDietitian(@NonNull @Length(min = 5, max = 15) String keyString, @NonNull Account dietitian);

}
