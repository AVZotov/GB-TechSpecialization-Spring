package ru.gb.service;

import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gb.domain.Customer;
import ru.gb.repository.CustomerJDBCDataAccessRepository;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //this annotation allows to not close MockitoAnnotation.openMocks()
class CustomerServiceTest {

    private CustomerService underTest;
    @Mock
    private CustomerJDBCDataAccessRepository dataAccessRepository;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(dataAccessRepository);
    }

    @Test
    void selectAllCustomers() {
        // When
        underTest.selectAllCustomers();
        // Then
        verify(dataAccessRepository).selectAllCustomers();
    }

    @Test
    void canGetCustomer() {
        // Given
        int id = new Random().nextInt(1, 100);
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(id, name, email, age);
        when(dataAccessRepository.selectCustomerById(id)).thenReturn(Optional.of(customer));
        // When
        Customer actual = underTest.selectCustomerById(id);
        // Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional() {
        // Given
        int id = -1;
        // When
        when(dataAccessRepository.selectCustomerById(id)).thenReturn(Optional.empty());
        // Then
        assertThatThrownBy(() -> underTest.selectCustomerById(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));
    }

    @Test
    void insertNewCustomer() {
        int id = new Random().nextInt(1, 100);
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer insertRequest = new Customer(id, name, email, age);

        when(dataAccessRepository.existsCustomerWithEmail(email)).thenReturn(false);
        // When
        underTest.insertNewCustomer(insertRequest);
        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(dataAccessRepository).insertCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(insertRequest.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(insertRequest.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(insertRequest.getAge());
    }

    @Test
    void willThrowWhenEmailExistsWhileAddingACustomer() {
        // Given
        int id = new Random().nextInt(1, 100);
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer insertRequest = new Customer(id, name, email, age);

        when(dataAccessRepository.existsCustomerWithEmail(email)).thenReturn(true);
        // When
        assertThatThrownBy(() -> underTest.insertNewCustomer(insertRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("email already taken");
        // Then
        verify(dataAccessRepository, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        // Given
        int id = new Random().nextInt(1, 100);
        when(dataAccessRepository.existsCustomerWithId(id)).thenReturn(true);
        // When
        underTest.deleteCustomer(id);
        // Then
        verify(dataAccessRepository).deleteCustomer(id);
    }

    @Test
    void willThrowDeleteCustomerByIdNotExists() {
        // Given
        int id = new Random().nextInt(1, 100);
        when(dataAccessRepository.existsCustomerWithId(id)).thenReturn(false);
        // When
        assertThatThrownBy(() -> underTest.deleteCustomer(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("customer not found");
        // Then
        verify(dataAccessRepository, never()).deleteCustomer(id);
    }

    @Test
    void canUpdateAllCustomersProperties() {
        // Given
        int id = new Random().nextInt(1, 100);
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(id, name, email, age);

        when(dataAccessRepository.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newName = new Faker().name().fullName();
        String newEmail = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int newAge = new Random().nextInt(18, 100);
        Customer updateRequest = new Customer(id, newName, newEmail, newAge);
        when(dataAccessRepository.existsCustomerWithEmail(newEmail)).thenReturn(false);
        // When
        underTest.updateCustomer(id, updateRequest);
        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(dataAccessRepository).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.getAge());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        // Given
        int id = new Random().nextInt(1, 100);
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(id, name, email, age);
        when(dataAccessRepository.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newName = new Faker().name().fullName();
        Customer updateRequest = new Customer(newName, email, age);

        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(dataAccessRepository).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        // Given
        int id = new Random().nextInt(1, 100);
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(id, name, email, age);
        when(dataAccessRepository.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        Customer updateRequest = new Customer(name, newEmail, age);

        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(dataAccessRepository).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        // Given
        int id = new Random().nextInt(1, 100);
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(id, name, email, age);
        when(dataAccessRepository.selectCustomerById(id)).thenReturn(Optional.of(customer));

        int newAge = -1;
        Customer updateRequest = new Customer(name, email, newAge);
        //When
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(dataAccessRepository).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        // Given
        int id = new Random().nextInt(1, 100);
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(id, name, email, age);

        when(dataAccessRepository.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());

        Customer updateRequest = new Customer(
                name, newEmail, age);
        when(dataAccessRepository.existsCustomerWithEmail(newEmail)).thenReturn(true);
        // When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("email already taken");
        // Then
        verify(dataAccessRepository, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        // Given
        int id = new Random().nextInt(1, 100);
        String name = new Faker().name().fullName();
        String email = new Faker().internet().safeEmailAddress(UUID.randomUUID().toString());
        int age = new Random().nextInt(18, 100);
        Customer customer = new Customer(id, name, email, age);
        when(dataAccessRepository.selectCustomerById(id)).thenReturn(Optional.of(customer));

        Customer updateRequest = new Customer(
                name, email, age);
        // When
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("no data changes found");
        // Then
        verify(dataAccessRepository, never()).updateCustomer(any());
    }
}