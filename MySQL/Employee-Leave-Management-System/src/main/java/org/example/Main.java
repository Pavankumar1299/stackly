
package org.example;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        EmployeeService employeeService = new EmployeeService();

        int choice = -1;

        while (choice != 0) {

            System.out.println("\n===== EMPLOYEE LEAVE MANAGEMENT =====");
            System.out.println("1. Register Employee");
            System.out.println("2. Search Employee");
            System.out.println("3. Display All Employees");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Apply for Leave");
            System.out.println("7. View Employees Leave Requests List");
            System.out.println("8. Approve Leave Request");
            System.out.println("9. Reject Leave Request");
            System.out.println("10. View Leave Balance");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {

                    case 1: {
                        System.out.print("Employee name: ");
                        String name = scanner.nextLine();

                        System.out.print("Email: ");
                        String email = scanner.nextLine();

                        System.out.print("Department: ");
                        String department = scanner.nextLine();

                        System.out.print("Joining date (YYYY-MM-DD): ");
                        LocalDate joiningDate =
                                LocalDate.parse(scanner.nextLine().trim());

                        Employee employee = new Employee(
                                0, name, email, department, joiningDate);

                        employeeService.registerEmployee(employee);
                        break;
                    }

                    case 2: {
                        System.out.print("Employee ID: ");
                        int employeeId = Integer.parseInt(
                                scanner.nextLine().trim());

                        employeeService.searchEmployee(employeeId);
                        break;
                    }

                    case 3:
                        employeeService.displayAllEmployees();
                        break;

                    case 4: {
                        System.out.print("Employee ID to update: ");
                        int employeeId = Integer.parseInt(
                                scanner.nextLine().trim());

                        System.out.print("Updated name: ");
                        String name = scanner.nextLine();

                        System.out.print("Updated email: ");
                        String email = scanner.nextLine();

                        System.out.print("Updated department: ");
                        String department = scanner.nextLine();

                        System.out.print(
                                "Updated joining date (YYYY-MM-DD): ");
                        LocalDate joiningDate =
                                LocalDate.parse(scanner.nextLine().trim());

                        Employee employee = new Employee(
                                employeeId, name, email,
                                department, joiningDate);

                        employeeService.updateEmployee(employee);
                        break;
                    }

                    case 5: {
                        System.out.print("Employee ID to delete: ");
                        int employeeId = Integer.parseInt(
                                scanner.nextLine().trim());

                        employeeService.deleteEmployee(employeeId);
                        break;
                    }

                    case 6: {
                        System.out.print("Employee ID: ");
                        int employeeId = Integer.parseInt(
                                scanner.nextLine().trim());

                        System.out.print("Start date (YYYY-MM-DD): ");
                        LocalDate startDate =
                                LocalDate.parse(scanner.nextLine().trim());

                        System.out.print("End date (YYYY-MM-DD): ");
                        LocalDate endDate =
                                LocalDate.parse(scanner.nextLine().trim());

                        System.out.print("Reason for leave: ");
                        String reason = scanner.nextLine();

                        if (endDate.isBefore(startDate)) {
                            System.out.println(
                                    "End date cannot be before start date!");
                            break;
                        }

                        LeaveRequest request = new LeaveRequest(
                                0, employeeId, startDate, endDate,
                                reason, "PENDING");

                        if (employeeService.applyLeave(request)) {
                            System.out.println(
                                    "Leave request submitted successfully!");
                        }
                        break;
                    }

                    case 7: {
                        employeeService.getLeaveRequestsList();
                        break;
                    }

                    case 8: {
                        System.out.print("Leave request ID: ");
                        int requestId = Integer.parseInt(
                                scanner.nextLine().trim());

                        employeeService.processLeaveRequest(
                                requestId, "APPROVED");

                        System.out.println("Leave request approved!");
                        break;
                    }

                    case 9: {
                        System.out.print("Leave request ID: ");
                        int requestId = Integer.parseInt(
                                scanner.nextLine().trim());

                        employeeService.processLeaveRequest(
                                requestId, "REJECTED");

                        System.out.println("Leave request rejected!");
                        break;
                    }

                    case 10: {
                        System.out.print("Employee ID: ");
                        int employeeId = Integer.parseInt(
                                scanner.nextLine().trim());

                        employeeService.displayLeaveBalance(employeeId);
                        break;
                    }

                    case 0:
                        System.out.println("Goodbye, buddy!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");

            } catch (DateTimeParseException e) {
                System.out.println("Invalid date! Use YYYY-MM-DD.");

            } catch (SQLException e) {
                System.out.println(
                        "Database operation failed: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
