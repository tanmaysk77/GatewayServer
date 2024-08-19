package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dtos.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")           // This should mach with name used by service to get registered inside eureka server
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber, @RequestHeader("eazybank-correlation-id") String correlationId);    // This method name can change but paraeters should match
}

