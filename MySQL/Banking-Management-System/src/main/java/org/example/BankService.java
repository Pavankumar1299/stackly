package org.example;

import java.math.BigDecimal;
import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

public class BankService {

    public void addCustomer(Customer customer) throws SQLException {

        if (customer.getCustomerName() == null || customer.getCustomerName().isBlank()) {
            throw new BankManagementException("Customer Name cannot be empty");
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new BankManagementException("Customer Email cannot be empty");
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            throw new BankManagementException("Customer Phone Number cannot be empty");
        }

        String customerSql = "INSERT INTO customers (name, email, phone, address, password) VALUES (?, ?, ?, ?, ?)";

        boolean added;
        int customerId;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement customerStatement = conn.prepareStatement(customerSql, Statement.RETURN_GENERATED_KEYS)) {

            customerStatement.setString(1, customer.getCustomerName());
            customerStatement.setString(2, customer.getEmail());
            customerStatement.setString(3, customer.getPhoneNumber());
            customerStatement.setString(4, customer.getAddress());
            customerStatement.setString(5, customer.getPassword());

            customerStatement.executeUpdate();

            try (ResultSet keys = customerStatement.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("Customer ID not found");
                }

                customerId = keys.getInt(1);
            }

            added = true;
        }

        if (added) {
            System.out.println("Customer added successfully!");
            System.out.println("Customer ID: " + customerId);
            System.out.println("Customer Name: " + customer.getCustomerName());
        } else {
            System.out.println("Customer not added successfully");
        }
    }

    public boolean isPasswordValid(String password) throws SQLException {

        String sql = "select customer_id from customers where password = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, password);

            return statement.executeQuery().next();
        }
    }

    public boolean isCustomerExists(int customerId) throws SQLException {

        String sql = "select * from customers where customer_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Customer customerDetails(int customerId, String password) throws SQLException {
        if (!isCustomerExists(customerId)) {
            throw new BankManagementException("Customer ID not found");
        }

        Customer customer = null;

        String sql = "select * from customers where customer_id = ? and password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                customer = new Customer(rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("password")
                        );
            }
        }

        if (customer != null) {
            return customer;
        } else {
            throw new BankManagementException("Password is incorrect");
        }
    }

    public Account accountDetails(int accountNo) throws SQLException {
        Account account = null;

        String sql = "select * from accounts where account_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, accountNo);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                account = new Account(rs.getInt("account_id"),
                        rs.getInt("account_no"),
                        rs.getInt("customer_id"),
                        rs.getString("account_type"),
                        rs.getBigDecimal("balance"),
                        rs.getDate("acc_creation_date").toLocalDate()
                );
            }
        }

        if (account != null) {
            return account;
        } else {
            throw new BankManagementException("Account not found");
        }
    }

    public boolean isAccNoExist(int accountNo) throws SQLException {
        String sql = "select * from accounts where account_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, accountNo);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void createAccount(Account account, String password) throws SQLException {
        Customer customer = customerDetails(account.getCustomerId(), password);
        if (customer == null) {
            throw new BankManagementException("Password is incorrect");
        }

        if (account.getBalance().compareTo(BigDecimal.valueOf(1000)) < 0) {
            System.out.println("Amount cannot be less than 1000");
        }

        int accountNo = ThreadLocalRandom.current().nextInt(100000, 1000000);
        while (isAccNoExist(accountNo)) {
            accountNo = ThreadLocalRandom.current().nextInt(100000, 1000000);
        }

        String accountSql = "INSERT INTO accounts (account_no, customer_id, account_type, balance) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement accountStatement = conn.prepareStatement(accountSql, Statement.RETURN_GENERATED_KEYS)) {

            accountStatement.setInt(1, accountNo);
            accountStatement.setInt(2, account.getCustomerId());
            accountStatement.setString(3, account.getAccountType());
            accountStatement.setBigDecimal(4, account.getBalance());

            accountStatement.executeUpdate();
        }

        System.out.println("Account created successfully!");
        System.out.println("Account No: " + accountNo);
    }

    public boolean isAccountExists(int accountNo) throws  SQLException {

        String sql = "select * from accounts where account_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, accountNo);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void depositAmount(int accountNo, BigDecimal amount) throws SQLException {

        if (!isAccountExists(accountNo)) {
            throw new SQLException("Account does not exist");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new SQLException("Amount cannot be less than 0");
        }

        String sql = "update accounts set balance = balance + ? where account_no = ?";

        String transSql = "insert into transactions (account_no, trans_ref, trans_type, amount, trans_status) values (?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            String status = "FAILED";

            try {
                try (PreparedStatement statement = conn.prepareStatement(sql)) {

                    statement.setBigDecimal(1, amount);
                    statement.setInt(2, accountNo);

                    if (statement.executeUpdate() > 0) {
                        status = "SUCCESS";
                    }
                }

                try (PreparedStatement transStatement = conn.prepareStatement(transSql)) {

                    transStatement.setInt(1, accountNo);
                    transStatement.setString(2, "NULL");
                    transStatement.setString(3, "DEPOSIT");
                    transStatement.setBigDecimal(4, amount);
                    transStatement.setString(5, status);

                    transStatement.executeUpdate();

                }

                conn.commit();
                System.out.println("Account deposited successfully!");

            } catch (SQLException ex) {
                conn.rollback();
                throw new BankManagementException("Amount deposited failed!" + ex.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void withdrawAmount(int accountNo, BigDecimal amount) throws SQLException {

        if (!isAccountExists(accountNo)) {
            throw new SQLException("Account does not exist");
        }

        BigDecimal balance = accountDetails(accountNo).getBalance();

        if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(balance) > 0) {
            throw new SQLException("Insufficient Balance");
        }

        String sql = "update accounts set balance = balance - ? where account_no = ?";

        String transSql = "insert into transactions (account_no, trans_ref, trans_type, amount, trans_status) values (?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {

                String status = "FAILED";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {

                    statement.setBigDecimal(1, amount);
                    statement.setInt(2, accountNo);

                    if (statement.executeUpdate() > 0) {
                        status = "SUCCESS";
                    }
                }

                try (PreparedStatement transStatement = conn.prepareStatement(transSql)) {

                    transStatement.setInt(1, accountNo);
                    transStatement.setString(2, "NULL");
                    transStatement.setString(3, "WITHDRAWAL");
                    transStatement.setBigDecimal(4, amount);
                    transStatement.setString(5, status);

                    transStatement.executeUpdate();
                }

                conn.commit();
                System.out.println("Account withdrawn successfully!");
            } catch (SQLException e) {
                conn.rollback();
                throw new BankManagementException("Withdraw failed!" + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public void transferAmount(int accountNoFrom, int accountNoTo, BigDecimal transferAmount) throws SQLException {

        if (!isAccountExists(accountNoFrom) || !isAccountExists(accountNoTo)) {
            throw new SQLException("Account does not exist! verify account once again");
        }

        BigDecimal balance = accountDetails(accountNoFrom).getBalance();

        if (transferAmount.compareTo(BigDecimal.ZERO) < 0 || transferAmount.compareTo(balance) > 0) {
            throw new SQLException("Insufficient Balance");
        }

        String amountDeduct = "update accounts set balance = balance - ? where account_no = ?";

        String amountCredit = "update accounts set balance = balance + ? where account_no = ?";

        String transFromSql = "insert into transactions (account_no, trans_ref, trans_type, amount, trans_status) values (?,?,?,?,?)";

        String transToSql = "insert into transactions (account_no, trans_ref, trans_type, amount, trans_status) values (?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                String status1 = "FAILED";
                String status2 = "FAILED";

                try (PreparedStatement statement = conn.prepareStatement(amountDeduct)) {
                    statement.setBigDecimal(1, transferAmount);
                    statement.setInt(2, accountNoFrom);

                    if (statement.executeUpdate() > 0) {
                        status1 = "SUCCESS";
                    }
                }

                try (PreparedStatement statement = conn.prepareStatement(amountCredit)){
                        statement.setBigDecimal(1, transferAmount);
                        statement.setInt(2, accountNoTo);

                        if (statement.executeUpdate() > 0) {
                            status2 = "SUCCESS";
                        }
                }

                try (PreparedStatement transStatement = conn.prepareStatement(transFromSql)) {

                    transStatement.setInt(1, accountNoFrom);
                    transStatement.setString(2, "TRF");
                    transStatement.setString(3, "TRANSFER_OUT");
                    transStatement.setBigDecimal(4, transferAmount);
                    transStatement.setString(5, status1);

                    transStatement.executeUpdate();
                }

                if (status1.equals("SUCCESS") && status2.equals("SUCCESS")) {
                    try (PreparedStatement transStatement = conn.prepareStatement(transToSql)) {

                        transStatement.setInt(1, accountNoTo);
                        transStatement.setString(2, "TRF");
                        transStatement.setString(3, "TRANSFER_IN");
                        transStatement.setBigDecimal(4, transferAmount);
                        transStatement.setString(5, status2);

                        transStatement.executeUpdate();
                    }
                }

                conn.commit();
                System.out.println("Amount transferred successfully!");

            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException("Amount Transfer Failed");
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

