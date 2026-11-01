package org.example.sra.service;

import org.example.sra.exception.StudentNotFoundException;
import org.example.sra.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final Map<Integer, Student> students = new HashMap<>();

    public StudentService() {
        students.put(1001, new Student(1001,"Pavan Pattar", "Advance Java", "pk@gmail.com", 27));
        students.put(1002, new Student(1002,"Anushka Sharma", "Programming in C", "anu@gmail.com", 24));
        students.put(1003, new Student(1003,"Dev Rock", "Python", "dev@gmail.com", 26));
        students.put(1004, new Student(1004,"Jon Snow", "DSA", "jonsnow@gmail.com", 25));
        students.put(1005, new Student(1005,"Tony Stark", "Advance Java", "tony@gmail.com", 24));
    }

    private int nextStudentId = 1006;

    // Register a student
    public Student registerStudent(Student student) {

        int id = nextStudentId++;

        Student newStudent = new Student(
                id,
                student.getStudentName(),
                student.getCourseName(),
                student.getEmail(),
                student.getAge()
        );

        students.put(id, newStudent);

        return newStudent;
    }

    // View all registered students
    public List<Student> getAllStudents() {

        return new ArrayList<>(students.values());
    }

    // Find a student by ID
    public Student getStudentById(int studentId) {

        Student student = students.get(studentId);

        if (student == null) {
            throw new StudentNotFoundException(
                    "Student not found with ID: " + studentId
            );
        }

        return student;
    }

    // Delete a student's registration
    public void deleteStudent(int studentId) {

        if (!students.containsKey(studentId)) {
            throw new StudentNotFoundException(
                    "Student not found with ID: " + studentId
            );
        }

        students.remove(studentId);
    }
}
