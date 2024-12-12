package comparus;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties
@Setter
@Getter
public class DataSourcesConfig {

	@Setter
	@Getter
	public static class Mapping {
		private String id;
		private String username;
		private String name;
		private String surname;
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

		public String getSelectUserQuery() {
			return "select %s id, %s username, %s name, %s surname from %s".formatted(mapping.getId(),
					mapping.getUsername(), mapping.getName(), mapping.getSurname(), table);
		}

		public String getDriverClassName() {
			return DataSourcesConfig.getDriverClassName(strategy);
		}
	}

	private List<DataSourceConfig> dataSources;

	private static String getDriverClassName(String strategy) {
		if (strategy.equals("postgres")) {
			return "org.postgresql.Driver";
		}
		throw new IllegalArgumentException("Unsupported strategy: " + strategy);
	}
}

