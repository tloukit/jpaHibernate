//package com.hibernatejpa.config;
//
//import java.util.Properties;
//
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//
//
//@Configuration
//@EnableTransactionManagement
//@ComponentScan(basePackages = {"com.hibernatejpa"})
//public class PersistenceConfigTest {
//
//
//	// LocalContainerEntityManagerFactoryBean => permet de générer contexte de persistence
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//		em.setDataSource(dataSourceH2());
//		// va chercher les entités dans tutorial.domain
//		em.setPackagesToScan(new String[] { "com.hibernatejpa.domain", "com.hibernatejpa.converter" });
//		
//		// on lui dit qu'on travaille avec implémentation Hibernate ( dépendance hibernate-core est bien présente par ailleurs)
//		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		em.setJpaVendorAdapter(vendorAdapter);
//		em.setJpaProperties(additionalProperties());
//
//		return em;
//	}
//
//	@Bean
//	public DataSource dataSourceH2() { // on travaille avec H2
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//
//		return dataSource;
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager() {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//
//		return transactionManager;
//	}
//	
//	private Properties additionalProperties() {
//		Properties properties = new Properties();
//		properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//		// dialecte a utiliser pour parler avec la BD
//		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//		return properties;
//	}
//
//}
