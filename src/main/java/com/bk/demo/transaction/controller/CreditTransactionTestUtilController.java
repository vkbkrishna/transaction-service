package com.bk.demo.transaction.controller;

import com.bk.demo.transaction.model.CreditTransaction;
import com.bk.demo.transaction.service.CreditTransactionClient;
import com.bk.demo.transaction.service.DataProcessor;
import com.bk.demo.transaction.util.CSVReader;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;

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
        int success = 0, failed = 0;
        List<String> errors = new ArrayList<>();
        try (CSVParser csvParser = csvReader.parseFile("sample.csv")){
            int previousTime = 0;
            Iterator<CSVRecord> csvRecordIterator = csvParser.iterator();
            while (csvRecordIterator.hasNext()){
                CSVRecord record = csvRecordIterator.next();
                CreditTransaction creditTransaction = CreditTransaction.class.getDeclaredConstructor().newInstance();
                Map<String, Integer> headerMap = csvParser.getHeaderMap();
                for (Field field : CreditTransaction.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (headerMap.containsKey(field.getName())) {
                        String value = record.get(field.getName());
                        csvReader.setFieldValue(field, creditTransaction, value);
                    }
                }
                try {
                    dataProcessor.process(creditTransaction);
                    int currentTime = creditTransaction.getTxTimeSeconds();
                    creditTransaction.setTxTimeSeconds(creditTransaction.getTxTimeSeconds() - previousTime);
                    previousTime = currentTime;
                    client.callInternalEndpoint(creditTransaction);
                    Set<ConstraintViolation<CreditTransaction>> violations = validator.validate(creditTransaction);
                    if (!violations.isEmpty()) {
                        failed++;
                        for (ConstraintViolation<CreditTransaction> v : violations) {
                            String err = "Transaction " + creditTransaction.getTransactionId() + ": " + v.getPropertyPath() + " " + v.getMessage();
                            errors.add(err);
                            throw new ConstraintViolationException(violations);
                        }
                        continue;
                    }
                    log.debug("Transaction {} read successfully", creditTransaction.getTransactionId());
                    success++;
                } catch (Exception e) {
                    log.error("Transaction with Id {} failed: {}", creditTransaction.getTransactionId(), e.getMessage(), e);
                    errors.add("Transaction with Id " + creditTransaction.getTransactionId() + ": " + e.toString());
                    failed++;
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("failed", failed);
        result.put("errors", errors);
        return result;
    }
}