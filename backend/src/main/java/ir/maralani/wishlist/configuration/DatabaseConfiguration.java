package ir.maralani.wishlist.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ir.maralani.wishlist.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Configuration for default system datasource with support for JDBC connection pools.
 * This is the main and only datasource which is being used by multiple services across the system.
 * <p>
 * Special care is taken to ensure that multiple vendor-free methods can be used to provide
 * datasource information (along with default spring.* namespace) in runtime while using in production profile.
 * In production mode, datasource parameters which includes URI, name, username and password can be read from
 * multiple sources, like system property, environment variables, command-line arguments and other mechanisms
 * which are  described in great detail in relevant parts of the Spring Boot documentations.
 * <p>
 * Also, a high-performance connection pool is wired up to the configured datasource.
 */
@Configuration
@EnableJpaRepositories("ir.maralani.wishlist.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
@EnableSpringDataWebSupport
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    /**
     * Application context
     */
    private final ApplicationContext context;

    /**
     * @param context Application context
     * @see #context
     */
    @Autowired
    public DatabaseConfiguration(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Configures a pooled {@link DataSource} straight from the development properties file.
     * This is used only in development profile.
     *
     * @param dataSourceProperties provided datasource information
     * @return A configured {@code DataSource}
     */
    @Profile(Constants.Profiles.DEV)
    @Bean(destroyMethod = "close")
    public DataSource getDevDataSource(DataSourceProperties dataSourceProperties) {
        log.debug("Configuring Development Datasource...");

        return getManagedPoolDataSource(dataSourceProperties.getUrl(),
                dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
    }

    /**
     * Configures a pooled datasource using provided parameters from the {@link Environment}.
     * These parameters might come from different sources.
     *
     * @param dataSourceProperties Datasource properties
     * @return A configured {@code DataSource}
     */
    @Profile(Constants.Profiles.PROD)
    @Bean(destroyMethod = "close")
    public DataSource getProdDataSource(DataSourceProperties dataSourceProperties) {
        final String protocolSegment = "jdbc:postgresql://";

        log.debug("Configuring Production Datasource...");

        String url = dataSourceProperties.getUrl() != null ?
                dataSourceProperties.getUrl() : context.getEnvironment().getProperty("database.url");
        String username = dataSourceProperties.getUsername() != null ?
                dataSourceProperties.getUsername() : context.getEnvironment().getProperty("database.username");
        String password = dataSourceProperties.getPassword() != null ?
                dataSourceProperties.getPassword() : context.getEnvironment().getProperty("database.password");

        if (url == null) {
            log.error("Expected database parameters are missing during startup.");
            log.error(Constants.Validation.STARTUP_MISSING_PROPERTIES_MESSAGE);
            SpringApplication.exit(context, (ExitCodeGenerator) () -> 1);
        } else if (!url.toLowerCase().startsWith(protocolSegment)) {
            // We should be as resilient as possible,
            // don't add driver specific url section if it already has it.
            url = protocolSegment + url;
        }

        return getManagedPoolDataSource(url, username, password);
    }

    /**
     * Wraps a {@link DataSource} inside a HikariCP instance to provide pooling capabilities.
     *
     * @param url      URL of the database
     * @param username username of the database
     * @param password password of the database
     * @return Pooled {@code Datasource}
     */
    private DataSource getManagedPoolDataSource(String url, String username, String password) {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(url);

        // Hikari doesn't like NULL values for username and password.
        hikariConfig.setUsername(username != null ? username : "");
        hikariConfig.setPassword(password != null ? password : "");

        return new HikariDataSource(hikariConfig);
    }
}
