package com.buseni.landingpageaigenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
@RequestMapping("/")
public class LandingPageAIGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LandingPageAIGeneratorApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		return "index";
	}
}
