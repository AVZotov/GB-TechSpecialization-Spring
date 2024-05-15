package ru.gb;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gb.domain.Customer;
import ru.gb.repository.CustomerRepository;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class Lesson07Application {

	public static void main(String[] args) {
		SpringApplication.run(Lesson07Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (CustomerRepository customerRepository,
										 PasswordEncoder passwordEncoder){
		return args -> {
			Customer admin = new Customer("root", "root@mail.ru", "password");
			customerRepository.save(admin);
			Customer user = new Customer("user", "user@mail.ru", "password");
			customerRepository.save(user);

			for (int i = 0; i < 10; i++) {
				Faker faker = new Faker();
				String name = faker.name().fullName();
				String email = faker.internet().safeEmailAddress();
				String password = passwordEncoder.encode(UUID.randomUUID().toString());
				Customer customer = new Customer(name, email, password);
				customerRepository.save(customer);
			}
		};
	}

}

