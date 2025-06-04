package com.bk.demo.transaction.service;

import com.bk.demo.transaction.model.CreditTransaction;
import com.bk.demo.transaction.repository.CreditTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreditTransactionService {
    private static final Logger log = LoggerFactory.getLogger(CreditTransactionService.class);
    private final CreditTransactionRepository repository;

    public CreditTransactionService(CreditTransactionRepository repository) {
        this.repository = repository;
    }

    public CreditTransaction createTransaction(CreditTransaction creditTransaction) {
        log.info("Attempting to save transaction");
        CreditTransaction creditTransaction1 = repository.save(creditTransaction);
        log.info("Transaction saved successfully");
        return creditTransaction1;
    }

    public List<CreditTransaction> getAllTransactions() {
        return repository.findAll();
    }
}