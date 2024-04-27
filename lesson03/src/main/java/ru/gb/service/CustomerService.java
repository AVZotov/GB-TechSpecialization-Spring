package ru.gb.service;

import org.springframework.stereotype.Service;
import ru.gb.exception.EmptyFieldException;
import ru.gb.model.Customer;
import ru.gb.model.CustomerRegistrationRequest;

@Service
public class CustomerService {

    public Customer addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        if (customerRegistrationRequest.name().isBlank()){
            throw new EmptyFieldException("field name can't be empty");
        }

        if (customerRegistrationRequest.age() == null){
            throw new EmptyFieldException("field age can't be empty");
        }

        return new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.age()
        );
    }
}
