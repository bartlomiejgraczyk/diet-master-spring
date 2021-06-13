package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.AccountService;
import pl.tul.zzpj.dietmaster.model.entities.Account;
import pl.tul.zzpj.dietmaster.model.entities.Diet;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;
import pl.tul.zzpj.dietmaster.model.mappers.RequestDietMapper;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.diet.UpdateDietRequest;
import pl.tul.zzpj.dietmaster.model.exception.exists.DietExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.DietNotFoundException;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.DietService;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.MealService;
import pl.tul.zzpj.dietmaster.logic.repositories.DietRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class DietServiceImpl implements DietService {
    
    private final DietRepository dietRepository;
    private final MealService mealService;
    private final RequestDietMapper mapper;
    private final AccountService accountService;

    @Autowired
    public DietServiceImpl(DietRepository dietRepository, MealService mealService, RequestDietMapper mapper, AccountService accountService) {
        this.dietRepository = dietRepository;
        this.mealService = mealService;
        this.mapper = mapper;
        this.accountService = accountService;
    }

    @Override
    public void addDiet(Diet diet) throws DietExistsException {
        if (dietWithTitleExists(diet))
            throw new DietExistsException(diet.getName());

        dietRepository.save(diet);
        mealService.addMeals(diet.getMeals(), diet);

    }

    @Override
    public void updateDiet(UpdateDietRequest dietDTO)throws DietNotFoundException {
        if (!dietRepository.existsById(dietDTO.getId()))
            throw new DietNotFoundException(dietDTO.getId());
        Diet diet = dietRepository.findDietById(dietDTO.getId());
        mapper.updateDietFromDto(dietDTO, diet);
        dietRepository.save(diet);

    }

    @Override
    public void deleteDiet(Long id) throws DietNotFoundException {
        if (!dietRepository.existsById(id))
            throw new DietNotFoundException(id);

        Diet diet = dietRepository.getOne(id);
        dietRepository.delete(diet);
    }

    @Override
    public List<Diet> getAllAvailableDiets() throws UserNotFoundException {
        Account user = accountService.getCurrentUser();
        HashSet<Diet> diets = new HashSet<>();
        diets.addAll(dietRepository.findDietsByAuthor(user));
        diets.addAll(dietRepository.findAllPublicDiets());
        diets.addAll(dietRepository.findAllMySubscribedDiets(user));
        return new ArrayList<>(diets);
    }

    @Override
    public List<Diet> getDietsByType(int type) {
        return dietRepository.findDietsByType(type);
    }

    private boolean dietWithTitleExists(Diet diet){
       return dietRepository.findAll().stream()
               .anyMatch(d -> d.getAuthor().getEmail().equals(diet.getAuthor().getEmail()) &&
                         d.getName().equals(diet.getName()));
    }
}
