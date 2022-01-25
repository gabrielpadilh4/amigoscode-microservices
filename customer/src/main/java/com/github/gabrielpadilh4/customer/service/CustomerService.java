package com.github.gabrielpadilh4.customer.service;

import com.github.gabrielpadilh4.customer.dto.CustomerRegistrationRequest;
import com.github.gabrielpadilh4.customer.model.Customer;
import com.github.gabrielpadilh4.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        // todo: check if email is valid
        // todo: check if email is not taken
        customerRepository.save(customer);
    }
}
