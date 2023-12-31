package com.pvs.perfectresume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PerfectResumeApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(PerfectResumeApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(PerfectResumeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Application is running");
	}
}
