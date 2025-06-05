package com.bk.demo.transaction.controller;

import com.bk.demo.transaction.model.CreditTransaction;
import com.bk.demo.transaction.service.CreditTransactionClient;
import com.bk.demo.transaction.service.DataProcessor;
import com.bk.demo.transaction.util.CSVReader;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/credit-transactions")
@Slf4j
public class CreditTransactionTestUtilController {

    private static final Logger log = LoggerFactory.getLogger(CreditTransactionTestUtilController.class);
    private final CreditTransactionClient client;
    private final Validator validator;
    CSVReader<CreditTransaction> csvReader = new CSVReader<CreditTransaction>(CreditTransaction.class, true);

    @Autowired
    private DataProcessor dataProcessor;

    public CreditTransactionTestUtilController(CreditTransactionClient client, Validator validator) {
        this.client = client;
        this.validator = validator;
    }

    @PostMapping("/test-generate")
    public Map<String, Object> generateTestTransactions(@RequestParam(defaultValue = "10") int count) throws Exception {
        AtomicInteger success = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        List<String> errors = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sample.csv")){
            BufferedReader reader = new BufferedReader((new InputStreamReader(inputStream)));
            CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().build();

            CSVParser csvParser =  format.parse(reader);
            AtomicInteger previousTime = new AtomicInteger();
            previousTime.set(0);
            csvParser.stream().iterator().forEachRemaining( record -> {
                CreditTransaction creditTransaction = null;
                try {
                    creditTransaction = CreditTransaction.class.getDeclaredConstructor().newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                Map<String, Integer> headerMap = csvParser.getHeaderMap();
                for (Field field : CreditTransaction.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (headerMap.containsKey(field.getName())) {
                        String value = record.get(field.getName());
                        try {
                            csvReader.setFieldValue(field, creditTransaction, value);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                try {
                    dataProcessor.process(creditTransaction);
                    int currentTime = creditTransaction.getTxTimeSeconds();
                    creditTransaction.setTxTimeSeconds(creditTransaction.getTxTimeSeconds() - previousTime.get());
                    previousTime.set(currentTime);
                    client.callInternalEndpoint(creditTransaction);
                    Set<ConstraintViolation<CreditTransaction>> violations = validator.validate(creditTransaction);
                    if (!violations.isEmpty()) {
                        failed.getAndIncrement();
                        for (ConstraintViolation<CreditTransaction> v : violations) {
                            String err = "Transaction " + creditTransaction.getTransactionId() + ": " + v.getPropertyPath() + " " + v.getMessage();
                            errors.add(err);
                        }
                    }
                    log.debug("Transaction {} read successfully", creditTransaction.getTransactionId());
                    success.getAndIncrement();
                } catch (Exception e) {
                    log.error("Transaction with Id {} failed: {}", creditTransaction.getTransactionId(), e.getMessage(), e);
                    errors.add("Transaction with Id " + creditTransaction.getTransactionId() + ": " + e.toString());
                    failed.getAndIncrement();
                }
            });
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("failed", failed);
        result.put("errors", errors);
        return result;
    }
}