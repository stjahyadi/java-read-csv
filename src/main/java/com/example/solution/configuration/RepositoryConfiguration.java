package com.example.solution.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;


@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.example.solution", "com.example.solution.service" })
public class RepositoryConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryConfiguration.class);

//	@Value("${jdbc.jndi}")
//	private String jndi;
//
//	@Bean
//	public JdbcTemplate getJdbcTemplate() {
//		return new JdbcTemplate(dataSource());
//	}
//
//	@Bean
//	public TransactionTemplate getTransactionTemplate(
//			@Qualifier("transactionManager") PlatformTransactionManager platformTransactionManager) {
//		return new TransactionTemplate(platformTransactionManager);
//	}
//
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//		return new PersistenceExceptionTranslationPostProcessor();
//	}
//
//	@Bean
//	public DataSource dataSource() {
//		try {
//			JndiTemplate jndiTemplate = new JndiTemplate();
//			DataSource driverManagerDataSource = (DataSource) jndiTemplate.lookup(jndi);
//			return driverManagerDataSource;
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//		}
//		return null;
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager() {
//		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
//		return transactionManager;
//	}

}
