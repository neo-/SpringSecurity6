package com.example.springsecurity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

	@GetMapping("/status")
	public ResponseEntity<String> getStatus() {
		return ResponseEntity.ok("Hello World! ");
	}
}
