package ru.gb.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.gb.domain.Customer;
import ru.gb.domain.CustomerDTO;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerDtoMapper implements Function<Customer, CustomerDTO> {
    @Override
    public CustomerDTO apply(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                customer.getUsername()
        );
    }
}
