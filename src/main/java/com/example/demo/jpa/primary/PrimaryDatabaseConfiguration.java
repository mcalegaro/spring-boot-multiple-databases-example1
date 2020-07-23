package com.example.demo.jpa.primary;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.jpa.primary.entity.MyPrimaryEntity;
import com.example.demo.jpa.primary.repo.MyPrimaryRepo;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory",

		transactionManagerRef = "transactionManager",

		basePackageClasses = MyPrimaryRepo.class)
@EntityScan(basePackageClasses = MyPrimaryEntity.class)
public class PrimaryDatabaseConfiguration {

	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "primary.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("dataSource") DataSource dataSource) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		return builder.dataSource(dataSource).packages(MyPrimaryEntity.class.getPackage().getName())
				.properties(properties).build();
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
