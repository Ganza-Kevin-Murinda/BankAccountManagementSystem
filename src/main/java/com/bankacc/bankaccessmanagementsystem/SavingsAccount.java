package com.bankacc.bankaccessmanagementsystem;

public class SavingsAccount extends BankAccount {

    // Fixed minimum balance, set once during account creation
    private final double minimumBalance;

    // Constructor with constructor chaining
    public SavingsAccount(String accountNumber, String customerName, double initialBalance, double minimumBalance) {
        super(accountNumber, customerName, initialBalance);
        this.minimumBalance = minimumBalance;
    }

    // Constructor with default minimum balance
    public SavingsAccount(String accountNumber, String customerName, double initialBalance) {
        this(accountNumber, customerName, initialBalance, 1000.0); // Default minimum balance
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            return false;
        }

        // Check if withdrawal would break minimum balance rule
        if (balance - amount < minimumBalance) {
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

        // Check if withdrawal would break minimum balance rule
        if (balance - amount < minimumBalance) {
            return false;
        }

        balance -= amount;
        transactionHistory.addTransaction(
                new Transaction(amount, ETransactionType.WITHDRAWAL, balance, description)
        );
        return true;
    }

    //Calculate interest based on current balance and given rate
    @Override
    public double calculateInterest(double rate) {
        double interest = balance * (rate / 100);
        return interest;
    }

    // Apply interest in depositing it to the account
    public boolean applyInterest(double rate) {
        double interest = calculateInterest(rate);
        return deposit(interest, "Interest payment");
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    // String representation of account details
    @Override
    public String toString() {
        return super.toString() + String.format(", Type: Savings, Minimum Balance: $%.2f", minimumBalance);
    }
}
