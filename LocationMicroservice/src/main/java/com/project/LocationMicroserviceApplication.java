package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.project.vo.RestTemplateVO;

@SpringBootApplication
public class LocationMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationMicroserviceApplication.class, args);
	}

	@Bean
//	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public RestTemplateVO getRestTemplateVO() {
		return new RestTemplateVO(); 
	}
	
}
