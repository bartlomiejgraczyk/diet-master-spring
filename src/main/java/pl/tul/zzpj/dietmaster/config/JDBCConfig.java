package pl.tul.zzpj.dietmaster.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJdbcRepositories
@EnableJpaRepositories(basePackages = {"pl.tul.zzpj.dietmaster.logic.repositories"})
public class JDBCConfig {
}
