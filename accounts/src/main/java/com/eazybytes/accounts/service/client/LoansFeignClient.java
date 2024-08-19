package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dtos.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")           // // This should mach with name used by service to get registered inside eureka server
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber, @RequestHeader("eazybank-correlation-id") String correlationId);    // This method name can change but request arguements and return type should match with original request
}
