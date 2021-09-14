package kr.co.ensmart.frameworkdemo.base.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GlobalControllerAdvice {
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleException(Exception e, HttpServletRequest request) {
		log.error("", e);
		return handleApiException();
    }

	@ResponseBody
	private ResponseEntity<ErrorResponse> handleApiException() { 
		return new ResponseEntity<>(new ErrorResponse("9000", "시스템오류가 발생했습니다."), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
