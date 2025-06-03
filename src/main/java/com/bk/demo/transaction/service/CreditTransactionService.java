package com.bk.demo.transaction.service;

import com.bk.demo.transaction.model.CreditTransaction;
import com.bk.demo.transaction.repository.CreditTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class CreditTransactionService {
    private final CreditTransactionRepository repository;

    public CreditTransactionService(CreditTransactionRepository repository) {
        this.repository = repository;
    }

    public CreditTransaction createTransaction(CreditTransaction transaction) {
        log.info("Attempting to save transaction with id: {}", transaction.getId());
        transaction.setTransactionDate(ZonedDateTime.now());
        return repository.save(transaction);
    }

    public List<CreditTransaction> getAllTransactions() {
        return repository.findAll();
    }
}