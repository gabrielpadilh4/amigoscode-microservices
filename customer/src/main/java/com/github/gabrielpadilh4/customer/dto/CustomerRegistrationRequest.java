package com.github.gabrielpadilh4.customer.dto;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {}