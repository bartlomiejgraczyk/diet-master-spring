package pl.tul.zzpj.dietmaster.diet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietServiceImpl implements DietService {
    
    private final DietRepository repository;

    @Autowired
    public DietServiceImpl(DietRepository repository) {
        this.repository = repository;
    }
}
