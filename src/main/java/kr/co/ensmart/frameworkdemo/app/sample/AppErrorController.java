package kr.co.ensmart.frameworkdemo.app.sample;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ensmart.frameworkdemo.base.advice.ErrorResponse;

@Controller
public class AppErrorController implements ErrorController {

	@RequestMapping("/error")
	public Object handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	    		return new ResponseEntity<>(new ErrorResponse("9200", "URL이 올바르지 않습니다."), new HttpHeaders(), HttpStatus.NOT_FOUND);
	        }
	        else if(statusCode == HttpStatus.FORBIDDEN.value()) {
	    		return new ResponseEntity<>(new ErrorResponse("9100", "권한이 필요합니다."), new HttpHeaders(), HttpStatus.FORBIDDEN);
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	    		return new ResponseEntity<>(new ErrorResponse("9000", "시스템오류가 발생했습니다."), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
		return new ResponseEntity<>(new ErrorResponse("9000", "시스템오류가 발생했습니다."), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
