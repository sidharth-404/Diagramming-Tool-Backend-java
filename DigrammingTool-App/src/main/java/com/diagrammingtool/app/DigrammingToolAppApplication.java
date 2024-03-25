package com.diagrammingtool.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class DigrammingToolAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigrammingToolAppApplication.class, args);
		System.out.println("Hello");
		
	}

}
