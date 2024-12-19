package com.comparus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties
@Setter
@Getter
public class DataSourcesConfig {

	private List<DataSourceConfig> dataSources;

	@Setter
	@Getter
	public static class Mapping {
		private String id;
		private String username;
		private String name;
		private String surname;
		
		public String map(String field) {
			return switch (field) {
			case "id" -> id;
			case "username" -> username;
			case "name" -> name;
			case "surname" -> surname;
			default -> throw new IllegalArgumentException("Unknown field: " + field);
			};
		}
	}

	@Setter
	@Getter
	public static class DataSourceConfig {
		private String strategy;
		private String name;
		private String url;
		private String table;
		private String user;
		private String password;
		private Mapping mapping;

		public String getSelectUsersQuery(Map<String, String> filter) {
			String query = "select %s id, %s username, %s name, %s surname from %s"
					.formatted(mapping.getId(), mapping.getUsername(), mapping.getName(), mapping.getSurname(), table);
			if (!filter.isEmpty()) {
				List<String> predicates = new ArrayList<>();
				filter.forEach((k, v) -> {
					predicates.add(mapping.map(k) + "='" + v + "'"); 
				});
				query = query + " where " + predicates.stream().collect(Collectors.joining(" and "));
			}
			return query;	
		}

		public String getDriverClassName() {
			if (strategy.equals("postgres")) {
				return "org.postgresql.Driver";
			}
			throw new IllegalArgumentException("Unsupported strategy: " + strategy);
		}
	}
}

