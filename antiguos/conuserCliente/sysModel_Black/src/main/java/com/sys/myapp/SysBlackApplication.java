package com.sys.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
