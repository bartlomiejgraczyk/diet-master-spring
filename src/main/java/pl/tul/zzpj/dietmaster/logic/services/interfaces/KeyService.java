package pl.tul.zzpj.dietmaster.logic.services.interfaces;

import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.CreateKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.GetKeyDto;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.key.UseKeyDto;
import pl.tul.zzpj.dietmaster.model.exception.InvalidDietitianException;
import pl.tul.zzpj.dietmaster.model.exception.KeyValidationException;
import pl.tul.zzpj.dietmaster.model.exception.NoPermissionKeyDeleteException;
import pl.tul.zzpj.dietmaster.model.exception.exists.KeyExistsException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.ClientNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.KeyNotFoundException;
import pl.tul.zzpj.dietmaster.model.exception.notfound.UserNotFoundException;

import java.util.List;

public interface KeyService {
    void generateKey(CreateKeyDto createKeyDto) throws UserNotFoundException, KeyExistsException, KeyValidationException;

    void deleteKey(Long id) throws UserNotFoundException, KeyNotFoundException, KeyValidationException, NoPermissionKeyDeleteException;

    void useKey(UseKeyDto key) throws UserNotFoundException, KeyNotFoundException, InvalidDietitianException;

    List<GetKeyDto> getMyKeys() throws UserNotFoundException;

    void deleteDietitian(String dietitian) throws UserNotFoundException, ClientNotFoundException;
}
