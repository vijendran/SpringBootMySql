package com.m2p.fileprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.m2p")
public class FileprocessorApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FileprocessorApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FileprocessorApplication.class);
	}
}
