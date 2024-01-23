package br.com.AmeDigital.backend_challenge_swapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BackendChallengeSwapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendChallengeSwapiApplication.class, args);
	}

}
