package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Exercise01Application {

	@Bean
	public RestTemplate template(){
		return new RestTemplate();
	}

	@Bean
	public HttpHeaders headers()
	{
		return new HttpHeaders();
	}

	public static void main(String[] args) {
		SpringApplication.run(Exercise01Application.class, args);
	}
}
