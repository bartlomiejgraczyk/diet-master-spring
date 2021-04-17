package pl.tul.zzpj.dietmaster.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietService {
    
    @Autowired
    private DietRepository repository;
}
