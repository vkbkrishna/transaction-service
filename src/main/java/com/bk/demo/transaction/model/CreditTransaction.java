// src/main/java/com/bk/demo/transaction/model/CreditTransaction.java
package com.bk.demo.transaction.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Data
@Slf4j
public class CreditTransaction {
    @Id
    private String id;

    @NotBlank
    private String accountNumber;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    private BigDecimal amount;

    private ZonedDateTime transactionDate;

    @NotBlank
    private String transactionType;

    @NotBlank
    private String currency;

    @Size(max = 255)
    private String description;

    @NotBlank
    private String referenceNumber;

    private String merchantName;
    private String merchantCategory;

    @NotBlank
    private String status;

    private String initiatedBy;
    private String approvedBy;
    private ZonedDateTime approvalDate;
    private String channel;
    private String location;
    private String remarks;
    private Boolean isReversed;
    private ZonedDateTime reversalDate;
    private String reversalReason;
    private String externalId;
}