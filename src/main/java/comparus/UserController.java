package comparus;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import comparus.DataSourcesConfig.DataSourceConfig;

@RestController
public class UserController {

	@Autowired
	private DataSourcesConfig config;

	@GetMapping("/users")
	public List<UserDto> getUsers() {
		List<UserDto> result = new ArrayList<>();
		for (DataSourceConfig cfg : config.getDataSources()) {
			result.addAll(getUsers(cfg));
		}
		return result;
	}

	private List<UserDto> getUsers(DataSourceConfig cfg) {
		DataSource dataSource = DataSourceBuilder.create()
				.url(cfg.getUrl())
				.username(cfg.getUser())
				.driverClassName(cfg.getDriverClassName())
				.password(cfg.getPassword())
				.build();
		List<UserDto> users = new JdbcTemplate(dataSource).query(cfg.getSelectUserQuery(),
				new BeanPropertyRowMapper<UserDto>(UserDto.class));
		return users;
	}
}
