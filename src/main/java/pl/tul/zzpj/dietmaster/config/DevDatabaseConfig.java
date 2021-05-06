package pl.tul.zzpj.dietmaster.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@Profile("dev")
public class DevDatabaseConfig extends AbstractJdbcConfiguration {

    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    private static final String PREFIX = "jdbc:h2:mem";
    private static final String DB = "dietmaster";
    private static final String MODE = "PostgreSQL";
    
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        return builder
                .driverClassName("org.h2.Driver")
                .url(PREFIX + ":" + DB + ";MODE=" + MODE + ";DATABASE_TO_LOWER=TRUE")
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }
    
    @Bean
    public NamedParameterJdbcTemplate devParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(this.dataSource());
    }
}
