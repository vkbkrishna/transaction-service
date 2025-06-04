package com.bk.demo.transaction.service;

import com.bk.demo.transaction.model.CreditTransaction;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreditTransactionClient {

    private final RestTemplate restTemplate;

    public CreditTransactionClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<CreditTransaction> callInternalEndpoint(CreditTransaction creditTransaction) {
        String baseUrl = "http://localhost:8080"; // Your application's base URL
        String endpoint = "/api/credit";

        return restTemplate.postForEntity(baseUrl + endpoint, creditTransaction, CreditTransaction.class);
    }

}

