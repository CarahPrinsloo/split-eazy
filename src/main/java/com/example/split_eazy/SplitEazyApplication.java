package com.example.split_eazy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@SpringBootApplication
@EnableR2dbcAuditing
public class SplitEazyApplication {
	public static void main(String[] args) {
		SpringApplication.run(SplitEazyApplication.class, args);
	}
}