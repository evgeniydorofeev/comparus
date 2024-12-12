package com.comparus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest //(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource("/application-test.yml")
public class IntegrationTests {
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Container
	@SuppressWarnings("resource")
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
		.withDatabaseName("postgres")
		.withUsername("postgres")
		.withPassword("postgres")
		.withInitScript("init-db.sql")
		.withExposedPorts(5432);
	
	@Test
	void myTest() {
		System.out.println(postgres.getJdbcUrl());
		UserDto[] dtos = testRestTemplate.getForObject("/users", UserDto[].class);
		System.out.println(dtos);
	}

}