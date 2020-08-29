package com.example.demo.security;

import lombok.*;

@AllArgsConstructor
public enum Role {
		
	SUPER("ROLE_SUPER"),
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");

	private String value;
	
	Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
        return value;
    }
}
