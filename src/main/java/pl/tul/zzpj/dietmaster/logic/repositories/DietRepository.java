package pl.tul.zzpj.dietmaster.logic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.entities.enums.acceslevels.DietAccessLevelTier;
import pl.tul.zzpj.dietmaster.model.entities.enums.types.DietType;

import java.util.List;


@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {
    Diet findDietById(long id);
    List<Diet> findDietsByAuthor(Account author);

    //accessLevel = PUBLIC
    @Query("SELECT d FROM Diet d WHERE d.accessLevel = ?1 ")
    List<Diet> findAllPublicDiets(DietAccessLevelTier level);
    //access level = SUBSCRIBED
    @Query("SELECT d FROM Diet d WHERE d.accessLevel = ?2 AND d.author " +
            "IN (SELECT dc.dietitian FROM DietitianClient dc WHERE dc.client = ?1)")
    List<Diet> findAllMySubscribedDiets(Account user, DietAccessLevelTier level);

    List<Diet> findDietsByType(DietType type);
}

