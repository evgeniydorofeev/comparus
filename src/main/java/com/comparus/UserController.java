package com.comparus;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController

public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
    @ApiOperation(value = "Fetch a single resource.")
	public List<UserDto> getUsers(@RequestParam(required = false) Map<String, String> filter) {
		return userService.getUsers(filter);
	}
}
