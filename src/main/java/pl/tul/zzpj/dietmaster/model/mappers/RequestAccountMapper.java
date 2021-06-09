package pl.tul.zzpj.dietmaster.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.RegistrationRequest;
import pl.tul.zzpj.dietmaster.model.entities.Account;

@Mapper(componentModel = "spring")
public interface RequestAccountMapper {
    
    @Mappings({
            @Mapping(target = "firstName", source = "request.firstName"),
            @Mapping(target = "lastName", source = "request.lastName"),
            @Mapping(target = "email", source = "request.email"),
            @Mapping(target = "password", source = "request.password")
    })
    Account requestToAccount(RegistrationRequest request);
}
