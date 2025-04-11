package com.bankacc.bankaccessmanagementsystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FixedDepositAccount extends BankAccount {
    private LocalDate maturityDate;
    private double interestRate;

    // Constructor with constructor chaining
    public FixedDepositAccount(String accountNumber, String customerName, double depositAmount,
                               LocalDate maturityDate, double interestRate) {
        super(accountNumber, customerName, depositAmount);
        this.maturityDate = maturityDate;
        this.interestRate = interestRate;
    }

    // Constructor with default values (1 year term)
    public FixedDepositAccount(String accountNumber, String customerName, double depositAmount) {
        this(accountNumber, customerName, depositAmount,
                LocalDate.now().plusYears(1), 5.0); // 1 year maturity, 5% interest
    }

    @Override
    public boolean withdraw(double amount) {
        // Cannot withdraw before maturity date
        if (LocalDate.now().isBefore(maturityDate)) {
            return false;
        }

        if (amount <= 0 || amount > balance) {
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
        // Cannot withdraw before maturity date
        if (LocalDate.now().isBefore(maturityDate)) {
            return false;
        }

        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        transactionHistory.addTransaction(
                new Transaction(amount, ETransactionType.WITHDRAWAL, balance, description)
        );
        return true;
    }

    @Override
    public boolean deposit(double amount) {
        // Usually fixed deposits don't allow additional deposits
        return false;
    }

    @Override
    public boolean deposit(double amount, String description) {
        // Usually fixed deposits don't allow additional deposits
        return false;
    }

    @Override
    public double calculateInterest(double rate) {
        // Use the account's interest rate if rate parameter is 0
        double effectiveRate = (rate > 0) ? rate : this.interestRate;

        // Calculate interest based on time to maturity
        long daysToMaturity = ChronoUnit.DAYS.between(LocalDate.now(), maturityDate);
        double yearsToMaturity = daysToMaturity / 365.0;

        // Simple interest calculation
        return balance * (effectiveRate / 100) * yearsToMaturity;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public boolean isMatured() {
        return !LocalDate.now().isBefore(maturityDate);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Type: Fixed Deposit, Maturity Date: %s, Interest Rate: %.2f%%",
                maturityDate, interestRate);
    }
}
