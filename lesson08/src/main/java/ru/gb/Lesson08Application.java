package ru.gb;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.gb.domain.Customer;
import ru.gb.repository.CustomerRepository;

@SpringBootApplication
public class Lesson08Application {

	public static void main(String[] args) {
		SpringApplication.run(Lesson08Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (CustomerRepository customerRepository){
		return args -> {

			for (int i = 0; i < 10; i++) {
				Faker faker = new Faker();
				String firstname = faker.name().firstName();
				String lastname = faker.name().lastName();
				String email = faker.internet().safeEmailAddress();
				Customer customer = new Customer(firstname, lastname, email);
				customerRepository.save(customer);
			}
		};
	}

//	@Bean
//	@Order(2)
//	CommandLineRunner commandLineRunner (){
//
//	}
}
