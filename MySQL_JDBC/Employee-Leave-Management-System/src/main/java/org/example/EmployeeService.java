package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public void registerEmployee(Employee employee) throws SQLException {

        if (employee.getEmployeeName() == null
                || employee.getEmployeeName().isBlank()) {
            System.out.println("Employee name cannot be empty!");
            return;
        }

        if (employee.getEmail() == null
                || employee.getEmail().isBlank()
                || !employee.getEmail().contains("@")) {
            System.out.println("Please enter a valid email!");
            return;
        }

        if (employee.getDepartment() == null
                || employee.getDepartment().isBlank()) {
            System.out.println("Department cannot be empty!");
            return;
        }

        if (employee.getJoiningDate() == null) {
            System.out.println("Joining date is required!");
            return;
        }

        String employeeSql = """
        INSERT INTO employees
            (employee_name, email, department, joining_date)
        VALUES (?, ?, ?, ?)
        """;

        String balanceSql = """
        INSERT INTO leave_balance
            (employee_id, total_leaves, used_leaves)
        VALUES (?, 20, 0)
        """;

        boolean added = false;

        try (Connection connection = DBConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                int employeeId;

                try (PreparedStatement employeeStatement =
                             connection.prepareStatement(
                                     employeeSql,
                                     java.sql.Statement.RETURN_GENERATED_KEYS)) {

                    employeeStatement.setString(
                            1, employee.getEmployeeName());
                    employeeStatement.setString(
                            2, employee.getEmail());
                    employeeStatement.setString(
                            3, employee.getDepartment());
                    employeeStatement.setDate(
                            4, java.sql.Date.valueOf(
                                    employee.getJoiningDate()));

                    employeeStatement.executeUpdate();

                    try (java.sql.ResultSet keys =
                                 employeeStatement.getGeneratedKeys()) {

                        if (!keys.next()) {
                            throw new SQLException(
                                    "Failed to retrieve employee ID.");
                        }

                        employeeId = keys.getInt(1);
                    }
                }

                try (PreparedStatement balanceStatement =
                             connection.prepareStatement(balanceSql)) {

                    balanceStatement.setInt(1, employeeId);
                    balanceStatement.executeUpdate();
                }

                connection.commit();
                added = true;

            } catch (SQLException e) {
                connection.rollback();
                throw e;

            } finally {
                connection.setAutoCommit(true);
            }
        }

        if (added) {
            System.out.println("Employee registered successfully!");
        } else {
            System.out.println("Employee registration failed!");
        }
    }

    public void searchEmployee(int employeeId) throws SQLException {

        Employee employee = null;

        String sql = """
            
                SELECT employee_id, employee_name, email,
                   department, joining_date
            FROM employees
            WHERE employee_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                     employee = new Employee(
                            rs.getInt("employee_id"),
                            rs.getString("employee_name"),
                            rs.getString("email"),
                            rs.getString("department"),
                            rs.getDate("joining_date").toLocalDate()
                    );
                }
            }

            if (employee == null) {
                System.out.println("Employee not found!");
            } else {
                System.out.println(employee);
            }
        }
    }

    public void displayAllEmployees() throws SQLException {

        List<Employee> employees = new ArrayList<>();

        String sql = """
            SELECT employee_id, employee_name, email,
                   department, joining_date
            FROM employees
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                 Employee employee = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("employee_name"),
                        rs.getString("email"),
                        rs.getString("department"),
                        rs.getDate("joining_date").toLocalDate()
                );

                employees.add(employee);
            }
        }

        if (employees == null) {
            System.out.println("No employees found!");
            return;
        }

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public void updateEmployee(Employee employee) throws SQLException {

        String sql = """
            UPDATE employees
            SET employee_name = ?,
                email = ?,
                department = ?,
                joining_date = ?
            WHERE employee_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, employee.getEmployeeName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getDepartment());
            ps.setDate(4, java.sql.Date.valueOf(employee.getJoiningDate()));
            ps.setInt(5, employee.getEmployeeId());

            if (ps.executeUpdate() > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Employee not found!");
            }
        }
    }

    public void deleteEmployee(int employeeId) throws SQLException {

        String sql = """
            DELETE FROM employees
            WHERE employee_id = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee not found!");
            }
        }
    }

    public void displayLeaveBalance(int employeeId) throws SQLException {

        LeaveBalance leaveBalance = null;

        String sql = """
                SELECT employee_id, total_leaves, used_leaves
                FROM leave_balance
                WHERE employee_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, employeeId);

            try (ResultSet rs = ps.executeQuery()) {


                if (rs.next()) {
                    leaveBalance = new LeaveBalance(
                            rs.getInt("employee_id"),
                            rs.getInt("total_leaves"),
                            rs.getInt("used_leaves")
                    );
                }
            }
        }

        if (leaveBalance == null) {
            System.out.println(
                    "Leave balance not found for employee ID: "
                            + employeeId);
            return;
        }

        System.out.println("\n===== LEAVE BALANCE =====");
        System.out.println("Employee ID: "
                + leaveBalance.getEmployeeId());
        System.out.println("Total Leaves: "
                + leaveBalance.getTotalLeaves());
        System.out.println("Used Leaves: "
                + leaveBalance.getUsedLeaves());
        System.out.println("Available Leaves: "
                + leaveBalance.getAvailableLeaves());
    }

    public boolean applyLeave(LeaveRequest leaveRequest) throws SQLException {

        String sql = """
                INSERT INTO leave_requests
                    (employee_id, start_date, end_date, reason, status)
                VALUES (?, ?, ?, ?, 'PENDING')
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, leaveRequest.getEmployeeId());
            ps.setDate(2, Date.valueOf(leaveRequest.getStartDate()));
            ps.setDate(3, Date.valueOf(leaveRequest.getEndDate()));
            ps.setString(4, leaveRequest.getReason());

            return ps.executeUpdate() > 0;
        }
    }

    public void getLeaveRequestsList() throws SQLException {

        List<LeaveRequest> requests = new ArrayList<>();

        String sql = """
                SELECT request_id, employee_id, start_date,
                       end_date, reason, status
                FROM leave_requests
                ORDER BY request_id DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    LeaveRequest request = new LeaveRequest(
                            rs.getInt("request_id"),
                            rs.getInt("employee_id"),
                            rs.getDate("start_date").toLocalDate(),
                            rs.getDate("end_date").toLocalDate(),
                            rs.getString("reason"),
                            rs.getString("status")
                    );

                    requests.add(request);
                }
            }
        }

        if (requests.isEmpty()) {
            System.out.println("No leave requests found!");
        }

        for (LeaveRequest request : requests) {
            System.out.println(request);
        }
    }

    public void processLeaveRequest(int requestId, String decision) throws SQLException {

        if (!decision.equals("APPROVED") && !decision.equals("REJECTED")) {
            throw new IllegalArgumentException(
                    "Decision must be APPROVED or REJECTED.");
        }

        String requestSql = """
            SELECT employee_id, start_date, end_date, status
            FROM leave_requests
            WHERE request_id = ?
            FOR UPDATE
            """;

        String updateRequestSql = """
            UPDATE leave_requests
            SET status = ?
            WHERE request_id = ?
            """;

        String updateBalanceSql = """
            UPDATE leave_balance
            SET used_leaves = used_leaves + ?
            WHERE employee_id = ?
              AND total_leaves - used_leaves >= ?
            """;

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                int employeeId;
                java.time.LocalDate startDate;
                java.time.LocalDate endDate;
                String currentStatus;

                try (PreparedStatement ps =
                             connection.prepareStatement(requestSql)) {

                    ps.setInt(1, requestId);

                    try (java.sql.ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Leave request not found.");
                        }

                        employeeId = rs.getInt("employee_id");
                        startDate = rs.getDate("start_date").toLocalDate();
                        endDate = rs.getDate("end_date").toLocalDate();
                        currentStatus = rs.getString("status");
                    }
                }

                if (!currentStatus.equals("PENDING")) {
                    throw new SQLException(
                            "This request has already been processed.");
                }

                if (decision.equals("APPROVED")) {
                    int leaveDays = Math.toIntExact(
                            java.time.temporal.ChronoUnit.DAYS.between(
                                    startDate, endDate) + 1);

                    try (PreparedStatement ps =
                                 connection.prepareStatement(updateBalanceSql)) {

                        ps.setInt(1, leaveDays);
                        ps.setInt(2, employeeId);
                        ps.setInt(3, leaveDays);

                        if (ps.executeUpdate() == 0) {
                            throw new SQLException(
                                    "Insufficient leave balance or balance record missing.");
                        }
                    }
                }

                try (PreparedStatement ps =
                             connection.prepareStatement(updateRequestSql)) {

                    ps.setString(1, decision);
                    ps.setInt(2, requestId);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "Failed to update leave request.");
                    }
                }

                connection.commit();

            } catch (SQLException | RuntimeException e) {
                connection.rollback();
                throw e;

            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

}