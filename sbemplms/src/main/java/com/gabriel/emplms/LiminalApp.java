package com.gabriel.emplms;

import com.reamillo.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
@SpringBootApplication
@Import(SecurityConfig.class)
public class LiminalApp {
	public static void main(String[] args)
	{
		SpringApplication.run(LiminalApp.class, args);
	}

}
