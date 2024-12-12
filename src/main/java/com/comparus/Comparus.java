package com.comparus;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.comparus.DataSourcesConfig.DataSourceConfig;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class Comparus {

	@Configuration
	public class SpringConfig {

		@Bean
		public Map<String, JdbcTemplate> jdbcTemples(DataSourcesConfig dataSourcesConfig) {
			Map<String, JdbcTemplate> jdbcTemplates = new HashMap<>();
			for (DataSourceConfig cfg : dataSourcesConfig.getDataSources()) {
				DataSource dataSource = DataSourceBuilder.create()
						.url(cfg.getUrl())
						.username(cfg.getUser())
						.driverClassName(cfg.getDriverClassName())
						.password(cfg.getPassword()).build();
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				jdbcTemplates.put(cfg.getName(), jdbcTemplate);
			}
			return jdbcTemplates;
		}

		@Bean
		public Docket api() {
			return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any()).build();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(Comparus.class, args);
	}
}
