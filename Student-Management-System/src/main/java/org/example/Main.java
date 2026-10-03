package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        StudentService studentService = new StudentService();
        studentService.createCourses();
        StudentFileService studentFileService = new StudentFileService();

        boolean running = true;

        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Update Student Marks");
            System.out.println("6. Update Student Attendance");
            System.out.println("7. Add Course Marks");
            System.out.println("8. Calculate Grade & Percentage");
            System.out.println("9. Display Top 3 Students");
            System.out.println("10. Display Top Scorer for Each Course");
            System.out.println("11. Remove Student");
            System.out.println("12. Save Students");
            System.out.println("13. Load Students");
            System.out.println("14. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> {

                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    if (studentService.studentExists(id)){
                        System.out.println("Student ID is already present");
                        break;
                    }

                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Student Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter Student Attendance: ");
                    int attendance = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter student status (ACTIVE/INACTIVE/COMPLETED): ");

                    String statusInput = sc.nextLine().toUpperCase();

                    StudentStatus status = StudentStatus.valueOf(statusInput);

                    Student student = new Student(id, name, email, attendance, status);

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
                    if (studentService.students.isEmpty()) {
                        System.out.println("No students found! please add or load students");
                        break;
                    }

                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    try {
                        Student student = studentService.searchByStudentId(id);
                        System.out.println(student);

                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 4 -> {
                    if (studentService.students.isEmpty()) {
                        System.out.println("No students found! please add or load students");
                        break;
                    }

                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();

                    try {
                        Student student = studentService.searchByName(name);
                        System.out.println(student);

                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 5 -> {
                    if (studentService.students.isEmpty()) {
                        System.out.println("No students found! please add or load students");
                        break;
                    }

                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    if (!studentService.studentExists(id)) {
                        System.out.println("No students found!");
                        break;
                    }

                    System.out.print("Enter Course ID: ");
                    int courseId = sc.nextInt();
                    sc.nextLine();

                    if (!studentService.checkCourse(courseId)) {
                        System.out.println("Course ID is not present");
                        break;
                    }

                    System.out.print("Enter Student New Marks: ");
                    int marks = sc.nextInt();
                    sc.nextLine();

                    try {
                        studentService.updateMarks(id, courseId, marks);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 6 -> {
                    if (studentService.students.isEmpty()) {
                        System.out.println("No students found! please add or load students");
                        break;
                    }

                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    if (!studentService.studentExists(id)){
                        System.out.println("Student ID is not present");
                        break;
                    }

                    System.out.print("Enter Student New Attendance: ");
                    int attendance = sc.nextInt();
                    sc.nextLine();

                    try {
                        studentService.updateAttendance(id, attendance);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 7 -> {

                    if (studentService.students.isEmpty()) {
                        System.out.println("No students found! please add or load students");
                        break;
                    }

                    System.out.print("Enter Student ID: ");
                    int studentId = sc.nextInt();
                    sc.nextLine();

                    try {
                        studentService.searchByStudentId(studentId);

                        System.out.println("\n===== Available Courses =====");
                        System.out.println("101. Java");
                        System.out.println("102. SQL");
                        System.out.println("103. Spring Boot");
                        System.out.println("104. HTML & CSS");
                        System.out.println("105. DSA");

                        for (int i = 1; i <= 5; i++) {

                            System.out.print("\nEnter Course ID: ");
                            int courseId = sc.nextInt();

                            try {
                                if (studentService.checkCourse(courseId)) {

                                    System.out.print("Enter Marks: ");
                                    double marks = sc.nextDouble();

                                    studentService.addCourseMarks(studentId, courseId, marks);
                                }

                            } catch (StudentException e) {
                                System.out.println(e.getMessage());
                                i--; // retry this course
                            }
                        }

                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 8 -> {

                    if (studentService.students.isEmpty()) {
                        System.out.println("No students found! please add or load students");
                        break;
                    }

                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    try {
                        studentService.calculateGrade(id);

                        Student student = studentService.searchByStudentId(id);

                        System.out.println("\n===== RESULT =====");
                        System.out.println("Student ID: " + student.getId());
                        System.out.println("Name: " + student.getName());
                        System.out.println("Percentage: " + student.getPercentage() + "%");
                        System.out.println("Grade: " + student.getGrade());
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 9 -> {
                    if (studentService.students.isEmpty()) {
                        System.out.println("No students found! please add or load students");
                        break;
                    }

                    studentService.displayTopThreeStudents();
                }

                case 10 -> {
                    if (studentService.students.isEmpty()) {
                        System.out.println("No students found! please add or load students");
                        break;
                    }

                    studentService.displayTopScorerByCourse();
                }

                case 11 -> {
                    System.out.print("Enter Student ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    try {
                        studentService.removeStudent(id);
                    } catch (StudentException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 12 -> {
                    try {
                        studentFileService.saveStudent((HashMap<Integer, Student>) studentService.students);
                    } catch (IOException e) {
                        System.out.println("Error saving students: " + e.getMessage());
                    }
                }

                case 13 -> {
                    try {
                        studentFileService.loadStudent((HashMap<Integer, Student>) studentService.students);
                    } catch (IOException e) {
                        System.out.println("Error loading students: " + e.getMessage());
                    }
                }

                case 14 -> {
                    System.out.println("Thank you for using our application!");
                    running = false;
                }

                default -> {
                    System.out.println("Invalid input!");
                }
            }

        } while (running);

        sc.close();
    }
}