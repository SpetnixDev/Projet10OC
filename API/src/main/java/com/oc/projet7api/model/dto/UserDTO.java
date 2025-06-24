package com.oc.projet7api.model.dto;

import lombok.Data;

@Data
public class UserDTO {
	private String email;
	private String password;
	
	private String firstName;
	private String lastName;
}
