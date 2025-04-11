package com.bankacc.bankaccessmanagementsystem;

public class CurrentAccount extends BankAccount {
    private final double overdraftLimit;

    // Constructor with constructor chaining
    public CurrentAccount(String accountNumber, String customerName, double initialBalance, double overdraftLimit) {
        super(accountNumber, customerName, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    // Constructor with default overdraft limit
    public CurrentAccount(String accountNumber, String customerName, double initialBalance) {
        this(accountNumber, customerName, initialBalance, 5000.0); // Default overdraft limit
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }

        // Check if withdrawal would exceed overdraft limit
        if (balance - amount < -overdraftLimit) {
            return false;
        }

        balance -= amount;
        transactionHistory.addTransaction(
                new Transaction(amount, ETransactionType.WITHDRAWAL, balance)
        );
        return true;
    }

    @Override
    public boolean withdraw(double amount, String description) {
        if (amount <= 0) {
            return false;
        }

        // Check if withdrawal would exceed overdraft limit
        if (balance - amount < -overdraftLimit) {
            return false;
        }

        balance -= amount;
        transactionHistory.addTransaction(
                new Transaction(amount, ETransactionType.WITHDRAWAL, balance, description)
        );
        return true;
    }

    @Override
    public double calculateInterest(double rate) {
        // Current accounts typically don't earn interest
        return 0.0;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Type: Current, Overdraft Limit: $%.2f", overdraftLimit);
    }
}
