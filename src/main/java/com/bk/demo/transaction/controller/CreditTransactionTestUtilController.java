package com.bk.demo.transaction.controller;

import com.bk.demo.transaction.model.CreditTransaction;
import com.bk.demo.transaction.service.CreditTransactionService;
import com.bk.demo.transaction.service.DataProcessor;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/credit-transactions")
@Slf4j
public class CreditTransactionTestUtilController {

    private final CreditTransactionService service;
    private final Validator validator;

    @Autowired
    private DataProcessor dataProcessor;

    public CreditTransactionTestUtilController(CreditTransactionService service, Validator validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping("/test-generate")
    public Map<String, Object> generateTestTransactions(@RequestParam(defaultValue = "10") int count) {
        int success = 0, failed = 0;
        List<String> errors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            CreditTransaction tx = generateTransaction(i);
            try {
                dataProcessor.process();

                Set<ConstraintViolation<CreditTransaction>> violations = validator.validate(tx);
                if (!violations.isEmpty()) {
                    failed++;
                    for (ConstraintViolation<CreditTransaction> v : violations) {
                        String err = "Transaction " + tx.getId() + ": " + v.getPropertyPath() + " " + v.getMessage();
                        errors.add(err);
                        throw new ConstraintViolationException(violations);
                    }
                    continue;
                }
                service.createTransaction(tx);
                log.info("Transaction {} created successfully", tx.getId());
                success++;
            } catch (Exception e) {

                log.error("Transaction with traceId {} failed: {}", tx.getId(), e.getMessage(), e);
                errors.add("Transaction with traceId " + tx.getId() + ": " + e.toString());
                failed++;
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("failed", failed);
        result.put("errors", errors);
        return result;
    }


    private CreditTransaction generateTransaction(int i) {
        CreditTransaction tx = com.bk.demo.transaction.util.CreditTransactionFaker.fake();
        if (i % 2 == 0) {
            // Valid - do nothing, already valid
        } else if (i % 8 == 1) {
            // Missing required fields
            tx.setAccountNumber("");
            tx.setAmount(null);
            tx.setTransactionType(null);
            tx.setCurrency("");
            tx.setReferenceNumber(null);
            tx.setStatus("");
        } else if (i % 8 == 2) {
            // Invalid amount
            tx.setAmount(new BigDecimal("-10.00"));
        } else if(i % 23 == 0){
            // Description too long
            tx.setDescription("a".repeat(300));
        }
        return tx;
    }
}