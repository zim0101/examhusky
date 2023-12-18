package com.app.examhusky;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamhuskyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExamhuskyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Add seeder here!");
	}
}
