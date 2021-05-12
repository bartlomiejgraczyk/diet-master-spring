package pl.tul.zzpj.dietmaster;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.tul.zzpj.dietmaster.nutrient.Nutrient;
import pl.tul.zzpj.dietmaster.nutrient.NutrientCategory;
import pl.tul.zzpj.dietmaster.nutrient.NutrientServiceImpl;

@SpringBootTest
class DietMasterApplicationTests {



    @Transactional
    public void insertWithQuery(Nutrient nutrient) {
//        entityManager.createNativeQuery("INSERT INTO nutrient (category, name) VALUES (?,?)")
//                .setParameter(1, nutrient.getCategory())
//                .setParameter(2, nutrient.getName())
//                .executeUpdate();
    }

    @Test
    void contextLoads() {
        Nutrient nutrient = new Nutrient("Calories", NutrientCategory.CALORIES);
        insertWithQuery(nutrient);
    }

}
