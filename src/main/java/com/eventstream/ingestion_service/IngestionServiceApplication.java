package com.eventstream.ingestion_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync; // <-- මේ import එකත් add වෙන්න ඕනේ

@EnableAsync // <-- මේක තමයි අලුතෙන් add කරන්න ඕන annotation එක
@SpringBootApplication
public class IngestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngestionServiceApplication.class, args);
	}

}