package pl.tul.zzpj.dietmaster.diet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tul.zzpj.dietmaster.meal.Meal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {

}

