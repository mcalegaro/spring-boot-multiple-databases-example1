package com.example.demo.config;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.demo.jpa.primary.PrimaryDatabaseConfiguration;
import com.example.demo.jpa.primary.entity.MyPrimaryEntity;
import com.example.demo.jpa.primary.repo.MyPrimaryRepo;
import com.example.demo.jpa.secondary.SecondaryDatabaseConfiguration;
import com.example.demo.jpa.secondary.entity.MyEntity;
import com.example.demo.jpa.secondary.repo.MyRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {

		MyRepo.class,

		PrimaryDatabaseConfiguration.class,

		SecondaryDatabaseConfiguration.class,

		MyPrimaryEntity.class,

		MyPrimaryRepo.class,

		MyEntity.class,

})
public class BatchConfig {

	@Bean
	public Job job(@Autowired JobBuilderFactory jobFactory, @Autowired StepBuilderFactory stepFactory,
			@Qualifier("secondaryFactory") EntityManagerFactory emf, @Autowired MyPrimaryRepo repo) {
		log.info("setting up job");
		return jobFactory.get("myJob").flow(step(stepFactory, emf, repo)).end().build();
	}

	@Bean
	public Step step(StepBuilderFactory stepFactory, EntityManagerFactory emf, MyPrimaryRepo repo) {
		return stepFactory.get("myStep").<MyEntity, MyEntity>chunk(2).reader(reader(emf)).processor(processor(repo))
				.writer(writer()).build();
	}

	private ItemWriter<? super MyEntity> writer() {
		return new ItemWriter<MyEntity>() {

			@Override
			public void write(List<? extends MyEntity> items) throws Exception {
				items.forEach(i -> {
					log.info("wrote ".concat(i.toString()));
				});
			}
		};
	}

	@Bean
	public ItemProcessor<MyEntity, MyEntity> processor(MyPrimaryRepo repo) {
		return new ItemProcessor<MyEntity, MyEntity>() {
			@Override
			public MyEntity process(MyEntity item) throws Exception {
				MyPrimaryEntity e = new MyPrimaryEntity();
				e.setId(item.getId());
				repo.save(e);
				log.info("processed " + item);
				return item;
			}
		};
	}

	@Bean
	public JpaPagingItemReader<MyEntity> reader(EntityManagerFactory emf) {
		JpaPagingItemReader<MyEntity> reader = new JpaPagingItemReader<MyEntity>();
		reader.setEntityManagerFactory(emf);
		reader.setQueryString("select e from MyEntity e");
		return reader;
	}

}
