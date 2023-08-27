package com.hibernatejpa.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hibernatejpa.config.PersistenceConfig;
//import com.hibernatejpa.config.PersistenceConfigTest;
import com.hibernatejpa.repository.MovieRepository;

//@ContextConfiguration(classes = {PersistenceConfigTest.class})
//@SqlConfig(dataSource="dataSourceH2", transactionManager = "transactionManager")

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@SqlConfig(dataSource = "dataSource", transactionManager = "transactionManager")
@Sql({"/datas/datas-test.sql"})
public class MovieServiceTest {
	
	@Autowired
	private MovieService service;
	
	@Test
	public void updateDescription_casNominal() {
		service.updateDescription(-2L, "super film gros t'as i");
	}
	
	

}
