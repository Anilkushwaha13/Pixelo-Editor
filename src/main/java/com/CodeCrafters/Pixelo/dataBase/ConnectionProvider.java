package com.CodeCrafters.Pixelo.dataBase;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
public class ConnectionProvider {
    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/appusers"); // Replace with your own value
        config.setUsername("root");// Replace with your own value
        config.setPassword("8446783531");// Replace with your own value
            return new HikariDataSource(config);
    }
}


