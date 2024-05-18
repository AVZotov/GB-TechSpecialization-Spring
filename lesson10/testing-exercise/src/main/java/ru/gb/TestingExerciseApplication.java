package ru.gb;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.gb.domain.Customer;
import ru.gb.repository.CustomerRepository;

@SpringBootApplication
public class TestingExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingExerciseApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (CustomerRepository customerRepository){
		return args -> {

			for (int i = 0; i < 10; i++) {
				Faker faker = new Faker();
				String name = faker.name().fullName();
				String email = faker.internet().safeEmailAddress();
				String password = faker.internet().password();
				Customer customer = new Customer(name, email, password);
				customerRepository.save(customer);
			}
		};
	}
}
