package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.CreateKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.GetKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.measurement.GetMeasurementDto;
import pl.tul.zzpj.dietmaster.logic.repositories.KeyRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.AccountService;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.KeyService;
import pl.tul.zzpj.dietmaster.model.entities.Key;
import pl.tul.zzpj.dietmaster.model.entities.Measurement;
import pl.tul.zzpj.dietmaster.model.exception.KeyValidationException;
import pl.tul.zzpj.dietmaster.model.exception.NoPermissionKeyDeleteException;
import pl.tul.zzpj.dietmaster.model.exception.exists.KeyExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.KeyNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyServiceImpl implements KeyService {

    private final KeyRepository repository;
    private final AccountService accountService;
    private final ModelMapper modelMapper;

    @Autowired
    public KeyServiceImpl(KeyRepository repository, AccountService accountService, ModelMapper modelMapper) {
        this.repository = repository;
        this.accountService = accountService;
        this.modelMapper = modelMapper;

        modelMapper.typeMap(Key.class, GetKeyDto.class).addMappings(mapper -> {
            mapper.map( src -> src.getDietitian().getEmail(), GetKeyDto::setDietitian);
            mapper.map(Key::getId, GetKeyDto::setId);
        });
    }


    @Override
    public void generateKey(CreateKeyDto createKeyDto) throws UserNotFoundException, KeyExistsException, KeyValidationException {
        //var account = accountService.getCurrentUser();
        var account = accountService.loadUserByUsername("pae3@o2.pl"); //TEST
        var keyStr = createKeyDto.getKeyString();
        if (keyStr.length() < 5 || keyStr.length() > 15)
            throw new KeyValidationException(keyStr);
        if (repository.existsKeyByDietitianAndKeyString(account, keyStr))
            throw new KeyExistsException(account.getEmail());

        var key = new Key(account, keyStr, createKeyDto.getOneTime());
        repository.save(key);
    }


    @Override
    public void deleteKey(Long id) throws UserNotFoundException, KeyNotFoundException, NoPermissionKeyDeleteException {
        //var account = accountService.getCurrentUser();
        var account = accountService.loadUserByUsername("pae3@o2.pl"); //TEST
        var keyOpt = repository.findById(id);
        if (keyOpt.isEmpty())
            throw new KeyNotFoundException(id);
        var key = keyOpt.get();
        if (key.getDietitian() != account)
            throw new NoPermissionKeyDeleteException();
        repository.delete(key);
    }

    @Override
    public List<GetKeyDto> getMyKeys() throws UserNotFoundException {
        //var account = accountService.getCurrentUser();
        var account = accountService.loadUserByUsername("pae3@o2.pl"); //TEST

        var keys = repository.findAll();
        return keys.stream()
                .map(k -> modelMapper.map(k, GetKeyDto.class))
                .collect(Collectors.toList());
    }
}
