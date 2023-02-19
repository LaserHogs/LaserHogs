package com.example.lasertagproject;

import java.sql.*;

public class PostgresConnection {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public PostgresConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;

        try {
            // create a new connection to the database
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addData(String name, int id, String lastname, String codename) {
        try {
            // create a prepared statement with a parameterized SQL query
            PreparedStatement statement = connection.prepareStatement("INSERT INTO player (id, first_name, last_name, codename) VALUES (?, ?, ?, ?)");

            // set the parameter values for the statement
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, lastname);
            statement.setString(4, codename);

            // execute the statement to insert the data into the table
            int rowsAffected = statement.executeUpdate();

            System.out.println(rowsAffected + " rows inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
