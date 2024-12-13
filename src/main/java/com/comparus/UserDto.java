package com.comparus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "User information")
public class UserDto {
	@Schema(description = "User id")
	String id;
	
	@Schema(description = "Login")
	String username;
	
	@Schema(description = "First name")
	String name;
	
	@Schema(description = "Last name")
	String surname;
}
