package com.bk.demo.transaction.util;

import com.bk.demo.transaction.model.CreditTransaction;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

public class CreditTransactionFaker {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    public static CreditTransaction fake() {
        CreditTransaction tx = new CreditTransaction();
        tx.setId(faker.internet().uuid());
        tx.setAccountNumber(faker.finance().iban());
        tx.setAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 10000)));
        tx.setTransactionDate(ZonedDateTime.now(ZoneId.of("UTC")));
        tx.setTransactionType("CREDIT");
        tx.setCurrency(faker.currency().code());
        tx.setDescription(faker.lorem().sentence());
        tx.setReferenceNumber(faker.idNumber().valid());
        tx.setMerchantName(faker.company().name());
        tx.setMerchantCategory(faker.commerce().department());
        tx.setStatus(random.nextBoolean() ? "PENDING" : "COMPLETED");
        tx.setInitiatedBy(faker.name().username());
        tx.setApprovedBy(faker.name().username());
        tx.setApprovalDate(ZonedDateTime.now(ZoneId.of("UTC")));
        tx.setChannel(random.nextBoolean() ? "ONLINE" : "BRANCH");
        tx.setLocation(faker.address().city());
        tx.setRemarks(faker.lorem().word());
        tx.setIsReversed(random.nextBoolean());
        tx.setReversalDate(null);
        tx.setReversalReason(null);
        tx.setExternalId(faker.internet().uuid());
        return tx;
    }
}