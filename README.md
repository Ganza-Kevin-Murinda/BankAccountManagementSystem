# 🏦 Bank Account Management System (JavaFX)

This is a JavaFX desktop application for managing different types of bank accounts, including Savings, Current, and Fixed Deposit accounts. The system supports account creation, deposit and withdrawal operations, and viewing transaction history.

---

## ✨ Features

- **Account Types:**
  - 💰 Savings Account (with minimum balance requirement)
  - 🧾 Current Account (with overdraft limit)
  - 📈 Fixed Deposit Account (with interest rate and term)

- **Functionalities:**
  - Create new accounts with type-specific details
  - View and select existing accounts
  - Deposit and withdraw funds
  - Add optional descriptions to transactions
  - View recent or full transaction history
  - Live balance updates
  - Basic input validation and error alerts

---

## 📸 Screenshots

![image](https://github.com/user-attachments/assets/11bb618e-b6b5-4bff-85d0-cba334e0f6fe)
![image](https://github.com/user-attachments/assets/ae328ba3-aa6d-42f8-a89c-335eb50078e0)


---

## 🛠 Technologies Used
- Java (OOP)
- JavaFX (GUI)
- Custom Linked List implementation for transactions

## 📂 Project Structure
```
├── BankApp.java                 # JavaFX UI
├── BankManager.java            # Manages all bank accounts
├── BankAccount.java            # Abstract class for accounts
├── SavingsAccount.java         # Savings account logic
├── CurrentAccount.java         # Current account logic
├── FixedDepositAccount.java    # Fixed Deposit logic
├── Transaction.java            # Transaction data
├── TransactionLinkedList.java  # Linked list of transactions
```

## 🚀 Getting Started

### Prerequisites

- Java 8 or later
- JavaFX SDK (configured in your IDE or command line)

### Running the App

1. Clone the repository:
   ```bash
   https://github.com/Ganza-Kevin-Murinda/BankAccountManagementSystem.git
   cd BankAccountManagementSystem
   ```
   
