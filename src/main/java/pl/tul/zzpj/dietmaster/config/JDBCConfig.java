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

    private static final String PASSWORD = "";
    private static final String USERNAME = "";
    private static final String PREFIX = "jdbc:postgresql://";
    private static final String HOST = "";
    private static final String PORT = "5432";
    private static final String DB = "";

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
//        return builder
//                .driverClassName("org.postgresql.Driver")
//                .url(PREFIX + HOST + ":" + PORT + "/" + DB + "?user=" + USERNAME + "&password=" + PASSWORD)
//                .username(USERNAME)
//                .password(PASSWORD)
//                .build();

        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String username = System.getenv("JDBC_DATABASE_USERNAME");
        String password = System.getenv("JDBC_DATABASE_PASSWORD");
        
        return builder
                .driverClassName("org.postgresql.Driver")
                .url(dbUrl)
                .username(username)
                .password(password)
                .build();
    }
}
