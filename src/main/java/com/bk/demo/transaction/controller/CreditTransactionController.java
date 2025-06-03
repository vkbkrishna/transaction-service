// src/main/java/com/bk/demo/transaction/controller/CreditTransactionController.java
package com.bk.demo.transaction.controller;

import com.bk.demo.transaction.model.CreditTransaction;
import com.bk.demo.transaction.service.CreditTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/credit")
@Slf4j
public class CreditTransactionController {
    private final CreditTransactionService service;

    public CreditTransactionController(CreditTransactionService service) {
        this.service = service;
    }

    @PostMapping
    public CreditTransaction create(@Valid @RequestBody CreditTransaction transaction) {
        log.info("Create credit transaction with id: {}", transaction.getId());
        return service.createTransaction(transaction);
    }

    @GetMapping
    public List<CreditTransaction> getAll() {
        return service.getAllTransactions();
    }
}