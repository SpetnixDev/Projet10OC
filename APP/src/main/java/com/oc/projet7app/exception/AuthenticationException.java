package com.oc.projet7app.exception;

public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 994742266219246677L;

	public AuthenticationException(String message) {
		super(message);
	}
}
