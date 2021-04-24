package pl.tul.zzpj.dietmaster.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJdbcRepositories
@EnableJpaRepositories(basePackages = {
        "pl.tul.zzpj.dietmaster.account", 
        "pl.tul.zzpj.dietmaster.diet", 
        "pl.tul.zzpj.dietmaster.ingredient",
        "pl.tul.zzpj.dietmaster.meal"
})
public class JDBCConfig extends AbstractJdbcConfiguration {

    @Bean
    public DataSource dataSource(){
        DataSourceBuilder builder = DataSourceBuilder.create();
        return builder
                .driverClassName("org.postgresql.Driver")
                .url("")
                .username("")
                .password("")
                .build();
        // TODO: fill database credentials
    }



}
