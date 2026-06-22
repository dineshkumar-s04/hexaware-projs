package com.hexaware.patientinsurance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PatientInsuranceRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientInsuranceRestApiApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {

		return new RestTemplate();

	}
}
