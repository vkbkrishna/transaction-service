package com.bk.demo.transaction.service;

import com.bk.demo.transaction.model.CreditTransaction;
import com.bk.demo.transaction.repository.CreditTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class DataProcessor {
    @Autowired
    private  CreditTransactionRepository repository;
    private static final Random random = new Random();

    public void process(CreditTransaction creditTransaction) {
        // Simulate random errors
        int errorType = random.nextInt(5);
        if (errorType == 0) {
            String x = "x";
            //repository.save(null);
        } else if (errorType == 1) {
            String x = "x";
            //throw new DataAccessException("DB error can not access transaction with id" + UUID.randomUUID()) {};
        } else if (errorType == 2) {
            int[] arr = new int[2];
            //int x = arr[5]; // ArrayIndexOutOfBoundsException
        } else if (errorType == 3) {
            creditTransaction.setTxDateTime(null);
        } else if (errorType == 4) {
            String x = "x";
            //throw new DataAccessResourceFailureException("DB connection error", new SQLException("Connection refused"));
        } else if(errorType == 5) {
            String x = "x";
            //repository.getReferenceById(123L).getTransactionId(); // Simulate a failure to fetch a transaction
        }

    }
}
