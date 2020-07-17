package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.config.BatchConfigTest;
import com.example.demo.jpa.primary.repo.MyPrimaryRepo;
import com.example.demo.jpa.secondary.SecondaryDatabaseConfigurationTest;

@SpringBootTest(properties = { "spring.batch.job.enabled=false", "spring.profiles.active=test" })
@SpringBatchTest
@ContextConfiguration(classes = {

		DemoApplication.class,

		SecondaryDatabaseConfigurationTest.class,

		BatchConfigTest.class

})
class DemoApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncher;

	@Autowired
	private MyPrimaryRepo repo;

	@Test
	void contextLoads() throws Exception {
		JobExecution exec = jobLauncher.launchJob();
		assertEquals(1, repo.count());
		assertEquals(BatchStatus.COMPLETED, exec.getStatus());
	}

}
