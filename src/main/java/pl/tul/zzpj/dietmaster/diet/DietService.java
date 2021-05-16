package pl.tul.zzpj.dietmaster.diet;

import pl.tul.zzpj.dietmaster.exception.UserNotFoundException;

public interface DietService {
    void addDiet(CreateDietRequest diet) throws UserNotFoundException;
    void updateDiet(UpdateDietRequest diet);
}
