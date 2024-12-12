package com.comparus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = {IntegrationTests.Initializer.class})
@TestPropertySource(properties = "spring.config.location=classpath:/application-test.yml")
public class IntegrationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Container
	@SuppressWarnings("resource")
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17").withDatabaseName("postgres")
			.withUsername("postgres").withPassword("postgres").withInitScript("init-db.sql").withExposedPorts(5432);

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of("CONTAINER.URL=" + postgres.getJdbcUrl())
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@DynamicPropertySource
	static void registerPgProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url",
				() -> String.format("jdbc:postgresql://localhost:%d/prop", postgres.getFirstMappedPort()));
	}

	@Test
	void myTest() {
		UserDto[] dtos = testRestTemplate.getForObject("/users", UserDto[].class);
		System.out.println(dtos);
	}

}