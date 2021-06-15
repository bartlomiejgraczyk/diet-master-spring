package pl.tul.zzpj.dietmaster.logic.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.CreateKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.GetKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.UseKeyDto;
import pl.tul.zzpj.dietmaster.logic.repositories.DietitianClientRepository;
import pl.tul.zzpj.dietmaster.logic.repositories.KeyRepository;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.AccountService;
import pl.tul.zzpj.dietmaster.logic.services.interfaces.KeyService;
import pl.tul.zzpj.dietmaster.model.entities.DietitianClient;
import pl.tul.zzpj.dietmaster.model.entities.Key;
import pl.tul.zzpj.dietmaster.model.exception.InvalidDietitianException;
import pl.tul.zzpj.dietmaster.model.exception.KeyValidationException;
import pl.tul.zzpj.dietmaster.model.exception.NoPermissionKeyDeleteException;
import pl.tul.zzpj.dietmaster.model.exception.exists.KeyExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.ClientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.KeyNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyServiceImpl implements KeyService {

    private final KeyRepository repository;
    private final DietitianClientRepository clientRepository;
    private final AccountService accountService;
    private final ModelMapper modelMapper;


    @Autowired
    public KeyServiceImpl(KeyRepository repository, DietitianClientRepository clientRepository, AccountService accountService, ModelMapper modelMapper) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.accountService = accountService;
        this.modelMapper = modelMapper;

        modelMapper.typeMap(Key.class, GetKeyDto.class).addMappings(mapper ->
                mapper.map(src -> src.getDietitian().getEmail(), GetKeyDto::setDietitian));
    }


    @Override
    public void generateKey(CreateKeyDto createKeyDto) throws UserNotFoundException, KeyExistsException, KeyValidationException {
        var account = accountService.getCurrentUser();
        var keyStr = createKeyDto.getKeyString();
        if (keyStr.length() < 5 || keyStr.length() > 15)
            throw new KeyValidationException(keyStr);
        if (repository.existsKeyByDietitianAndKeyString(account, keyStr))
            throw new KeyExistsException(createKeyDto.getKeyString());

        var key = new Key(account, keyStr, createKeyDto.getOneTime());
        repository.save(key);
    }


    @Override
    public void deleteKey(Long id) throws UserNotFoundException, KeyNotFoundException, NoPermissionKeyDeleteException {
        var account = accountService.getCurrentUser();
        var keyOpt = repository.findById(id);
        if (keyOpt.isEmpty())
            throw new KeyNotFoundException(id);
        var key = keyOpt.get();
        if (key.getDietitian() != account)
            throw new NoPermissionKeyDeleteException();
        repository.delete(key);
    }

    @Override
    public void useKey(UseKeyDto useKeyDto) throws UserNotFoundException, KeyNotFoundException, InvalidDietitianException {
        var client = accountService.getCurrentUser();

        var dietitian = accountService.loadUserByUsername(useKeyDto.getDietitian());

        if (client == dietitian)
            throw new InvalidDietitianException(dietitian.getEmail());

        var keyOptional = repository.findFirstByKeyStringAndDietitian(useKeyDto.getKeyString(), dietitian);

        if (keyOptional.isEmpty())
            throw new KeyNotFoundException(useKeyDto.getKeyString());
        var key = keyOptional.get();

        var dietitianClient = new DietitianClient(dietitian, client);
        clientRepository.save(dietitianClient);

        if (key.getOneTime())
            repository.delete(key);
    }

    @Override
    public List<GetKeyDto> getMyKeys() throws UserNotFoundException {
        var account = accountService.getCurrentUser();
        var keys = repository.findAllByDietitian(account);

        return keys.stream()
                .map(k -> modelMapper.map(k, GetKeyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteDietitian(String dietitianMail) throws UserNotFoundException, ClientNotFoundException {
        var client = accountService.getCurrentUser();

        var dietitian = accountService.loadUserByUsername(dietitianMail);

        var dClientOpt = clientRepository.findFirstByDietitianAndClient(dietitian, client);

        if (dClientOpt.isEmpty())
            throw new ClientNotFoundException(dietitianMail);

        clientRepository.delete(dClientOpt.get());
    }
}
