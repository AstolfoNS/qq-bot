package com.astolfo.robotservice;

import love.forte.simbot.spring.EnableSimbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSimbot
@SpringBootApplication
public class RobotServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobotServiceApplication.class, args);
	}

}
