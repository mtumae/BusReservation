package com.busreservation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB {
    public static Connection connect() throws SQLException {
        try {
            // Get database credentials from DatabaseConfig class
            var jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
            var user = "postgres";
            var password = "admin123";
            // Open a connection
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin123");
        } catch (SQLException  e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}



class Main {
    public static void main(String[] args){

        try (var connection =  DB.connect()){
            System.out.println("Connected to the PostgreSQL database.");
        } catch (SQLException e) {
            System.err.println("Connection failed: " +e.getMessage());
        }
    }
}
