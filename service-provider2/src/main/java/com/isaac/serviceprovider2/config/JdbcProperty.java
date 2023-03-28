package com.isaac.serviceprovider2.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JdbcProperty {

    @Value("${database.jdbc.driver}")
    private String jdbcDriver;

    @Value("${database.jdbc.url}")
    private String jdbcUrl;

    @Value("${database.jdbc.username}")
    private String jdbcUserName;

    @Value("${database.jdbc.password}")
    private String jdbcPassword;

}
