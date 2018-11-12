package pl.abbl.reactchat.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PrimaryDatabaseConfig {
    @Autowired
    private Environment environment;

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.general")
    public DataSource generalDataSource(){
        return DataSourceBuilder.create().build();
    }
}
