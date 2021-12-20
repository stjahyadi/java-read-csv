package com.example.solution.exception;


public class CustomFileNotFoundException extends Exception {
	private static final long serialVersionUID = 4932121857929485183L;

	public CustomFileNotFoundException(String message) {
		super(message);
	}

	public CustomFileNotFoundException(String message, Throwable err) {
		super(message, err);
	}

}
