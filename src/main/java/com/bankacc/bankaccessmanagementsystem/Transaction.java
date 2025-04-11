package com.bankacc.bankaccessmanagementsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
* In this class I will define attributes and methods that makes up a transaction*/

public class Transaction {

    private double amount;
    private LocalDateTime date;
    private ETransactionType type;
    private String description;
    private double resultingBalance;

    //constructor
    public Transaction(double amount, ETransactionType type, double resultingBalance) {
        this.amount = amount;
        this.type = type;
        this.resultingBalance = resultingBalance;
        this.date = LocalDateTime.now();
        this.description = "";
    }

    // Overloaded constructor with description
    public Transaction(double amount, ETransactionType type, double resultingBalance, String description) {
        this(amount, type, resultingBalance);
        this.description = description;
    }

    // Getters
    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public ETransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getResultingBalance() {
        return resultingBalance;
    }

    //Creates a toString() method that makes it easy to print each transaction in a readable way
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String transactionType = (type == ETransactionType.DEPOSIT) ? "Deposit" : "Withdrawal";
        return String.format("%s - %s: $%.2f - Balance: $%.2f %s",
                        date.format(formatter),
                        transactionType,
                        amount,
                        resultingBalance,
                        description.isEmpty() ? "" : "- " + description);
    }
}
