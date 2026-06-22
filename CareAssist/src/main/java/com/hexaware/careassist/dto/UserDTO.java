package com.hexaware.careassist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	
	private int userId;
	private String name;
	private String email;
	private String password;
	private String phone;
	private String role;
	private String accountStatus;
	private String profilePic;
}
