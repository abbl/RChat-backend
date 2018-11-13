package pl.abbl.reactchat.database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class ChatRoomContentDatabase {
    @Autowired
    private Environment environment;

    @Bean()
    @ConfigurationProperties("app.datasource.rooms")
    public DataSource chatRoomContentDataSource() {
        return DataSourceBuilder.create().build();
    }
}

/*
DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("app.datasource.rooms.url"));
        dataSource.setUsername(environment.getProperty("app.datasource.rooms.username"));
        dataSource.setPassword(environment.getProperty("app.datasource.rooms.password"));

        return dataSource;
 */