package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.model.entities.Ingredient;

public interface IngredientService {
    Ingredient findById(long id);
}
