package com.humbuckers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDTO {

	
	private Long userId;
	private String userName;
	private String fullName;
	private String password;
	private UserRoleDTO userRole;
}