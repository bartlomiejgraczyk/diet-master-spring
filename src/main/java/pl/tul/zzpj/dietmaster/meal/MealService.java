package pl.tul.zzpj.dietmaster.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealService {
    
    @Autowired
    private MealRepository repository;
}
