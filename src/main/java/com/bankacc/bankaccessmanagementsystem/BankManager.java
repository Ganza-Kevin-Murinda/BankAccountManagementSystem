package com.bankacc.bankaccessmanagementsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class BankManager {
    //stores subclass not the super class
    private Map<String, BankAccount> accounts;
    private int lastAccountNumber = 1000;

    public BankManager() {
        accounts = new HashMap<>();
    }

    // Create a new savings account
    public SavingsAccount createSavingsAccount(String customerName, double initialBalance, double minimumBalance) {
        String accountNumber = generateAccountNumber("SAV");
        SavingsAccount account = new SavingsAccount(accountNumber, customerName, initialBalance, minimumBalance);
        accounts.put(accountNumber, account);
        return account;
    }

    // Create a new current account
    public CurrentAccount createCurrentAccount(String customerName, double initialBalance, double overdraftLimit) {
        String accountNumber = generateAccountNumber("CUR");
        CurrentAccount account = new CurrentAccount(accountNumber, customerName, initialBalance, overdraftLimit);
        accounts.put(accountNumber, account);
        return account;
    }

    // Create a new fixed deposit account
    public FixedDepositAccount createFixedDepositAccount(String customerName, double depositAmount,
                                                         int termInMonths, double interestRate) {
        String accountNumber = generateAccountNumber("FD");
        LocalDate maturityDate = LocalDate.now().plusMonths(termInMonths);
        FixedDepositAccount account = new FixedDepositAccount(
                accountNumber, customerName, depositAmount, maturityDate, interestRate);
        accounts.put(accountNumber, account);
        return account;
    }

    // Generate a unique account number
    private String generateAccountNumber(String prefix) {
        lastAccountNumber++;
        return prefix + lastAccountNumber;
    }

    // Get an account by its account number
    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    // Get all accounts
    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    // Get all accounts for a specific customer
    public List<BankAccount> getAccountsByCustomer(String customerName) {
        List<BankAccount> customerAccounts = new ArrayList<>();
        for (BankAccount account : accounts.values()) {
            if (account.getCustomerName().equalsIgnoreCase(customerName)) {
                customerAccounts.add(account);
            }
        }
        return customerAccounts;
    }

    // Apply interest to all savings accounts
    public void applySavingsInterest(double rate) {
        for (BankAccount account : accounts.values()) {
            // checks if the account object is of type SavingsAccount
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).applyInterest(rate);
            }
        }
    }
}
