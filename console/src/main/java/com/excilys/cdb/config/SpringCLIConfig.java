package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
		"com.excilys.cdb.service", 
		"com.excilys.cdb.dao", 
		"com.excilys.cdb.mapper", 
		"com.excilys.cdb.verification", 
		"com.excilys.cdb.ui", 
		"com.excilys.cdb.controllerCLI", 
		"com.excilys.cdb.controller.session"})
public class SpringCLIConfig {

	@Bean
	public DataSource getDataSource() {
		return new HikariDataSource(new HikariConfig("/database/hikariConfig.properties"));
	}
	
	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(getDataSource());
		factoryBean.setPackagesToScan("com.excilys.cdb.dto");
		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}
	
}
