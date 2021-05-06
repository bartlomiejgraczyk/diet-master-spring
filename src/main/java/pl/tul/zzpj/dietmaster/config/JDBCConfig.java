package pl.tul.zzpj.dietmaster.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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

    private static final String PASSWORD = "***REMOVED***";
    private static final String USERNAME = "***REMOVED***";
    private static final String PREFIX = "jdbc:postgresql://";
    private static final String HOST = "***REMOVED***";
    private static final String PORT = "5432";
    private static final String DB = "***REMOVED***";

    @Bean
    @Profile("dev")
    public DataSource developmentDataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        return builder
                .driverClassName("org.postgresql.Driver")
                .url(PREFIX + HOST + ":" + PORT + "/" + DB + "?user=" + USERNAME + "&password=" + PASSWORD)
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    @Bean
    @Profile("prod")
    public DataSource productionDataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        
        String url = System.getenv("JDBC_DATABASE_URL");
        String username = System.getenv("JDBC_DATABASE_USERNAME");
        String password = System.getenv("JDBC_DATABASE_PASSWORD");

        return builder
                .driverClassName("org.postgresql.Driver")
                .url(url)
                .username(username)
                .password(password)
                .build();
    }
    
    @Bean
    @Profile("dev")
    public NamedParameterJdbcTemplate devParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(this.developmentDataSource());
    }

    @Bean
    @Profile("prod")
    public NamedParameterJdbcTemplate prodParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(this.productionDataSource());
    }
}
