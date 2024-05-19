package ru.gb;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.gb.domain.Customer;
import ru.gb.service.CustomerService;

import java.util.Random;

@SpringBootApplication
public class Lesson10Application {

	public static void main(String[] args) {
		SpringApplication.run(Lesson10Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(CustomerService customerService){
		return args -> {
			String name = new Faker().name().fullName();
			String email = new Faker().internet().safeEmailAddress();
			int age = new Random().nextInt(18, 100);

			customerService.insertNewCustomer(new Customer(name, email, age));
		};
	}
}
