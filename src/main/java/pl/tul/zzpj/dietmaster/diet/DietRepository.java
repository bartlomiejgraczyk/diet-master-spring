package pl.tul.zzpj.dietmaster.diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {
    Diet findDietById(long id);
}

