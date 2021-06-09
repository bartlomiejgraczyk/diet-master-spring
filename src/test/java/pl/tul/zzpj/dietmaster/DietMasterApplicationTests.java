package pl.tul.zzpj.dietmaster;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.tul.zzpj.dietmaster.model.entities.Nutrient;
import pl.tul.zzpj.dietmaster.model.entities.enums.categorytype.NutrientCategory;

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
