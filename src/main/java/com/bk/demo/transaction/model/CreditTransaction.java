package com.bk.demo.transaction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@Slf4j
public class CreditTransaction {
    private long index;
    @Id
    private long transactionId;
    private String txDateTime;
    private String customerId;
    private long terminalId;
    private double txAmount;
    private int txTimeSeconds;
    private int txTimeDays;
    private boolean txFraud;
    private int txFraudScenario;

    // Default constructor
    public CreditTransaction() {
    }

    // Parameterized constructor
    public CreditTransaction(long transactionId, String txDateTime, String customerId,
                             long terminalId, double txAmount, int txTimeSeconds,
                             int txTimeDays, boolean txFraud, int txFraudScenario) {
        this.transactionId = transactionId;
        this.txDateTime = txDateTime;
        this.customerId = customerId;
        this.terminalId = terminalId;
        this.txAmount = txAmount;
        this.txTimeSeconds = txTimeSeconds;
        this.txTimeDays = txTimeDays;
        this.txFraud = txFraud;
        this.txFraudScenario = txFraudScenario;
    }

    // Getters and Setters
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTxDateTime() {
        return txDateTime;
    }

    public void setTxDateTime(String txDateTime) {
        this.txDateTime = txDateTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public long getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(long terminalId) {
        this.terminalId = terminalId;
    }

    public double getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(double txAmount) {
        this.txAmount = txAmount;
    }

    public int getTxTimeSeconds() {
        return txTimeSeconds;
    }

    public void setTxTimeSeconds(int txTimeSeconds) {
        this.txTimeSeconds = txTimeSeconds;
    }

    public int getTxTimeDays() {
        return txTimeDays;
    }

    public void setTxTimeDays(int txTimeDays) {
        this.txTimeDays = txTimeDays;
    }

    public boolean isTxFraud() {
        return txFraud;
    }

    public void setTxFraud(boolean txFraud) {
        this.txFraud = txFraud;
    }

    public int getTxFraudScenario() {
        return txFraudScenario;
    }

    public void setTxFraudScenario(int txFraudScenario) {
        this.txFraudScenario = txFraudScenario;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", txDateTime=" + txDateTime +
                ", customerId=" + customerId +
                ", terminalId=" + terminalId +
                ", txAmount=" + txAmount +
                ", txTimeSeconds=" + txTimeSeconds +
                ", txTimeDays=" + txTimeDays +
                ", txFraud=" + txFraud +
                ", txFraudScenario=" + txFraudScenario +
                '}';
    }
}
