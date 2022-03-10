package com.letscode.starwar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class StarwarApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarwarApplication.class, args);
	}

}
