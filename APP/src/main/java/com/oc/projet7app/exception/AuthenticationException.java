package com.oc.projet7app.exception;

import java.io.Serial;

public class AuthenticationException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 994742266219246677L;

	public AuthenticationException(String message) {
		super(message);
	}
}
