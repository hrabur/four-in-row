package pu.fmi.connect4.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pu.fmi.connect4.logic.GameNotFoundException;
import pu.fmi.connect4.logic.IllegalMoveException;

/**
 * Demo og how exception handling is done in Spring MVC. For more details see
 * {@link https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-exceptionhandler.html}
 */
@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler
	public ErrorResponse handleGameNotFound(GameNotFoundException e) {
		return ErrorResponse.builder(e, HttpStatus.NOT_FOUND, e.getMessage()).build();
	}

	@ExceptionHandler
	public ErrorResponse handleGameNotFound(IllegalMoveException e) {
		return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage()).build();
	}
}
