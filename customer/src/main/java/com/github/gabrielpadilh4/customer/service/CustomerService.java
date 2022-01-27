package com.github.gabrielpadilh4.customer.service;

import com.github.gabrielpadilh4.customer.dto.CustomerRegistrationRequest;
import com.github.gabrielpadilh4.customer.dto.FraudCheckResponseDTO;
import com.github.gabrielpadilh4.customer.model.Customer;
import com.github.gabrielpadilh4.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository customerRepository,
                              RestTemplate restTemplate) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        // todo: check if email is valid
        // todo: check if email is not taken
        customerRepository.saveAndFlush(customer);
        // todo: check if fraudster
        FraudCheckResponseDTO fraudCheckResponseDTO = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponseDTO.class,
                customer.getId()
        );

        if(fraudCheckResponseDTO.isFraudster()){
            throw new IllegalStateException("Fraudster");
        }

        // todo: send notification
    }
}
