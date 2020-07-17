package com.example.demo.jpa.secondary;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.jpa.secondary.entity.MyEntity;
import com.example.demo.jpa.secondary.repo.MyRepo;

@Configuration
@EnableJpaRepositories(

		entityManagerFactoryRef = "secondaryFactory",

		transactionManagerRef = "secondaryTransaction",

		basePackageClasses = MyRepo.class

)
@EntityScan(basePackageClasses = MyEntity.class)
public class SecondaryDatabaseConfiguration {

	@Bean(name = "secondaryDataSource")
	@ConfigurationProperties(prefix = "secondary.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "secondaryFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("secondaryDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages(MyEntity.class.getPackage().getName()).build();
	}

	@Bean(name = "secondaryTransaction")
	public PlatformTransactionManager transactionManager(
			@Qualifier("secondaryFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}