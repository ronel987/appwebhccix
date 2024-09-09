package com.sys.myapp;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//configura archivos q se guarden en BD
@Configuration
public class WebConfig implements WebMvcConfigurer {
   @Bean
   public MultipartResolver multipartResolver() throws IOException {
	   
	CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver();
	//archivo tamaño ilimitado
	multipartResolver.setMaxUploadSize(-1);
	//nada en memoria
	multipartResolver.setMaxInMemorySize(0);
	return multipartResolver;
   }
}
