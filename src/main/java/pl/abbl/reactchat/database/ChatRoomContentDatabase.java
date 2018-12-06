package pl.abbl.reactchat.database;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.abbl.reactchat.models.ChatMessage;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class ChatRoomContentDatabase {
    @Autowired
    private Environment environment;

    @Bean("chatRoomContentDataSource")
    @ConfigurationProperties("app.datasource.rooms")
    public DataSource chatRoomContentDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("chatRoomContentEntityManager")
    public LocalContainerEntityManagerFactoryBean chatRoomContentEntityManagerFactory(final EntityManagerFactoryBuilder builder, final @Qualifier("chatRoomContentDataSource") DataSource dataSource){
        return builder.dataSource(dataSource).packages(ChatMessage.class).build();
    }

    @Bean("chatRoomContentTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("chatRoomContentEntityManager") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}

/*
DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("app.datasource.rooms.url"));
        dataSource.setUsername(environment.getProperty("app.datasource.rooms.username"));
        dataSource.setPassword(environment.getProperty("app.datasource.rooms.password"));

        return dataSource;
 */