package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ProtectedController {

	@GetMapping("/demo")
	public String demo() {
		return "Demo Controller";
	}


	@PostMapping("/demo")
	public String setTitle() {
		return "Demo Controller";
	}

}
