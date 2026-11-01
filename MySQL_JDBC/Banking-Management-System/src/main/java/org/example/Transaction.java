package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private int transactionId;
    private int accountId;
    private String transactionRef;
    private String transactionType;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private String transactionStatus;

    public Transaction(int transactionId, int accountId, String transactionRef, String transactionType, BigDecimal amount, LocalDate transactionDate, String transactionStatus) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionRef = transactionRef;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionStatus = transactionStatus;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionRef() {
        return transactionRef;
    }

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "accountId=" + accountId +
                ", transactionId=" + transactionId +
                ", transactionRef=" + transactionRef +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", transactionStatus='" + transactionStatus + '\'' +
                '}';
    }

}
