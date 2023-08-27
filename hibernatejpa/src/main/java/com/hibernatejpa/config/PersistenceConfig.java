package com.hibernatejpa.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.hibernatejpa"})
@PropertySource("classpath:application.properties")
public class PersistenceConfig {

	@Value("${database.url}")
	private String databaseUrl;
	
	// LocalContainerEntityManagerFactoryBean => permet de générer contexte de persistence
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		// va chercher les entités dans tutorial.domain
		em.setPackagesToScan(new String[] { "com.hibernatejpa.domain",  "com.hibernatejpa.converter" });
		
		// on lui dit qu'on travaille avec implémentation Hibernate ( dépendance hibernate-core est bien présente par ailleurs)
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@Bean
	public DataSource dataSource() { // on travaille avec H2
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/hibernate4all");
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername("postgres");
		dataSource.setPassword("root");

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return transactionManager;
	}
	
	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "none");
		// dialecte a utiliser pour parler avec la BD
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		return properties;
	}

}
