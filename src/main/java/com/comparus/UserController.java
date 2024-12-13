package com.comparus;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="users", description = "User API")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	@Parameter(name="users", description="Get users from all avaliable sources")
	public List<UserDto> getUsers(@RequestParam(required = false) Map<String, String> filter) {
		return userService.getUsers(filter);
	}
}
