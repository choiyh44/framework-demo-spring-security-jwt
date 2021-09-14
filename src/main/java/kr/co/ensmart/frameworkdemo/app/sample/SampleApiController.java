package kr.co.ensmart.frameworkdemo.app.sample;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/samples")
public class SampleApiController {

	@GetMapping("")
	public ResponseEntity<String> getAllSample() {
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
}
