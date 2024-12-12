package com.comparus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.comparus.DataSourcesConfig.DataSourceConfig;

@Repository
public class UserService {

	@Autowired
	private DataSourcesConfig config;

	private Map<String, JdbcTemplate> jdbcTemplates = new ConcurrentHashMap<>();

	public List<UserDto> getUsers() {
		List<UserDto> users = new ArrayList<>();
		for (DataSourceConfig cfg : config.getDataSources()) {
			users.addAll(getUsers(cfg));
		}
		return users;
	}

	private List<UserDto> getUsers(DataSourceConfig cfg) {
		JdbcTemplate jdbcTemplate = jdbcTemplates.get(cfg.getName());
		if (jdbcTemplate == null) {
			DataSource dataSource = DataSourceBuilder.create().url(cfg.getUrl()).username(cfg.getUser())
					.driverClassName(cfg.getDriverClassName()).password(cfg.getPassword()).build();
			jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplates.put(cfg.getName(), jdbcTemplate);
		}
		;
		List<UserDto> users = jdbcTemplate.query(cfg.getSelectUserQuery(),
				new BeanPropertyRowMapper<UserDto>(UserDto.class));
		return users;
	}
}
