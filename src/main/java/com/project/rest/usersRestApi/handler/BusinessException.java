package com.project.rest.usersRestApi.handler;

public class BusinessException extends RuntimeException {
	public BusinessException(String message) {
		// TODO Auto-generated constructor stub
		super(message);
	}

	public BusinessException(String mensagem, Object... params) {
		super(String.format(mensagem, params));
	}
}
