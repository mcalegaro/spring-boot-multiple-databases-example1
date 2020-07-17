package com.example.demo.jpa.secondary;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.example.demo.jpa.secondary.SecondaryDatabaseConfiguration;
import com.example.demo.jpa.secondary.entity.MyEntity;

public class SecondaryDatabaseConfigurationTest extends SecondaryDatabaseConfiguration {

	@Override
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("secondaryDataSource") DataSource dataSource) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("hibernate.hbm2ddl.auto", "create");
		return builder.dataSource(dataSource).properties(properties).packages(MyEntity.class).build();
	}

}