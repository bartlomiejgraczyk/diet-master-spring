package pl.tul.zzpj.dietmaster.config;

import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Condition<?, ?> condition = Conditions.isNotNull();
        mapper.getConfiguration().setPropertyCondition(condition);

        return mapper;
    }

}
