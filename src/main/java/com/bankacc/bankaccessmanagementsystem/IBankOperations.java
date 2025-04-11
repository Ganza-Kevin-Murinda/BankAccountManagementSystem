package com.bankacc.bankaccessmanagementsystem;

/*
* In this Interface I will define the core methods/contracts for all the account types
*/

public interface IBankOperations {
    boolean deposit(double amount);
    boolean withdraw(double amount);
    double getBalance();
    String getAccountNumber();
}
