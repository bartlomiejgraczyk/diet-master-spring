package pl.tul.zzpj.dietmaster.diet;

public interface DietService {
    void addDiet(CreateDietRequest diet);
    void updateDiet(UpdateDietRequest diet);
}
