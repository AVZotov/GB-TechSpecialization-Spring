package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.domain.Customer;
import ru.gb.repository.CustomerJDBCDataAccessRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    public final CustomerJDBCDataAccessRepository customerJDBCDataAccessRepository;

    public List<Customer> selectAllCustomers(){
        return customerJDBCDataAccessRepository.selectAllCustomers();
    }

    public Customer selectCustomerById(Integer id){
        return customerJDBCDataAccessRepository.selectCustomerById(id)
                .orElseThrow(() -> new RuntimeException("customer with id [%s] not found".formatted(id))
                );
    }

    public void insertNewCustomer(Customer customer){
        if (customerJDBCDataAccessRepository.existsCustomerWithEmail(customer.getEmail())){
            throw new RuntimeException("email already taken");
        }
        customerJDBCDataAccessRepository.insertCustomer(customer);
    }

    public void updateCustomer(Integer id, Customer updateRequest){
        Customer existingCustomer = selectCustomerById(id);
        boolean changes = false;

        if (!updateRequest.getName().equals(existingCustomer.getName())) {
            existingCustomer.setName(updateRequest.getName());
            changes = true;
        }
        if (!updateRequest.getAge().equals(existingCustomer.getAge())) {
            existingCustomer.setAge(updateRequest.getAge());
            changes = true;
        }
        if (!updateRequest.getEmail().equals(existingCustomer.getEmail())) {
            if (existsCustomerWithEmail(updateRequest.getEmail())) {
                throw new RuntimeException("email already taken");
            }
            existingCustomer.setEmail(updateRequest.getEmail());
            changes = true;
        }
        if (!changes) {
            throw new RuntimeException("no data changes found");
        }
        customerJDBCDataAccessRepository.updateCustomer(existingCustomer);
    }

    public void deleteCustomer(Integer id){
        if (!existsCustomerWithId(id)){
            throw new RuntimeException("customer not found");
        }
        customerJDBCDataAccessRepository.deleteCustomer(id);
    }

    public boolean existsCustomerWithId(Integer id){
        return customerJDBCDataAccessRepository.existsCustomerWithId(id);
    }

    public boolean existsCustomerWithEmail(String email){
        return customerJDBCDataAccessRepository.existsCustomerWithEmail(email);
    }
}
