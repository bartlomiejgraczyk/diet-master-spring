package pl.tul.zzpj.dietmaster.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJdbcRepositories
@EnableJpaRepositories(basePackages = {
        "pl.tul.zzpj.dietmaster.account",
        "pl.tul.zzpj.dietmaster.diet",
        "pl.tul.zzpj.dietmaster.ingredient",
        "pl.tul.zzpj.dietmaster.meal",
        "pl.tul.zzpj.dietmaster.measurement",
        "pl.tul.zzpj.dietmaster.nutrient",
        "pl.tul.zzpj.dietmaster.registration.token"
})
public class JDBCConfig {
}
