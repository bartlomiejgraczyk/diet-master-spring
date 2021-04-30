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
        "pl.tul.zzpj.dietmaster.nutrient"
})
public class JDBCConfig extends AbstractJdbcConfiguration {

    private final static String password = "d8eb9f7389777534fed3c6c6512e40912ff16dca60360bed2d2fc66dda697914";
    private final static String username = "xinqxtgupfnums";
    private final static String postgres = "jdbc:postgresql://";
    private final static String host = "ec2-54-155-226-153.eu-west-1.compute.amazonaws.com";
    private final static String port = "5432";
    private final static String db = "d3krudhfc0uus";

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        return builder
                .driverClassName("org.postgresql.Driver")
                .url(postgres + host + ":" + port + "/" + db + "?user=" + username + "&password=" + password)
                .username(username)
                .password(password)
                .build();
        // TODO: fill database credentials
    }

}
