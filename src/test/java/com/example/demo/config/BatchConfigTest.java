package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.jpa.secondary.entity.MyEntity;
import com.example.demo.jpa.secondary.repo.MyRepo;

@Configuration
public class BatchConfigTest {

	@Autowired
	private MyRepo repo;

	@Bean
	public ApplicationArguments applicationArguments() {
		prepareRepo();
		return new DefaultApplicationArguments(new String[] { "" });
	}

	private void prepareRepo() {
		MyEntity e = new MyEntity();
		e.setId(52259374034L);
		repo.save(e);
	}

}
