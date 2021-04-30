package pl.tul.zzpj.dietmaster.registration;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.tul.zzpj.dietmaster.account.Account;

@Mapper(componentModel = "spring")
public interface RequestAccountMapper {
    
    Account requestToAccount(RegistrationRequest request);
}
