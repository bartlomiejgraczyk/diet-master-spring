package pl.tul.zzpj.dietmaster.config;

import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredient.UpdateIngredientDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.GetIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.ingredientnutrition.UpdateIngredientNutritionDto;
import pl.tul.zzpj.dietmaster.model.entities.Ingredient;
import pl.tul.zzpj.dietmaster.model.entities.IngredientNutrition;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Condition<?, ?> condition = Conditions.isNotNull();
        mapper.getConfiguration().setPropertyCondition(condition);

        addNutrientCategoryMapping(mapper);

        return mapper;
    }

    private void addNutrientCategoryMapping(ModelMapper modelMapper) {
        modelMapper.typeMap(IngredientNutrition.class, GetIngredientNutritionDto.class)
            .addMappings(mapper -> mapper.map(
                in -> in.getNutrient().getCategory(),
                GetIngredientNutritionDto::setCategory)
            );
    }
}
