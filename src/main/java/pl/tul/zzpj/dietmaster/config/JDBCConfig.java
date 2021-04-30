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
        "pl.tul.zzpj.dietmaster.meal",
        "pl.tul.zzpj.dietmaster.measurement",
        "pl.tul.zzpj.dietmaster.nutrient",
        "pl.tul.zzpj.dietmaster.registration.token"
})
public class JDBCConfig extends AbstractJdbcConfiguration {

    @Bean
    public DataSource dataSource(){
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        return builder
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://xinqxtgupfnums:d8eb9f7389777534fed3c6c6512e40912ff16dca60360bed2d2fc66dda697914@ec2-54-155-226-153.eu-west-1.compute.amazonaws.com:5432/d3krudhfc0uus")
                .username("xinqxtgupfnums")
                .password("d8eb9f7389777534fed3c6c6512e40912ff16dca60360bed2d2fc66dda697914")
                .build();
        // TODO: fill database credentials
    }



}
