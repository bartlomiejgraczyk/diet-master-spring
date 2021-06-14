package pl.tul.zzpj.dietmaster.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiData;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.bmi.BmiDataView;
import pl.tul.zzpj.dietmaster.logic.controllers.requests.registration.RegistrationRequest;
import pl.tul.zzpj.dietmaster.model.entities.Account;

@Mapper(componentModel = "spring")
public interface BmiDataMapper {
    
    @Mappings({
            @Mapping(target = "country", source = "SpatialDim"),
            @Mapping(target = "sex", source = "Dim1"),
            @Mapping(target = "percentageOver30Bmi", source = "NumericValue"),
            @Mapping(target = "low", source = "Low"),
            @Mapping(target = "high", source = "High")
    })
    BmiDataView bmiDataToView(BmiData request);
}
