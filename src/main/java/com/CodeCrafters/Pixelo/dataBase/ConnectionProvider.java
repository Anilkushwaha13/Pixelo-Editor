package com.CodeCrafters.Pixelo.dataBase;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

//
//@Component
//public class ConnectionProvider {
//    @Bean(destroyMethod = "close")
//    public DataSource dataSource(){
//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        config.setJdbcUrl("jdbc:mysql://pixelo-editor-code-crafter.f.aivencloud.com:25243/defaultdb?ssl-mode=REQUIRED"); // Replace with your own value
//        config.setUsername("avnadmin");// Replace with your own value
//        config.setPassword("AVNS_yxUp2IWDbcw2DvZsCLB");
//            return new HikariDataSource(config);
//    }
//}


