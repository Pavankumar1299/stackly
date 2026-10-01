package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws StudentException, IOException {

        StudentService studentService = new StudentService();
        StudentFileService studentFileService = new StudentFileService();

        boolean running = true;

        do {
            Scanner sc = new Scanner(System.in);

            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Update Marks");
            System.out.println("6. Update Attendance");
            System.out.println("7. Calculate Grade");
            System.out.println("8. Display Topper");
            System.out.println("9. Remove Student");
            System.out.println("10. Save Students");
            System.out.println("11. Load Students");
            System.out.println("12. Exit");
            System.out.println();

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Student Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Student Marks: ");
                    int marks = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Student Attendance: ");
                    int attendance = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter student status (ACTIVE/INACTIVE/COMPLETED): ");
                    String statusInput = sc.nextLine().toUpperCase();

                    StudentStatus status = StudentStatus.valueOf(statusInput);

                    Student student = new Student(id, name, email, marks, attendance, status);

                    try {
                        studentService.addStudent(student);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 2 -> {
                    studentService.displayAllStudent();
                }

                case 3 -> {
                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
//                    sc.nextLine();

                    try {
                        Student student = studentService.searchByStudentId(id);
                        System.out.println(student);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 4 -> {
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();

                    try {
                        Student student = studentService.searchByName(name);
                        System.out.println(student);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    };
                }

                case 5 -> {
                    System.out.println("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter Student New Marks: ");
                    int marks = sc.nextInt();
                    sc.nextLine();

                    try {
                        studentService.updateMarks(id, marks);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 6 -> {
                    System.out.println("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter Student New Attendance: ");
                    int attendance = sc.nextInt();
                    sc.nextLine();

                    try {
                        studentService.updateAttendance(id, attendance);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 7 -> {
                    System.out.println("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    try {
                        studentService.calculateGrade(id);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 8 -> {
                    studentService.displayTopper();
                }

                case 9 -> {
                    System.out.println("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    try {
                        studentService.removeStudent(id);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 10 -> {
                    studentFileService.saveStudent(studentService.students);
                }

                case 11 -> {
                    studentFileService.loadStudent(studentService.students);
                }

                case 12 -> {
                    System.out.println("Thank you for using our application!");
                    running = false;
                }

                default -> {
                    System.out.println("Invalid input!");
                }
            }
        } while (running);
    }
}