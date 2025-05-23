package com.busreservation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB {
    public static Connection connect() throws SQLException {
        try {
            // Get database credentials from DatabaseConfig class
            var jdbcUrl = "";
            var user = "";
            var password = "";
            // Open a connection
            return DriverManager.getConnection(jdbcUrl, user, password);
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
            System.err.println(e.getMessage());
        }
    }
}
