package com.isaac.serviceprovider2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    // method 1 to assign a DataSource
    @Autowired
    JdbcProperty jdbcProperty;

    // method 2 to assign a DataSource
    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        // assign a DataSource
        // method 1: create DataSource Bean using @Bean
//        jdbcTemplate.setDataSource(jdbcDataSource());

        // method 2: configure a DataSource in application,yml
        jdbcTemplate.setDataSource(dataSource);

        return jdbcTemplate;
    }

//    @Bean // get rid of conflict with DataSource in application.yml
    public DataSource jdbcDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(jdbcProperty.getJdbcDriver());
        driverManagerDataSource.setUrl(jdbcProperty.getJdbcUrl());
        driverManagerDataSource.setUsername(jdbcProperty.getJdbcUserName());
        driverManagerDataSource.setPassword(jdbcProperty.getJdbcPassword());
        return driverManagerDataSource;
    }
}
