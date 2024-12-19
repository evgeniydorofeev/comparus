package com.comparus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.comparus.DataSourcesConfig.DataSourceConfig;

@Repository
public class UserService {

	@Autowired
	private DataSourcesConfig dataSourcesConfig;

	@Autowired
	Map<String, JdbcTemplate> jdbcTemplates;

	public List<UserDto> getUsers(Map<String, String> filter) {
		List<UserDto> users = new ArrayList<>();
		for (DataSourceConfig cfg : dataSourcesConfig.getDataSources()) {
			users.addAll(getUsers(cfg, filter));
		}
		return users;
	}

	private List<UserDto> getUsers(DataSourceConfig cfg, Map<String, String> filter) {
		JdbcTemplate jdbcTemplate = jdbcTemplates.get(cfg.getName());
		String query = cfg.getSelectUsersQuery(filter);
		List<UserDto> users = jdbcTemplate.query(query, new BeanPropertyRowMapper<UserDto>(UserDto.class));
		return users;
	}
}
