package com.excilys.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackageClasses = {SpringConfig.class}, basePackages = { "com.excilys.dao"})
public class SpringTestConfig {
	
	@Bean
    public HikariDataSource getDataSource() {
        return new HikariDataSource(new HikariConfig("/database/hikariConfig.properties"));
    }
	
}