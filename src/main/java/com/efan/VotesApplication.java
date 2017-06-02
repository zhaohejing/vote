package com.efan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableAsync
public class VotesApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication app=new SpringApplication(VotesApplication.class);
		app.run(args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(VotesApplication.class);
	}
}
