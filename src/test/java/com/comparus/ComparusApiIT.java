package com.comparus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ComparusApiIT {

	@DynamicPropertySource
	static void registerPgJdbcUrl(DynamicPropertyRegistry registry) {
		registry.add("data-sources[0].strategy", () -> "postgres");
		registry.add("data-sources[0].user", () -> "postgres");
		registry.add("data-sources[0].password", () -> "postgres");
		registry.add("data-sources[0].table", () -> "users");
		registry.add("data-sources[0].url", () -> postgres.getJdbcUrl());
		registry.add("data-sources[0].mapping.id", () -> "user_id");
		registry.add("data-sources[0].mapping.username", () -> "login");
		registry.add("data-sources[0].mapping.name", () -> "first_name");
		registry.add("data-sources[0].mapping.surname", () -> "last_name");
	}

	@Autowired
	TestRestTemplate testRestTemplate;

	@Container
	@SuppressWarnings("resource")
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
			.withUsername("postgres")
			.withPassword("postgres")
			.withInitScript("init-db.sql");

	@Test
	void test() {
		UserDto[] dtos = testRestTemplate.getForObject("/users", UserDto[].class);
		assertEquals(1, dtos.length);
		assertEquals("example-user-id-1", dtos[0].id);
		assertEquals("user-1", dtos[0].username);
		assertEquals("John", dtos[0].name);
		assertEquals("Doe", dtos[0].surname);
	}

}