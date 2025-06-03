package com.bk.demo.transaction.service;

import com.bk.demo.transaction.repository.CreditTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class DataProcessor {
    @Autowired
    private  CreditTransactionRepository repository;
    private static final Random random = new Random();

    public void process() {
        // Simulate random errors
        int errorType = random.nextInt(15);
        if (errorType == 0) {
            repository.save(null);
        } else if (errorType == 1) {
            throw new DataAccessException("DB error can not access transaction with id" + UUID.randomUUID()) {};
        } else if (errorType == 2) {
            int[] arr = new int[2];
            int x = arr[5]; // ArrayIndexOutOfBoundsException
        } else if (errorType == 3) {
            String s = null;
            s.length(); // NullPointerException
        }else if (errorType == 4) {
            throw new DataAccessResourceFailureException("DB connection error", new SQLException("Connection refused"));
        }else if(errorType == 5) {
            repository.getReferenceById(123L).getId(); // Simulate a failure to fetch a transaction
        }

    }
}
