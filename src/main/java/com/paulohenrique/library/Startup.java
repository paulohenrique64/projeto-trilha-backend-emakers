package com.paulohenrique.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Startup {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Startup.class, args);
	}
}
