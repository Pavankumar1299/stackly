package com.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/demo";
        String username = "root";
        String password = "1299";

        String createTable = "CREATE TABLE IF NOT EXISTS students (" +
                "id INT PRIMARY KEY, " +
                "name VARCHAR(20), " +
                "age INT)";

        String insertQuery =
                "INSERT INTO students (id, name, age) VALUES (?, ?, ?)";

        try (Connection con =
                     DriverManager.getConnection(url, username, password)) {

            System.out.println("Connected to database successfully");

            try (PreparedStatement ps = con.prepareStatement(createTable)) {
                ps.executeUpdate();
                System.out.println("Table created successfully");
            }

//            try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
//                ps.setInt(1, 2);
//                ps.setString(2, "Ravan");
//                ps.setInt(3, 29);
//
//                ps.executeUpdate();
//                System.out.println("Record inserted successfully");
//            }

            String selectQuery = "SELECT * FROM students";

            try (PreparedStatement ps = con.prepareStatement(selectQuery)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        System.out.println(rs.getInt("id") + "," +
                                rs.getString("name") + "," +
                                rs.getInt("age"));
                    }
                }
            }

            try (Statement st = con.createStatement()) {
                String name = "Pavan' OR '1'='1";

                String sql =
                        "SELECT * FROM students WHERE name = '" + name + "'";

                st.executeQuery(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
