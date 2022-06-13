package com.krugger.web.controllers.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {

	private String code;
	private HttpStatus status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	private String message;
	private String cause;
	private String debugMessage;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	ApiError(HttpStatus status) {
		this();
		this.status = status;
		code = String.valueOf(status.value());
	}

	ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		code = String.valueOf(status.value());
		this.message = message;
		debugMessage = ex.getLocalizedMessage();
	}

	ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		code = String.valueOf(status.value());
		message = "Unexpected error";
		debugMessage = ex.getLocalizedMessage();
	}

	public String getCause() {
		return cause;
	}

	public String getCode() {
		return code;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
