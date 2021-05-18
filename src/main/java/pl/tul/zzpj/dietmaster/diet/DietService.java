package pl.tul.zzpj.dietmaster.diet;

import pl.tul.zzpj.dietmaster.exception.DietExistsException;
import pl.tul.zzpj.dietmaster.exception.DietNotFoundException;
import pl.tul.zzpj.dietmaster.exception.UserNotFoundException;

public interface DietService {
    void addDiet(Diet diet) throws UserNotFoundException, DietExistsException;
    void updateDiet(UpdateDietRequest diet) throws DietExistsException;
    void deleteDiet(Long id) throws DietNotFoundException;
}
