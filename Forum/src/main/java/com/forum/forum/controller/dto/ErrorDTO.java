package com.forum.forum.controller.dto;

public class ErrorDTO {

	private String field;
	private String exception;

	public ErrorDTO(String field, String exception) {
		this.field = field;
		this.exception = exception;
	}

	public String getField() {
		return field;

	}

	public String getException() {
		return exception;
	}

}
