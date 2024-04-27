package ru.gb.service;

import org.springframework.stereotype.Service;
import ru.gb.exception.RequestValidationException;
import ru.gb.exception.ResourceNotFoundException;
import ru.gb.model.Customer;
import ru.gb.model.CustomerUpdateRequest;
import ru.gb.repository.CustomerRepository;

import java.util.List;

@Service
public class DataProcessingService {
    private final CustomerRepository customerRepository;

    public DataProcessingService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer selectCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with ID: %d not found".formatted(customerId)
                ));
    }

    public void updateCustomer(Long customerId, CustomerUpdateRequest customerUpdateRequest){
        Customer customer = selectCustomerById(customerId);
        boolean hasChanges = false;

        if (customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())){
            customer.setName(customerUpdateRequest.name());
            hasChanges = true;
        }

        if (customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(customer.getAge())){
            customer.setAge(customerUpdateRequest.age());
            hasChanges = true;
        }

        if (!hasChanges){throw new RequestValidationException("nothing to update");}

        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId){
        if (!customerRepository.existsCustomerById(customerId)){
            throw new ResourceNotFoundException("customer with ID: %d not found".formatted(customerId));
        }
        customerRepository.deleteById(customerId);
    }

    public List<Customer> filterCustomersByAge(Integer age) {

        return customerRepository.findCustomerByAge(age);
    }

    public Double countAverageAge() {
        List<Customer> customers = selectAllCustomers();
        return customers.stream()
                .mapToDouble(Customer::getAge)
                .average()
                .orElse(0);
    }
}
