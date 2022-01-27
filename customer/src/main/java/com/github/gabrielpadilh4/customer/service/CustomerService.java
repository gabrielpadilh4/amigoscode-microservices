package com.github.gabrielpadilh4.customer.service;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.gabrielpadilh4.customer.dto.CustomerRegistrationRequest;
import com.github.gabrielpadilh4.customer.dto.FraudCheckResponseDTO;
import com.github.gabrielpadilh4.customer.exception.FraudsterCustomerException;
import com.github.gabrielpadilh4.customer.exception.InvalidEmailException;
import com.github.gabrielpadilh4.customer.model.Customer;
import com.github.gabrielpadilh4.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public record CustomerService(CustomerRepository customerRepository,
                              RestTemplate restTemplate) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        if(!isValidEmail(customer.getEmail())){
            throw new InvalidEmailException("Invalid e-mail format");
        }

        if(isEmailAlreadyTaken(customer.getEmail())){
            throw new InvalidEmailException("E-mail already taken");
        }

        customerRepository.saveAndFlush(customer);

        if(isCustomerFraudster(customer.getId())){
            throw new FraudsterCustomerException("Fraudster customer");
        }

        // todo: send notification
    }

    private boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    private boolean isCustomerFraudster(Integer customerId){
        try{
            FraudCheckResponseDTO fraudCheckResponseDTO = restTemplate.getForObject(
                    "http://FRAUD/api/v1/fraud-check/{customerId}",
                    FraudCheckResponseDTO.class,
                    customerId
            );

            return fraudCheckResponseDTO.isFraudster();
        }catch(Exception e){
            throw new FraudsterCustomerException("Could not check if customer is fraudster. Error: " + e.getMessage());
        }
    }

    private boolean isEmailAlreadyTaken(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);

        return customer.isPresent();
    }
}
