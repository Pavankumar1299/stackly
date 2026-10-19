package org.example;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        LocalDate currentDate = LocalDate.now();
        BankService bankService = new BankService();

        do {
            System.out.println("\n=== Welcome to Banking Management System ===");
            System.out.println(" 1. Add Customer");
            System.out.println(" 2. Create Account");
            System.out.println(" 3. Display Customer Details");
            System.out.println(" 4. Display Account Details");
            System.out.println(" 5. Deposit Money");
            System.out.println(" 6. Withdraw Money");
            System.out.println(" 7. Transfer Money");
            System.out.println(" 0. Exit");

            System.out.print("\nPlease enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> {
                    System.out.print("Enter customer name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter customer email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter customer phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter customer address: ");
                    String address = sc.nextLine();

                    System.out.print("Enter password (min length is 4): ");
                    String password = sc.nextLine();

                    System.out.print("Confirm password: ");
                    String  confirmPassword = sc.nextLine();

                    try {
                        if (!password.equals(confirmPassword)) {
                            throw new BankManagementException("Password does not match");
                        }

                        if (password.length() < 4) {
                            throw new BankManagementException("Password too short");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    Customer customer = new Customer(0, name, email, phone, address, password);

                    try {
                        bankService.addCustomer(customer);
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 2 -> {
                    System.out.print("Enter Customer id: ");
                    int custId = sc.nextInt();
                    sc.nextLine();
                    try {
                        if (!bankService.isCustomerExists(custId)) {
                            throw new BankManagementException("Customer does not exist");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();

                    System.out.println("Types of account available");
                    System.out.println(" 1. Savings");
                    System.out.println(" 2. Current");
                    System.out.print("\nEnter account type: ");
                    int ch = sc.nextInt();
                    sc.nextLine();

                    if (ch < 1 && ch > 2) {
                        throw new BankManagementException("Invalid choice");
                    }

                    String accType = (ch == 1) ? "Savings" : "Current";

                    System.out.print("Enter initial balance Rs. 1000 or above: ");
                    BigDecimal initialBalance = sc.nextBigDecimal();
                    sc.nextLine();

                    Account account = new Account(0, 0, custId, accType, initialBalance, currentDate);

                    try {
                        bankService.createAccount(account, password);
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 3 -> {
                    System.out.print("Enter Customer id: ");
                    int custId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();

                    try {
                        Customer customer = bankService.customerDetails(custId, password);
                        System.out.println(customer);
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 4 -> {
                    System.out.print("Enter Account no: ");
                    int accNo = sc.nextInt();
                    sc.nextLine();

                    try {
                        if (!bankService.isAccountExists(accNo)) {
                            throw new BankManagementException("Account does not exist");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();

                    try {
                        if (!bankService.isPasswordValid(password)) {
                            throw new BankManagementException("Password not valid");
                        }

                        Account account = bankService.accountDetails(accNo);
                        System.out.println(account);
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 5 -> {
                    System.out.print("Enter Account No: ");
                    int accountNo = sc.nextInt();
                    sc.nextLine();

                    try {
                        if (!bankService.isAccountExists(accountNo)) {
                            throw new BankManagementException("Account does not exist");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();

                    try {
                        if (!bankService.isPasswordValid(password)) {
                            throw new BankManagementException("Password not valid");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.print("Enter amount: ");
                    BigDecimal amount = sc.nextBigDecimal();
                    sc.nextLine();

                    try {
                        bankService.depositAmount(accountNo, amount);
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 6 -> {
                    System.out.print("Enter Account No: ");
                    int accountNo = sc.nextInt();
                    sc.nextLine();

                    try {
                        if (!bankService.isAccountExists(accountNo)) {
                            throw new BankManagementException("Account does not exist");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();

                    try {
                        if (!bankService.isPasswordValid(password)) {
                            throw new BankManagementException("Password not valid");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    System.out.print("Enter amount: ");
                    BigDecimal amount = sc.nextBigDecimal();
                    sc.nextLine();

                    try {
                        bankService.withdrawAmount(accountNo, amount);
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 7 -> {
                    System.out.print("Enter your account no: ");
                    int accountNoFrom = sc.nextInt();
                    sc.nextLine();

                    try {
                        if (!bankService.isAccountExists(accountNoFrom)) {
                            throw new BankManagementException("Account does not exist");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();

                    try {
                        if (!bankService.isPasswordValid(password)) {
                            throw new BankManagementException("Password not valid");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.print("Enter other account no: ");
                    int accountNoTo = sc.nextInt();
                    sc.nextLine();

                    try {
                        if (!bankService.isAccountExists(accountNoTo)) {
                            throw new BankManagementException("Other account does not exist");
                        }
                    } catch (BankManagementException e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }

                    System.out.print("Enter the amount you want to transfer: ");
                    BigDecimal transferAmount = sc.nextBigDecimal();
                    sc.nextLine();

                    bankService.transferAmount(accountNoFrom, accountNoTo, transferAmount);
                }

                case 0 -> {
                    running = false;
                    System.out.println("Thank you for using our system.");
                }
            }
        } while (running);

    }
}