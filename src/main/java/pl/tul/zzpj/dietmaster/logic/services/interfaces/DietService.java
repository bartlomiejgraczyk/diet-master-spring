package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.diet.UpdateDietRequest;
import pl.tul.zzpj.dietmaster.model.exception.EnumNameNotEqualException;
import pl.tul.zzpj.dietmaster.model.exception.exists.DietExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.DietNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import java.util.List;

public interface DietService {

    void addDiet(Diet diet) throws UserNotFoundException, DietExistsException;

    void updateDiet(UpdateDietRequest diet) throws DietNotFoundException;

    void deleteDiet(Long id) throws DietNotFoundException;

    List<Diet> getAllAvailableDiets() throws UserNotFoundException;

    List<Diet> getDietsByType(String type) throws EnumNameNotEqualException;
}
