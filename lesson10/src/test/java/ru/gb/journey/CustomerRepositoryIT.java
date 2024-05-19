package ru.gb.journey;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.gb.domain.Customer;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//Spring boot test with Random port using webflux web environment as recommended by spring boot docs
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerRepositoryIT {

    @Autowired
    private WebTestClient webTestClient;
    private static final String CUSTOMER_URI = "/customers/";

    @Test
    void canRegisterCustomer() {
        // create registration request
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer request = new Customer(name, email, age);
        // send a post request
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), Customer.class)
                .exchange()
                .expectStatus()
                .isCreated();

        // get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        // make sure that customer is present
        Customer expectedCustomer = new Customer(
                name, email, age
        );

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        assert allCustomers != null;
        int id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        expectedCustomer.setId(id);

        // get customer by id
        webTestClient.get()
                .uri(CUSTOMER_URI + "{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .isEqualTo(expectedCustomer);
    }

    @Test
    void canDeleteCustomer() {
        // create registration request
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer request = new Customer(name, email, age);

        // send a post request
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), Customer.class)
                .exchange()
                .expectStatus()
                .isCreated();

        // get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();


        assert allCustomers != null;
        int id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // delete customer
        webTestClient.delete()
                .uri(CUSTOMER_URI + "{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
        // get customer by id
        webTestClient.get()
                .uri(CUSTOMER_URI + "{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(500);
    }

    @Test
    void canUpdateCustomer() {
        // create registration request
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer request = new Customer(name, email, age);
        // send a post request
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), Customer.class)
                .exchange()
                .expectStatus()
                .isCreated();

        // get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        assert allCustomers != null;
        int id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // update customer

        String newName = UUID.randomUUID().toString();

        Customer updateRequest = new Customer(newName, email, age);

        webTestClient.put()
                .uri(CUSTOMER_URI + "{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), Customer.class)
                .exchange()
                .expectStatus()
                .isOk();

        // get customer by id
        Customer updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_URI + "{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        Customer expected = new Customer(
                id, newName, email, age
        );
        assertThat(updatedCustomer).isEqualTo(expected);
    }
}
