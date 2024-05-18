package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestTestingExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.from(TestingExerciseApplication::main).with(TestTestingExerciseApplication.class).run(args);
	}

}
