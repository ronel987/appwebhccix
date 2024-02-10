package com.sys.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nz.net.ultraq.thymeleaf.LayoutDialect;


@SpringBootApplication
public class SysBlackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysBlackApplication.class, args);
	}
	
	@Bean
	public LayoutDialect layoutDialect() {
	  return new LayoutDialect();
	}
	
}
