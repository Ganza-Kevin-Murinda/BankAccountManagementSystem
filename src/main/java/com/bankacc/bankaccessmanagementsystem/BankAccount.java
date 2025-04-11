package com.bankacc.bankaccessmanagementsystem;

public abstract class BankAccount implements IBankOperations {

    private String accountNumber;
    private String customerName;
    protected double balance;
    protected TransactionLinkedList transactionHistory;

    //constructor
    public BankAccount(String accountNumber, String customerName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.balance = initialBalance;
        this.transactionHistory = new TransactionLinkedList();

        // Record initial deposit
        if (initialBalance > 0) {
            this.transactionHistory.addTransaction(
                    new Transaction(initialBalance, ETransactionType.DEPOSIT, initialBalance, "Initial deposit")
            );
        }
    }

    // Implementing common methods from the interface
    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    // Default implementation for deposit (common across account types)
    @Override
    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        transactionHistory.addTransaction(
                new Transaction(amount, ETransactionType.DEPOSIT, balance)
        );
        return true;
    }

    // Overloaded deposit method with description
    public boolean deposit(double amount, String description) {
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        transactionHistory.addTransaction(
                new Transaction(amount, ETransactionType.DEPOSIT, balance, description)
        );
        return true;
    }

    // Abstract withdraw method to be implemented by subclasses
    @Override
    public abstract boolean withdraw(double amount);

    // Overloaded withdraw method with description
    public boolean withdraw(double amount, String description) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;

        transactionHistory.addTransaction(
                new Transaction(amount, ETransactionType.WITHDRAWAL, balance, description)
        );

        return true;
    }

    // Get customer name
    public String getCustomerName() {
        return customerName;
    }

    // Get recent transactions
    public Transaction[] getRecentTransactions(int n) {
        return transactionHistory.getLastNTransactions(n);
    }

    // Get all transactions
    public Transaction[] getAllTransactions() {
        return transactionHistory.getAllTransactions();
    }

    // Calculate interest (to be implemented by specific account types)
    public abstract double calculateInterest(double rate);

    @Override
    public String toString() {
        return String.format("Account Number: %s, Customer: %s, Balance: $%.2f",
                accountNumber, customerName, balance);
    }
}
