package pl.tul.zzpj.dietmaster.meal;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MealServiceImpl implements MealService {
    
    private MealRepository repository;
}
