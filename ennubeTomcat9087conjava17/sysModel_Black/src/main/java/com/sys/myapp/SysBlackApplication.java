package com.sys.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class SysBlackApplication  {
	private TemplateEngine templateEngine = new TemplateEngine();
	
	 public SysBlackApplication(final ServletContext servletContext) {

	        super();

	        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);	        
	        
	        // This will convert "index" to "/WEB-INF/templates/index.html"
	        templateResolver.setPrefix("/src/main/resources/templates");
	        templateResolver.setSuffix(".html");
	        // Template cache TTL=1h. If not set, entries would be cached until expelled
	        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
	        
	        // Cache is set to true by default. Set to false if you want templates to
	        // be automatically updated when modified.
	        templateResolver.setCacheable(true);
	        
	        this.templateEngine = new TemplateEngine();
	        this.templateEngine.setTemplateResolver(templateResolver);	     

	    }
	
	public static void main(String[] args) {
		SpringApplication.run(SysBlackApplication.class, args);		
	}
	
	
	
	@Bean
	public LayoutDialect layoutDialect() {
	  return new LayoutDialect();
	}
	
}
