package com.busreservation;
import javafx.event.ActionEvent;

import java.sql.*;
import java.util.ArrayList;

public class DB{
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Connection Failed");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    void getBus(Connection conn, ActionEvent event){
        ArrayList<String> bus_id = new ArrayList<>();
        ArrayList<String> bus_type = new ArrayList<>();
        ArrayList<String> seats = new ArrayList<>();
        ArrayList<String> arrival_time = new ArrayList<>();
        ArrayList<String> alight_time = new ArrayList<>();
        Statement statement;
        try {
            String query = "select * from Bus";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                bus_type.add(result.getString("bus_type"));
                seats.add(result.getString("seats"));
                arrival_time.add(result.getString("arrival_time"));
                alight_time.add(result.getString("alight_time"));
            }
            System.out.println("Bus type:");
            System.out.println(bus_type);
            System.out.println("\nSeat:");
            System.out.println(seats);
            System.out.println("\nArrival time:");
            System.out.println(seats);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        };}
    }