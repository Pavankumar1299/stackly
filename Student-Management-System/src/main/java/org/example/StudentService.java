package org.example;

import java.util.HashMap;

public class StudentService {

    HashMap<Integer, Student> students = new HashMap<Integer, Student>();

    public void addStudent(Student student) throws StudentException {
        if (students.containsKey(student.getId())) {
            throw  new StudentException("Student already exists!");
        } else {
            students.put(student.getId(), student);
            System.out.println("Student details saved successfully");
        }
    }

    public void displayAllStudent() {
        for (Student s : students.values()) {
            System.out.println(s);
        }

        if (students.isEmpty()) {
            System.out.println("No students found!");
        }
    }

    public Student searchByName(String name) throws StudentException {
        boolean found = false;

        for (Student student : students.values()) {
            if (student.getName().equals(name)) {
                found = true;
                return student;
            }
        }

        throw new StudentException("Student not found!");
    }

    public Student searchByStudentId(int studentId) throws StudentException {
        if (students.containsKey(studentId)) {
            return students.get(studentId);
        } else {
            throw new StudentException("Student not found!");
        }
    }

    public void updateMarks(int studentId, int newMark) throws StudentException {
        if (students.containsKey(studentId)) {
            if (newMark >= 0 && newMark <= 100) {
                students.get(studentId).setMarks(newMark);
                System.out.println("Marks updated!");
            } else {
                System.out.println("Invalid marks!");
            }
        } else {
            throw new StudentException("Student not found!");
        }
    }

    public void updateAttendance(int studentId, int newAttendance) throws StudentException {
        if (students.containsKey(studentId)) {
            if (newAttendance >= 0 && newAttendance <= 100) {
                students.get(studentId).setAttendance(newAttendance);
                System.out.println("Attendance updated!");
            } else {
                System.out.println("Invalid attendance!");
            }
        } else {
            throw new StudentException("Student not found!");
        }
    }

    public void calculateGrade(int studentId) throws StudentException {
        if (students.containsKey(studentId)) {
            double marks = students.get(studentId).getMarks();
            String grade;

            if (marks >= 90 && marks <= 100) {
                grade = "A";
            } else if (marks >= 75 && marks <= 89) {
                grade = "B";
            } else if (marks >= 60 &&  marks <= 74) {
                grade = "C";
            } else if (marks >= 45 &&  marks <= 59) {
                grade = "D";
            } else {
                grade = "F";
            }

            System.out.println("Grade calculated!");
            System.out.println("Grade: " + grade);

        } else {
            throw new StudentException("Student not found!");
        }
    }

    public void removeStudent(int studentId) throws StudentException {
        if (students.containsKey(studentId)) {
            students.remove(studentId);
            System.out.println("Student removed!");
        } else {
            throw new StudentException("Student not found!");
        }
    }

    public void displayTopper() {
        Student topStudent = null;
        double currentMarks = 0;
        for (Student s : students.values()) {
            if (s.getMarks() > currentMarks) {
                currentMarks = s.getMarks();
                topStudent = s;
            }
        }

        if (topStudent != null) {
            System.out.println("Top Student Details:");
            ;
            System.out.println("Student Id: " + topStudent.getId());
            System.out.println("Student Name: " + topStudent.getName());
            System.out.println("Top Marks: " + topStudent.getMarks());
            System.out.println("Top Attendance: " + topStudent.getAttendance());
        } else {
            System.out.println("Student not found!");
        }
    }


}
