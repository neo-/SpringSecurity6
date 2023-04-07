package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

	@GetMapping("/demo")
	public String demo() {
		return "Demo Controller";
	}
}
