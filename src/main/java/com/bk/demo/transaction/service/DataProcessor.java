package com.bk.demo.transaction.service;

import com.bk.demo.transaction.model.CreditTransaction;
import com.bk.demo.transaction.repository.CreditTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class DataProcessor {
    @Autowired
    private  CreditTransactionRepository repository;
    private static final Random random = new Random(10);

    public void process(CreditTransaction creditTransaction) {
        // Simulate random errors
        int errorType = random.nextInt(10);
        if (errorType == 0) {
            creditTransaction.setTransactionId(0);
        } else if (errorType == 1) {
            creditTransaction.setCustomerId(UUID.randomUUID().toString());
        } else if (errorType == 2) {
            creditTransaction.setTxDateTime(null);
        }

    }
}
