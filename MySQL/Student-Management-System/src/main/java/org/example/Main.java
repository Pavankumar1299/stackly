
package org.example;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        StudentService studentService = new StudentService();
        ResultService resultService = new ResultService();

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Register Student");
            System.out.println("2. Search Student by ID");
            System.out.println("3. Display All Students");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Add Course");
            System.out.println("7. Display All Courses");
            System.out.println("8. Enroll Student in Course");
            System.out.println("9. Enter Student Marks");
            System.out.println("10. Generate Student Result");
            System.out.println("11. Mark Attendance");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {

                    case 1:
                        System.out.print("Enter student name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter student email: ");
                        String email = scanner.nextLine();

                        System.out.println("1. ACTIVE");
                        System.out.println("2. INACTIVE");
                        System.out.println("3. COMPLETED");
                        System.out.print("Choose status: ");

                        int statusChoice = Integer.parseInt(
                                scanner.nextLine().trim());

                        StudentStatus status;

                        switch (statusChoice) {
                            case 1:
                                status = StudentStatus.ACTIVE;
                                break;
                            case 2:
                                status = StudentStatus.INACTIVE;
                                break;
                            case 3:
                                status = StudentStatus.COMPLETED;
                                break;
                            default:
                                System.out.println("Invalid status!");
                                break;
                        }

                        if (statusChoice >= 1 && statusChoice <= 3) {
                            status = StudentStatus.values()[statusChoice - 1];

                            Student student = new Student(
                                    0, name, email, status);

                            studentService.addStudent(student);
                        }
                        break;

                    case 2:
                        System.out.print("Enter student ID: ");
                        int studentId = Integer.parseInt(
                                scanner.nextLine().trim());

                        studentService.searchStudent(studentId);
                        break;

                    case 3:
                        studentService.displayAllStudents();
                        break;

                    case 4:
                        System.out.print("Enter student ID: ");
                        int updateId = Integer.parseInt(
                                scanner.nextLine().trim());

                        System.out.print("Enter updated name: ");
                        String updatedName = scanner.nextLine();

                        System.out.print("Enter updated email: ");
                        String updatedEmail = scanner.nextLine();

                        System.out.println("1. ACTIVE");
                        System.out.println("2. INACTIVE");
                        System.out.println("3. COMPLETED");
                        System.out.print("Choose status: ");

                        int updatedStatusChoice = Integer.parseInt(
                                scanner.nextLine().trim());

                        if (updatedStatusChoice >= 1
                                && updatedStatusChoice <= 3) {

                            StudentStatus updatedStatus =
                                    StudentStatus.values()[
                                            updatedStatusChoice - 1];

                            Student updatedStudent = new Student(
                                    updateId,
                                    updatedName,
                                    updatedEmail,
                                    updatedStatus);

                            studentService.updateStudent(updatedStudent);
                        } else {
                            System.out.println("Invalid status!");
                        }
                        break;

                    case 5:
                        System.out.print("Enter student ID to delete: ");
                        int deleteId = Integer.parseInt(
                                scanner.nextLine().trim());

                        studentService.deleteStudent(deleteId);
                        break;

                    case 6:
                        System.out.print("Enter course name: ");
                        String courseName = scanner.nextLine();

                        System.out.print("Enter course duration: ");
                        int duration = Integer.parseInt(
                                scanner.nextLine().trim());

                        Course course = new Course(
                                0, courseName, duration);

                        if (studentService.addCourse(course)) {
                            System.out.println("Course added successfully!");
                        } else {
                            System.out.println("Course was not added.");
                        }
                        break;

                    case 7:
                        List<Course> courses = studentService.getAllCourses();

                        if (courses.isEmpty()) {
                            System.out.println("No courses found!");
                        } else {
                            courses.forEach(System.out::println);
                        }
                        break;

                    case 8:
                        System.out.print("Enter student ID: ");
                        int enrollmentStudentId = Integer.parseInt(
                                scanner.nextLine().trim());

                        System.out.print("Enter course ID: ");
                        int enrollmentCourseId = Integer.parseInt(
                                scanner.nextLine().trim());

                        if (studentService.enrollStudent(
                                enrollmentStudentId,
                                enrollmentCourseId)) {
                            System.out.println(
                                    "Student enrolled successfully!");
                        }
                        break;

                    case 9:
                        System.out.print("Enter student ID: ");
                        int marksStudentId = Integer.parseInt(
                                scanner.nextLine().trim());

                        System.out.print("Enter course ID: ");
                        int marksCourseId = Integer.parseInt(
                                scanner.nextLine().trim());

                        System.out.print("Enter marks (0-100): ");
                        double marks = Double.parseDouble(
                                scanner.nextLine().trim());

                        if (studentService.addMarks(
                                marksStudentId, marksCourseId, marks)) {
                            System.out.println("Marks added successfully!");
                        }
                        break;

                    case 10:
                        System.out.print("Enter student ID: ");
                        int resultStudentId = Integer.parseInt(
                                scanner.nextLine().trim());

                        resultService.generateResult(resultStudentId);
                        break;

                    case 11:
                        System.out.print("Enter enrollment ID: ");
                        int enrollmentId = Integer.parseInt(
                                scanner.nextLine().trim());

                        System.out.print(
                                "Enter attendance date (YYYY-MM-DD): ");
                        LocalDate attendanceDate = LocalDate.parse(
                                scanner.nextLine().trim());

                        System.out.print("Enter status (PRESENT/ABSENT): ");
                        String attendanceStatus = scanner.nextLine()
                                .trim().toUpperCase();

                        if (!attendanceStatus.equals("PRESENT")
                                && !attendanceStatus.equals("ABSENT")) {
                            System.out.println("Invalid attendance status!");
                            break;
                        }

                        if (studentService.markAttendance(
                                enrollmentId,
                                attendanceDate,
                                attendanceStatus)) {
                            System.out.println(
                                    "Attendance marked successfully!");
                        }
                        break;

                    case 0:
                        System.out.println(
                                "Exiting Student Management System. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid number! Please enter a valid number.");
                choice = -1;

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Invalid date! Use YYYY-MM-DD.");

                choice = -1;

            } catch (SQLException e) {
                System.out.println(
                        "Database operation failed: " + e.getMessage());

                choice = -1;
            }

        } while (choice != 0);

        scanner.close();
    }
}
