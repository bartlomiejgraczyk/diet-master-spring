package pl.tul.zzpj.dietmaster;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.tul.zzpj.dietmaster.ingredient.Ingredient;
import pl.tul.zzpj.dietmaster.ingredient.IngredientCategory;
import pl.tul.zzpj.dietmaster.ingredient.IngredientRepository;
import pl.tul.zzpj.dietmaster.nutrient.Nutrient;
import pl.tul.zzpj.dietmaster.nutrient.NutrientCategory;
import pl.tul.zzpj.dietmaster.nutrient.NutrientRepository;
import pl.tul.zzpj.dietmaster.nutrient.NutrientService;

@SpringBootApplication
public class DietMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DietMasterApplication.class, args);
	}

	/*@Bean
	CommandLineRunner init(NutrientRepository repository, IngredientRepository ingredientRepository) {
		return args -> {
			Nutrient nutrient = new Nutrient("Calories", NutrientCategory.CALORIES);
			Nutrient nutrient2 = new Nutrient("Calories2", NutrientCategory.CALORIES);
			nutrient.setDescription("TestCalories");
			nutrient2.setDescription("TestCalories2");
			repository.save(nutrient);
			repository.save(nutrient2);

			ingredientRepository.save(new Ingredient("Name1", IngredientCategory.BEAN));
			ingredientRepository.save(new Ingredient("Name2", IngredientCategory.NUT));
		};
	}*/
}
