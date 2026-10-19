package org.example;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Account {

    private int accountId;
    private int accountNumber;
    private int customerId;
    private String accountType;
    private BigDecimal balance;
    private LocalDate accountCreationDate;

    public Account(int accountId, int accountNumber, int customerId, String accountType, BigDecimal balance, LocalDate accountCreationDate) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.accountCreationDate = accountCreationDate;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(LocalDate accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountNumber=" + accountNumber +
                ", customerId='" + customerId + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", accountCreationDate=" + accountCreationDate +
                '}';
    }


}
