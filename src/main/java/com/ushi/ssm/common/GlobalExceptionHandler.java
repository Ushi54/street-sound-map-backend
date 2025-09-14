package com.ushi.ssm.common;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
		return ResponseEntity.badRequest().body(error("bad_request", ex.getMessage(), 400));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
		return ResponseEntity.badRequest().body(error("bad_request", "invalid query parameter", 400));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleOther(Exception ex) {
		// ログに詳細、クライアントには一般的なメッセージ
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(error("internal_error", "unexpected error", 500));
	}

	private Map<String, Object> error(String code, String message, int status) {
		return Map.of("timestamp", OffsetDateTime.now().toString(), "status", status, "error", code, "message",
				message);
	}
}
