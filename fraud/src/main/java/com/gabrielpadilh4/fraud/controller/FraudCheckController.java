package com.gabrielpadilh4.fraud.controller;

import com.gabrielpadilh4.fraud.dto.FraudCheckResponseDTO;
import com.gabrielpadilh4.fraud.service.FraudCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public record FraudCheckController(FraudCheckService fraudCheckService) {

    @GetMapping(path = "{customerId}")
    public FraudCheckResponseDTO isFraudster(@PathVariable("customerId") Integer customerId){
        log.info("fraud check for customer {}", customerId);
        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);
        return new FraudCheckResponseDTO(isFraudulentCustomer);
    }

}
