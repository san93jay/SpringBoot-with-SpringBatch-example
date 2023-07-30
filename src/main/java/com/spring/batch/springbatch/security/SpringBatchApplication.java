package com.spring.batch.springbatch.security;

import org.modelmapper.ModelMapper;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.servlet.Filter;

@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);

	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder(8);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	@Bean
	public Filter filter(){
		ShallowEtagHeaderFilter filter=new ShallowEtagHeaderFilter();
		return filter;
	}

}
