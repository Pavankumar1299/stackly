package org.example;

import java.util.HashMap;
import java.util.Map;

public class Student {

    private int id;
    private String name;
    private String email;
    private int attendance;
    private StudentStatus status;
    private Map<Integer, Double> courseMarks = new HashMap<>();
    private String grade;
    private double percentage;

    public Student(int id, String name, String email, int attendance, StudentStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.attendance = attendance;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public Map<Integer, Double> getCourseMarks() {
        return courseMarks;
    }

    public void setCourseMarks(Map<Integer, Double> courseMarks) {
        this.courseMarks = courseMarks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
//                ", marks=" + marks +
                ", attendance=" + attendance +
                ", status=" + status +
//                ", grade='" + grade + '\'' +
//                ", percentage=" + percentage +
                '}';
    }
}
