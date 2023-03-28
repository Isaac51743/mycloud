package com.isaac.serviceprovider2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Autowired
    JdbcProperty jdbcProperty;


    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        // assign a DataSource
        jdbcTemplate.setDataSource(jdbcDataSource());

        return jdbcTemplate;
    }

    @Bean
    public DataSource jdbcDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(jdbcProperty.getJdbcDriver());
        driverManagerDataSource.setUrl(jdbcProperty.getJdbcUrl());
        driverManagerDataSource.setUsername(jdbcProperty.getJdbcUserName());
        driverManagerDataSource.setPassword(jdbcProperty.getJdbcPassword());
        return driverManagerDataSource;
    }
}
