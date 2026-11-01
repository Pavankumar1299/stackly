package org.example.sra.controller;

import java.util.List;

import org.example.sra.model.Student;
import org.example.sra.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/students")
@Validated
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 1. Register a student
    @PostMapping
    public ResponseEntity<Student> registerStudent(
            @RequestBody Student student) {

        Student registeredStudent =
                studentService.registerStudent(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registeredStudent);
    }

    // 2. View all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents()
        );
    }

    // 3. Find a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @PathVariable("id") @Positive int id) {

        return ResponseEntity.ok(
                studentService.getStudentById(id)
        );
    }

    // 4. Delete a student's registration
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable("id") @Positive int id) {

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }
}
