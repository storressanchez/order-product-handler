package com.rohlik.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrderProductApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderProductApplication.class, args);
	}
}