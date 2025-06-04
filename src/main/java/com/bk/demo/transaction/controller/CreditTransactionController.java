package com.bk.demo.transaction.controller;

import com.bk.demo.transaction.model.CreditTransaction;
import com.bk.demo.transaction.service.CreditTransactionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit")
@Slf4j
public class CreditTransactionController {
    private static final Logger log = LoggerFactory.getLogger(CreditTransactionController.class);
    private final CreditTransactionService service;

    public CreditTransactionController(CreditTransactionService service) {
        this.service = service;
    }

    @PostMapping
    public CreditTransaction create(@Valid @RequestBody CreditTransaction creditTransaction) {
        MDC.clear();
        MDC.put("TransactionId", Long.toString(creditTransaction.getTransactionId()));
        log.info("Processing transaction from terminal {}", creditTransaction.getTerminalId());
        CreditTransaction creditTransaction1 = null;
        try {
            String dateOfTransaction = creditTransaction.getTxDateTime().substring(0, 9);
            log.info("Transaction time stamp is {}", dateOfTransaction);
        } catch (Exception e){
            log.error("ERR101 :: Bad data :: Unable to get date from transaction date time field", e);
            throw e;
        }
        creditTransaction1 = service.createTransaction(creditTransaction);
        log.info("Transaction has been processed in {} seconds", creditTransaction.getTxTimeSeconds());
        return creditTransaction1;
    }

}