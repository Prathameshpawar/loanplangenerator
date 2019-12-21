package com.pp.plangenerator.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author PRATHAMESH PAWAR
 *
 */
@SpringBootApplication
@ComponentScan("com.pp.plangenerator")
public class PlanGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanGeneratorApplication.class, args);
	}
}
