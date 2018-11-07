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
public class DatabaseConfig {
    @Autowired
    private Environment environment;

    @Bean
    @Primary
    public DataSource generalDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("app.datasource.general.url"));
        dataSource.setUsername(environment.getProperty("app.datasource.general.username"));
        dataSource.setPassword(environment.getProperty("app.datasource.general.password"));

        return dataSource;
    }

    @Bean
    public DataSource roomsDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("app.datasource.rooms.url"));
        dataSource.setUsername(environment.getProperty("app.datasource.rooms.username"));
        dataSource.setPassword(environment.getProperty("app.datasource.rooms.password"));

        return dataSource;
    }
}
