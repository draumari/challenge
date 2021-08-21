package com.ar.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ChallengeApplication {
	
	@Autowired
	static Consumo consumo;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
//		consumo.getCotizacion();
	}
		
}
