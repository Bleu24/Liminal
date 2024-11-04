package com.reamillo.emplms;

import com.reamillo.emplms.config.JwtSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
// import com.reamillo.config.SecurityConfig;

@SpringBootApplication
@Import(JwtSecurityConfig.class)
public class LiminalApp {
	public static void main(String[] args)
	{
		SpringApplication.run(LiminalApp.class, args);
	}

}
