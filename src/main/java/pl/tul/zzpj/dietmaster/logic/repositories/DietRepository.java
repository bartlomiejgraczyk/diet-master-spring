package pl.tul.zzpj.dietmaster.logic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.model.entities.Diet;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {
    Diet findDietById(long id);
}

