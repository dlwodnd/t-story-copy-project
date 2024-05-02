package com.projcet.tstorycopyproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class TStoryCopyProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TStoryCopyProjectApplication.class, args);
	}

}
