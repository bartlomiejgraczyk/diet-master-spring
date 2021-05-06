package pl.tul.zzpj.dietmaster.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@Profile("prod")
public class ProdDatabaseConfig extends AbstractJdbcConfiguration {

    private static final String USERNAME = "xinqxtgupfnums";
    private static final String PASSWORD = "d8eb9f7389777534fed3c6c6512e40912ff16dca60360bed2d2fc66dda697914";
    private static final String PREFIX = "jdbc:postgresql://";
    private static final String HOST = "ec2-54-155-226-153.eu-west-1.compute.amazonaws.com";
    private static final String PORT = "5432";
    private static final String DB = "d3krudhfc0uus";

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        return builder
                .driverClassName("org.postgresql.Driver")
                .url(PREFIX + HOST + ":" + PORT + "/" + DB + "?user=" + USERNAME + "&password=" + PASSWORD)
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    @Bean
    public NamedParameterJdbcTemplate prodParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(this.dataSource());
    }
}
