package com.bankacc.bankaccessmanagementsystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.time.LocalDate;

public class BankApp extends Application {

    private BankManager bankManager;
    private BankAccount currentAccount;
    private ComboBox<String> accountSelector;
    private TextArea transactionHistoryArea;
    private Label balanceLabel;

    // CSS style strings for UI elements
    private final String HEADER_STYLE = "-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #2c3e50;";
    private final String SUBHEADER_STYLE = "-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #34495e;";
    private final String LABEL_STYLE = "-fx-font-size: 12px; -fx-text-fill: #2c3e50;";
    private final String BALANCE_STYLE = "-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #2980b9;";

    // Button styles
    private final String CREATE_BUTTON_STYLE = "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;";
    private final String CLEAR_BUTTON_STYLE = "-fx-background-color: #7f8c8d; -fx-text-fill: white;";
    private final String DEPOSIT_BUTTON_STYLE = "-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;";
    private final String WITHDRAW_BUTTON_STYLE = "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;";
    private final String VIEW_BUTTON_STYLE = "-fx-background-color: #3498db; -fx-text-fill: white;";
    private final String FILTER_BUTTON_STYLE = "-fx-background-color: #9b59b6; -fx-text-fill: white;";
    private final String REFRESH_BUTTON_STYLE = "-fx-background-color: #f39c12; -fx-text-fill: white;";

    @Override
    public void start(Stage primaryStage) {
        bankManager = new BankManager();

        // Create UI
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Top section - Account creation
        VBox topSection = createAccountCreationSection();
        root.setTop(topSection);

        // Center section - Transaction history
        VBox centerSection = createTransactionHistorySection();
        root.setCenter(centerSection);

        // Bottom section - Transaction operations
        VBox bottomSection = createTransactionOperationsSection();
        root.setBottom(bottomSection);

        // Wrap the BorderPane in a ScrollPane to enable scrolling
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);  // Makes the content fit the width of the ScrollPane
        scrollPane.setPrefViewportHeight(600);  // Set preferred viewport height

