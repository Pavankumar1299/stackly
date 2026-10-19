
package org.example;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    public void addStudent(Student student) throws SQLException {

        if (student.getName() == null || student.getName().isBlank()) {
            System.out.println("Student name cannot be empty!");
            return;
        }

        if (student.getEmail() == null || student.getEmail().isBlank()) {
            System.out.println("Student email cannot be empty!");
            return;
        }

        if (student.getStatus() == null) {
            System.out.println("Student status cannot be empty!");
            return;
        }

        String sql = "INSERT INTO students (name, email, status) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getStatus().name());

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Student registered successfully!");
            } else {
                System.out.println("Student registration failed!");
            }
        }
    }

    public Student getStudentById(int id) throws SQLException {

        String sql = "SELECT id, name, email, status FROM students WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (java.sql.ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            StudentStatus.valueOf(rs.getString("status"))
                    );
                }
            }
        }

        return null;
    }

    public void searchStudent(int id) throws SQLException {

        Student student = getStudentById(id);

        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found!");
        }
    }

    public void displayAllStudents() throws SQLException {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT id, name, email, status FROM students";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        StudentStatus.valueOf(rs.getString("status"))
                );

                students.add(student);
            }
        }

        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }

        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void updateStudent(Student student) throws SQLException {

        String sql = "UPDATE students SET name = ?, email = ?, status = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getStatus().name());
            ps.setInt(4, student.getId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found!");
            }
        }
    }

    public void deleteStudent(int id) throws SQLException {

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }
        }
    }

    public boolean addMarks(int studentId, int courseId, double marks) throws SQLException {

        String sql = """
                INSERT INTO marks (student_id, course_id, marks)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setDouble(3, marks);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean enrollStudent(int studentId, int courseId) throws SQLException {

        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean addCourse(Course course) throws SQLException {

        String sql = "INSERT INTO courses (course_name, duration) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, course.getCourseName());
            ps.setInt(2, course.getDuration());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Course> getAllCourses() throws SQLException {

        List<Course> courses = new ArrayList<>();

        String sql = "SELECT course_id, course_name, duration FROM courses";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Course course = new Course(
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getInt("duration")
                );

                courses.add(course);
            }
        }

        return courses;
    }

    public boolean markAttendance(int enrollmentId, LocalDate attendanceDate, String attendanceStatus) throws SQLException {

        String sql = """
                INSERT INTO attendance
                    (enrollment_id, attendance_date, attendance_status)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, enrollmentId);
            ps.setDate(2, Date.valueOf(attendanceDate));
            ps.setString(3, attendanceStatus);

            return ps.executeUpdate() > 0;
        }
    }

}
