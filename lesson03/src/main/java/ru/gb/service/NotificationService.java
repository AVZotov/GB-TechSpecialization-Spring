package ru.gb.service;

import org.springframework.stereotype.Service;
import ru.gb.model.Customer;

@Service
public class NotificationService {
    public void notifyOnCustomerInsertion(String message){
        System.out.println(message);
    }
}