        Scene scene = new Scene(scrollPane, 700, 650);
        primaryStage.setTitle("Bank Account Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createAccountCreationSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));
        section.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label titleLabel = new Label("Create New Account");
        titleLabel.setStyle(HEADER_STYLE);

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 0, 10, 0));

        // Set style for all labels
        Label customerNameLabel = new Label("Customer Name:");
        Label initialBalanceLabel = new Label("Initial Balance:");
        Label accountTypeLabel = new Label("Account Type:");

        customerNameLabel.setStyle(LABEL_STYLE);
        initialBalanceLabel.setStyle(LABEL_STYLE);
        accountTypeLabel.setStyle(LABEL_STYLE);

        // Customer name field
        grid.add(customerNameLabel, 0, 0);
        TextField customerNameField = new TextField();
        customerNameField.setPromptText("Enter customer name");
        grid.add(customerNameField, 1, 0);

        // Initial balance field
        grid.add(initialBalanceLabel, 0, 1);
        TextField initialBalanceField = new TextField();
        initialBalanceField.setPromptText("Enter initial deposit amount");
        grid.add(initialBalanceField, 1, 1);

        // Account type selector
        grid.add(accountTypeLabel, 0, 2);
        ComboBox<String> accountTypeSelector = new ComboBox<>();
        accountTypeSelector.getItems().addAll("Savings Account", "Current Account", "Fixed Deposit");
        accountTypeSelector.setValue("Savings Account");
        accountTypeSelector.setMaxWidth(Double.MAX_VALUE);
        grid.add(accountTypeSelector, 1, 2);

        // Additional parameters section (changes based on account type)
        Label additionalParamLabel = new Label("Minimum Balance:");
        additionalParamLabel.setStyle(LABEL_STYLE);
        TextField additionalParamField = new TextField("1000");
        grid.add(additionalParamLabel, 0, 3);
        grid.add(additionalParamField, 1, 3);

        // Term selector for fixed deposit (initially hidden)
        Label termLabel = new Label("Term (months):");
        termLabel.setStyle(LABEL_STYLE);
        ComboBox<Integer> termSelector = new ComboBox<>();
        termSelector.getItems().addAll(3, 6, 12, 24, 36, 60);
        termSelector.setValue(12);
        termSelector.setMaxWidth(Double.MAX_VALUE);
        grid.add(termLabel, 0, 4);
        grid.add(termSelector, 1, 4);
        termLabel.setVisible(false);
        termSelector.setVisible(false);

        // Change additional parameter based on account type
        accountTypeSelector.setOnAction(e -> {
            String selectedType = accountTypeSelector.getValue();
            switch (selectedType) {
                case "Savings Account":
                    additionalParamLabel.setText("Minimum Balance:");
                    additionalParamField.setText("1000");
                    termLabel.setVisible(false);
                    termSelector.setVisible(false);
                    break;
                case "Current Account":
                    additionalParamLabel.setText("Overdraft Limit:");
                    additionalParamField.setText("5000");
                    termLabel.setVisible(false);
                    termSelector.setVisible(false);
                    break;
                case "Fixed Deposit":
                    additionalParamLabel.setText("Interest Rate (%):");
                    additionalParamField.setText("5.0");
                    termLabel.setVisible(true);
                    termSelector.setVisible(true);
                    break;
            }
        });

        Button createAccountButton = new Button("Create Account");
        createAccountButton.setStyle(CREATE_BUTTON_STYLE);

        Button clearFormButton = new Button("Clear Form");
        clearFormButton.setStyle(CLEAR_BUTTON_STYLE);

        clearFormButton.setOnAction(e -> {
            customerNameField.clear();
            initialBalanceField.clear();
            additionalParamField.setText("1000"); // Reset to default
            accountTypeSelector.setValue("Savings Account");
            termLabel.setVisible(false);
            termSelector.setVisible(false);
            termSelector.setValue(12);
        });

        createAccountButton.setOnAction(e -> {
            try {
                String customerName = customerNameField.getText();
                if (customerName.trim().isEmpty()) {
                    showAlert("Invalid Input", "Please enter a customer name.", Alert.AlertType.ERROR);
                    return;
                }

                double initialBalance = Double.parseDouble(initialBalanceField.getText());
                double additionalParam = Double.parseDouble(additionalParamField.getText());

                BankAccount newAccount = null;
                switch (accountTypeSelector.getValue()) {
                    case "Savings Account":
                        newAccount = bankManager.createSavingsAccount(customerName, initialBalance, additionalParam);
                        break;
                    case "Current Account":
                        newAccount = bankManager.createCurrentAccount(customerName, initialBalance, additionalParam);
                        break;
                    case "Fixed Deposit":
                        int term = termSelector.getValue();
                        newAccount = bankManager.createFixedDepositAccount(
                                customerName, initialBalance, term, additionalParam);
                        break;
                }

                if (newAccount != null) {
                    updateAccountSelector();
                    // Clear form fields after successful account creation
                    customerNameField.clear();
                    initialBalanceField.clear();
                    additionalParamField.setText("1000");
                    showAlert("Account Created",
                            "Account successfully created!\nAccount Number: " + newAccount.getAccountNumber(),
                            Alert.AlertType.INFORMATION);
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter valid numbers for amount fields.", Alert.AlertType.ERROR);
            }
        });

        // Account selector
        HBox selectorBox = new HBox(10);
        selectorBox.setPadding(new Insets(10, 0, 0, 0));

        Label selectLabel = new Label("Select Account:");
        selectLabel.setStyle(LABEL_STYLE);
        accountSelector = new ComboBox<>();
        accountSelector.setMaxWidth(Double.MAX_VALUE);
        accountSelector.setPrefWidth(300);
        HBox.setHgrow(accountSelector, Priority.ALWAYS);

        Button refreshButton = new Button("Refresh List");
        refreshButton.setStyle(REFRESH_BUTTON_STYLE);

        refreshButton.setOnAction(e -> updateAccountSelector());

        accountSelector.setOnAction(e -> {
            String selectedItem = accountSelector.getValue();
            if (selectedItem != null) {
                String accountNumber = selectedItem.split(" - ")[0];
                currentAccount = bankManager.getAccount(accountNumber);
                updateTransactionHistory();
                updateBalanceLabel();
            }
        });

        selectorBox.getChildren().addAll(selectLabel, accountSelector, refreshButton);

        // Create button layout
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(createAccountButton, clearFormButton);

        section.getChildren().addAll(titleLabel, grid, buttonBox, selectorBox);
        return section;
    }

    private VBox createTransactionHistorySection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(15, 10, 15, 10));
        section.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label titleLabel = new Label("Transaction History");
        titleLabel.setStyle(SUBHEADER_STYLE);

        transactionHistoryArea = new TextArea();
        transactionHistoryArea.setEditable(false);
        transactionHistoryArea.setPrefHeight(200);
        transactionHistoryArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

        balanceLabel = new Label("Current Balance: $0.00");
        balanceLabel.setStyle(BALANCE_STYLE);

        // Custom number of transactions
        HBox countBox = new HBox(10);
        countBox.setPadding(new Insets(5, 0, 5, 0));

        Label countLabel = new Label("Number of transactions:");
        countLabel.setStyle(LABEL_STYLE);
        TextField countField = new TextField("5");
        countField.setPrefWidth(60);

        Button viewCustomButton = new Button("View Recent");
        viewCustomButton.setStyle(VIEW_BUTTON_STYLE);

        Button viewAllButton = new Button("View All");
        viewAllButton.setStyle(VIEW_BUTTON_STYLE);

        countBox.getChildren().addAll(countLabel, countField, viewCustomButton, viewAllButton);

        // Filtering options
        VBox filterBox = new VBox(10);
        filterBox.setPadding(new Insets(5, 0, 5, 0));

        Label filterHeaderLabel = new Label("Filter Transactions");
        filterHeaderLabel.setStyle(LABEL_STYLE);

        HBox typeFilterBox = new HBox(10);
        Label filterTypeLabel = new Label("Type:");
        filterTypeLabel.setStyle(LABEL_STYLE);
        ComboBox<String> filterType = new ComboBox<>();
        filterType.getItems().addAll("All Types", "Deposits Only", "Withdrawals Only");
        filterType.setValue("All Types");

        typeFilterBox.getChildren().addAll(filterTypeLabel, filterType);

        HBox dateFilterBox = new HBox(10);
        Label startDateLabel = new Label("From:");
        startDateLabel.setStyle(LABEL_STYLE);
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPrefWidth(120);

        Label endDateLabel = new Label("To:");
        endDateLabel.setStyle(LABEL_STYLE);
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPrefWidth(120);

        dateFilterBox.getChildren().addAll(startDateLabel, startDatePicker, endDateLabel, endDatePicker);

        Button applyFilterButton = new Button("Apply Filter");
        applyFilterButton.setStyle(FILTER_BUTTON_STYLE);

        filterBox.getChildren().addAll(filterHeaderLabel, typeFilterBox, dateFilterBox, applyFilterButton);

        // Event handlers
        viewAllButton.setOnAction(e -> {
            if (currentAccount != null) {
                updateTransactionHistory();
            } else {
                showAlert("No Account Selected", "Please select an account first.", Alert.AlertType.WARNING);
            }
        });

        viewCustomButton.setOnAction(e -> {
            if (currentAccount != null) {
                try {
                    int count = Integer.parseInt(countField.getText());
                    if (count <= 0) {
                        showAlert("Invalid Input", "Please enter a positive number.", Alert.AlertType.ERROR);
                        return;
                    }
                    updateTransactionHistory(count);
                } catch (NumberFormatException ex) {
                    showAlert("Invalid Input", "Please enter a valid number.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("No Account Selected", "Please select an account first.", Alert.AlertType.WARNING);
            }
        });

        applyFilterButton.setOnAction(e -> {
            if (currentAccount != null) {
                String filter = filterType.getValue();
                LocalDate startDate = startDatePicker.getValue();
                LocalDate endDate = endDatePicker.getValue();

                if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
                    showAlert("Invalid Date Range", "End date cannot be before start date.", Alert.AlertType.ERROR);
                    return;
                }

                Transaction[] allTransactions = currentAccount.getAllTransactions();
                StringBuilder sb = new StringBuilder();

                for (Transaction t : allTransactions) {
                    boolean includeByType = true;
                    boolean includeByDate = true;

                    // Filter by type
                    if (filter.equals("Deposits Only") && t.getType() != ETransactionType.DEPOSIT) {
                        includeByType = false;
                    } else if (filter.equals("Withdrawals Only") && t.getType() != ETransactionType.WITHDRAWAL) {
                        includeByType = false;
                    }

                    // Filter by date
                    LocalDate transactionDate = t.getDate().toLocalDate();
                    if (startDate != null && transactionDate.isBefore(startDate)) {
                        includeByDate = false;
                    }
                    if (endDate != null && transactionDate.isAfter(endDate)) {
                        includeByDate = false;
                    }

                    if (includeByType && includeByDate) {
                        sb.append(t.toString()).append("\n");
                    }
                }

                if (sb.length() == 0) {
                    transactionHistoryArea.setText("No transactions match the filter criteria.");
                } else {
                    transactionHistoryArea.setText(sb.toString());
                }
            } else {
                showAlert("No Account Selected", "Please select an account first.", Alert.AlertType.WARNING);
            }
        });

        section.getChildren().addAll(titleLabel, transactionHistoryArea, balanceLabel, countBox, filterBox);
        return section;
    }

    private VBox createTransactionOperationsSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));
        section.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label titleLabel = new Label("Perform Transaction");
        titleLabel.setStyle(SUBHEADER_STYLE);

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 0, 10, 0));

        // Amount field
        Label amountLabel = new Label("Amount:");
        amountLabel.setStyle(LABEL_STYLE);
        grid.add(amountLabel, 0, 0);
        TextField amountField = new TextField();
        amountField.setPromptText("Enter transaction amount");
        grid.add(amountField, 1, 0);

        // Description field
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.setStyle(LABEL_STYLE);
        grid.add(descriptionLabel, 0, 1);
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Optional transaction description");
        grid.add(descriptionField, 1, 1);

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        Button depositButton = new Button("Deposit");
        depositButton.setStyle(DEPOSIT_BUTTON_STYLE);
        depositButton.setPrefWidth(120);

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setStyle(WITHDRAW_BUTTON_STYLE);
        withdrawButton.setPrefWidth(120);

        depositButton.setOnAction(e -> {
            if (currentAccount != null) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        showAlert("Invalid Amount", "Please enter a positive amount.", Alert.AlertType.WARNING);
                        return;
                    }

                    String description = descriptionField.getText();

                    boolean success;
                    if (description.isEmpty()) {
                        success = currentAccount.deposit(amount);
                    } else {
                        success = currentAccount.deposit(amount, description);
                    }

                    if (success) {
                        updateTransactionHistory();
                        updateBalanceLabel();
                        amountField.clear();
                        descriptionField.clear();
                        showAlert("Success", "Deposit successful!", Alert.AlertType.INFORMATION);
                    } else {
                        showAlert("Failed", "Deposit failed. Please check the amount.", Alert.AlertType.WARNING);
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Invalid Input", "Please enter a valid amount.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("No Account Selected", "Please select an account first.", Alert.AlertType.WARNING);
            }
        });

        withdrawButton.setOnAction(e -> {
            if (currentAccount != null) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount <= 0) {
                        showAlert("Invalid Amount", "Please enter a positive amount.", Alert.AlertType.WARNING);
                        return;
                    }

                    String description = descriptionField.getText();

                    boolean success;
                    if (description.isEmpty()) {
                        success = currentAccount.withdraw(amount);
                    } else {
                        success = currentAccount.withdraw(amount, description);
                    }

                    if (success) {
                        updateTransactionHistory();
                        updateBalanceLabel();
                        amountField.clear();
                        descriptionField.clear();
                        showAlert("Success", "Withdrawal successful!", Alert.AlertType.INFORMATION);
                    } else {
                        if (currentAccount instanceof FixedDepositAccount) {
                            FixedDepositAccount fdAccount = (FixedDepositAccount)currentAccount;
                            if (!fdAccount.isMatured()) {
                                showAlert("Failed", "Withdrawal failed. Fixed deposit has not matured yet.",
                                        Alert.AlertType.WARNING);
                                return;
                            }
                        }

                        if (currentAccount instanceof SavingsAccount) {
                            SavingsAccount savingsAccount = (SavingsAccount)currentAccount;
                            double minBalance = savingsAccount.getMinimumBalance();
                            showAlert("Failed",
                                    "Withdrawal failed. This would bring your balance below the minimum requirement of $"
                                            + minBalance + ".", Alert.AlertType.WARNING);
                        } else if (currentAccount instanceof CurrentAccount) {
                            CurrentAccount currentAcc = (CurrentAccount)currentAccount;
                            double limit = currentAcc.getOverdraftLimit();
                            showAlert("Failed",
                                    "Withdrawal failed. This would exceed your overdraft limit of $"
                                            + limit + ".", Alert.AlertType.WARNING);
                        } else {
                            showAlert("Failed", "Withdrawal failed. Please check your balance.",
                                    Alert.AlertType.WARNING);
                        }
                    }
                } catch (NumberFormatException ex) {
                    showAlert("Invalid Input", "Please enter a valid amount.", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("No Account Selected", "Please select an account first.", Alert.AlertType.WARNING);
            }
        });

        buttonBox.getChildren().addAll(depositButton, withdrawButton);

        section.getChildren().addAll(titleLabel, grid, buttonBox);
        return section;
    }

    private void updateAccountSelector() {
        accountSelector.getItems().clear();
        for (BankAccount account : bankManager.getAllAccounts()) {
            String accountType = "";
            if (account instanceof SavingsAccount) {
                accountType = "Savings";
            } else if (account instanceof CurrentAccount) {
                accountType = "Current";
            } else if (account instanceof FixedDepositAccount) {
                accountType = "Fixed Deposit";
            }

            accountSelector.getItems().add(String.format("%s - %s - %s - $%.2f",
                    account.getAccountNumber(),
                    account.getCustomerName(),
                    accountType,
                    account.getBalance()));
        }

        // Select the first account if available
        if (!accountSelector.getItems().isEmpty()) {
            accountSelector.setValue(accountSelector.getItems().get(0));
            String accountNumber = accountSelector.getValue().split(" - ")[0];
            currentAccount = bankManager.getAccount(accountNumber);
            updateTransactionHistory();
            updateBalanceLabel();
        }
    }

    private void updateTransactionHistory() {
        if (currentAccount != null) {
            transactionHistoryArea.clear();
            Transaction[] transactions = currentAccount.getAllTransactions();

            if (transactions.length == 0) {
                transactionHistoryArea.setText("No transactions found.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Transaction transaction : transactions) {
                    sb.append(transaction.toString()).append("\n");
                }
                transactionHistoryArea.setText(sb.toString());
            }
        }
    }

    private void updateTransactionHistory(int count) {
        if (currentAccount != null) {
            transactionHistoryArea.clear();
            Transaction[] transactions = currentAccount.getRecentTransactions(count);

            if (transactions.length == 0) {
                transactionHistoryArea.setText("No transactions found.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Transaction transaction : transactions) {
                    sb.append(transaction.toString()).append("\n");
                }
                transactionHistoryArea.setText(sb.toString());
            }
        }
    }

    private void updateBalanceLabel() {
        if (currentAccount != null) {
            balanceLabel.setText(String.format("Current Balance: $%.2f", currentAccount.getBalance()));
            // Change color based on balance
            if (currentAccount.getBalance() < 0) {
                balanceLabel.setStyle(BALANCE_STYLE + "-fx-text-fill: #e74c3c;"); // Red for negative
            } else {
                balanceLabel.setStyle(BALANCE_STYLE + "-fx-text-fill: #2980b9;"); // Blue for positive
            }
        } else {
            balanceLabel.setText("Current Balance: $0.00");
            balanceLabel.setStyle(BALANCE_STYLE);
        }
    }

    private void updateLayoutForScrolling() {
        // Add margins between sections for better spacing in scrollable view
        VBox.setMargin(balanceLabel, new Insets(0, 0, 10, 0));
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}