package com.example;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/demo";
        String username = "root";
        String password = "1299";

        Statement statement = null;
        ResultSet resultSet = null;

        try (Connection connection =
                     DriverManager.getConnection(url, username, password)) {

            System.out.println("Database connected successfully!");

            String query = "select * from employees";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);

                System.out.println(id + " " + name + " " + email);
            }



        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
