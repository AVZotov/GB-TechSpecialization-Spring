package ru.gb.repository;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.gb.AbstractTestContainers;
import ru.gb.domain.Customer;
import ru.gb.repository.utility.CustomerRowMapper;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerJDBCDataAccessRepositoryTest extends AbstractTestContainers {

    private CustomerJDBCDataAccessRepository underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        //New instance will be created before EACH test!
        underTest = new CustomerJDBCDataAccessRepository(getJdbcTemplate(), customerRowMapper);
    }

    @Test
    void selectAllCustomers() {
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);

        underTest.insertCustomer(customer);
        // When
        List<Customer> actual = underTest.selectAllCustomers();
        // Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        //Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);

        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When
        Optional<Customer> actual = underTest.selectCustomerById(id);
        // Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        // Given
        int id = 0;
        // When
        var actual = underTest.selectCustomerById(id);
        // Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        //Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);
        //When
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName()); // change
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }


    @Test
    void updateCustomerName() {
        // Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var newName = "foo";

        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);
        underTest.updateCustomer(update);
        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName); // change
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void updateCustomerEmail() {
        // Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newEmail = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());

        // When email is changed
        Customer update = new Customer();
        update.setId(id);
        update.setEmail(newEmail);
        underTest.updateCustomer(update);
        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(newEmail); // change
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void updateCustomerAge() {
        // Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        var newAge = 100;
        // When age is changed
        Customer update = new Customer();
        update.setId(id);
        update.setAge(newAge);
        underTest.updateCustomer(update);
        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(newAge); // change
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }

    @Test
    void willUpdateAllPropertiesCustomer() {
        // Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When update with new name, age and email
        Customer update = new Customer();
        update.setId(id);
        update.setName("foo");
        update.setEmail(new Faker().internet().safeEmailAddress(UUID.randomUUID().toString()));
        update.setAge(100);
        underTest.updateCustomer(update);
        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValue(update);
    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {
        // Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When update without no changes
        Customer update = new Customer();
        update.setId(id);
        underTest.updateCustomer(update);
        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }

    @Test
    void deleteCustomer() {
        //Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        // When
        underTest.deleteCustomer(id);
        // Then
        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void existsCustomerWithId() {
        //Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //When
        boolean actual = underTest.existsCustomerWithId(id);
        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerWithIdWillReturnFalseWhenIdNotPresent() {
        // Given
        int id = -1;
        // When
        var actual = underTest.existsCustomerWithId(id);
        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerWithEmail() {
        //Given
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(name, email, age);
        underTest.insertCustomer(customer);
        // When
        boolean actual = underTest.existsCustomerWithEmail(email);
        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
        // Given
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        // When
        boolean actual = underTest.existsCustomerWithEmail(email);
        // Then
        assertThat(actual).isFalse();
    }
}