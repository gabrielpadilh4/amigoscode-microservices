package com.github.gabrielpadilh4.customer.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message){
        super(message);
    }
}
