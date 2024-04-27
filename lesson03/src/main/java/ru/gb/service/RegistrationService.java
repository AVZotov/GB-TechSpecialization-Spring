package ru.gb.service;

import org.springframework.stereotype.Service;
import ru.gb.model.Customer;
import ru.gb.model.CustomerRegistrationRequest;
import ru.gb.repository.CustomerRepository;

@Service
public class RegistrationService {

    private final NotificationService notificationService;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    public RegistrationService(NotificationService notificationService, CustomerService customerService, CustomerRepository customerRepository) {
        this.notificationService = notificationService;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        Customer customer = customerService.addCustomer(customerRegistrationRequest);
        customerRepository.save(customer);
        notificationService.notifyOnCustomerInsertion("Customer with name: " + customer.getName() + " successfully registered");
    }
}
