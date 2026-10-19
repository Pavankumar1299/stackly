
package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultService {

    public void generateResult(int studentId) throws SQLException {

        String sql = """
                SELECT COUNT(*) AS totalCourses,
                       AVG(marks) AS percentage
                FROM marks
                WHERE student_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    int totalCourses = rs.getInt("totalCourses");

                    if (totalCourses == 0) {
                        System.out.println("No marks found for this student!");
                        return;
                    }

                    double percentage = rs.getDouble("percentage");

                    String grade;

                    if (percentage >= 90) {
                        grade = "A";
                    } else if (percentage >= 75) {
                        grade = "B";
                    } else if (percentage >= 60) {
                        grade = "C";
                    } else if (percentage >= 50) {
                        grade = "D";
                    } else {
                        grade = "F";
                    }

                    System.out.println("Student ID: " + studentId);
                    System.out.printf("Percentage: %.2f%%%n", percentage);
                    System.out.println("Grade: " + grade);
                }
            }
        }
    }
}
