package com.busreservation;
import javafx.event.ActionEvent;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class DB extends Home{
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


    void getReservation(Connection conn, ActionEvent event){
        ArrayList<String> ResId = new ArrayList<>();
        ArrayList<String> admission_no = new ArrayList<>();
        ArrayList<String> busId = new ArrayList<>();
        ArrayList<String> submittedOn = new ArrayList<>();
        Statement statement;
        try{
            String query = "select * from Reservations";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                admission_no.add(result.getString("admission_number"));
                ResId.add(result.getString("reservation_id"));
                busId.add(result.getString("bus_id"));
                submittedOn.add(result.getString("submitted_on"));
            }
            System.out.println("Users:");
            System.out.println(admission_no);
            System.out.println(ResId);
            System.out.println(busId);
            System.out.println(submittedOn);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    void getAllUser(Connection conn, ActionEvent event){
        ArrayList<String> admission_no = new ArrayList<>();
        ArrayList<String> fname = new ArrayList<>();
        ArrayList<String> lname = new ArrayList<>();
        ArrayList<String> role = new ArrayList<>();
        ArrayList<String> email = new ArrayList<>();
        Statement statement;

        try{
            String query = "select * from Users";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                admission_no.add(result.getString("admission_number"));
                fname.add(result.getString("fname"));
                lname.add(result.getString("lname"));
                role.add(result.getString("user_role"));
                email.add(result.getString("email"));
            }
            System.out.println("Users:");
            System.out.println(admission_no);
            System.out.println(fname);
            System.out.println(lname);
            System.out.println(role);
            System.out.println(email);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    void getBus(Connection conn, ActionEvent event, String ad_no){
        ArrayList<String> bus_id = new ArrayList<>();
        ArrayList<String> bus_type = new ArrayList<>();
        ArrayList<String> seats = new ArrayList<>();
        ArrayList<String> arrival_time = new ArrayList<>();
        ArrayList<String> alight_time = new ArrayList<>();
        ArrayList<String> stage = new ArrayList<>();
        Statement statement;
        try {
            String query = "select * from Bus";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                bus_id.add(result.getString("bus_id"));
                bus_type.add(result.getString("bus_type"));
                seats.add(result.getString("seats"));
                arrival_time.add(result.getString("arrival_time"));
                alight_time.add(result.getString("alight_time"));
                stage.add(result.getString("stage"));
            }
            System.out.println("Bus type:");
            System.out.println(bus_type);
            System.out.println(bus_id);
            System.out.println("\nSeat:");
            System.out.println(seats);
            System.out.println("\nArrival time:");
            System.out.println(arrival_time);
            System.out.println("\nAlight time:");
            System.out.println(alight_time);
            Dashboard(event, stage, seats, arrival_time,ad_no);
        }catch (SQLException | IOException e) {
            throw new RuntimeException(e.getCause());
        };
    }


    //Query specific user
    void getUser(Connection conn, ActionEvent event, String ad_no){
        Statement statement;
        try {
            String query = "select * from Users where ad_no='" + ad_no + "' limit 1;";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            boolean exists = result.next();
            if(exists){
                System.out.println("User"+ad_no+" exists...");
            }
            else{
                System.out.println("User does not exist");
                message_box("User does not exist. Try again!","Ok");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    void checkUser(Connection conn, ActionEvent event, String ad_no){
        Statement statement;
        try {
            String query = "select * from Students where ad_no='" + ad_no + "' limit 1;";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            boolean exists = result.next();
            if(exists){
                System.out.println("User"+ad_no+" exists! \n Logging you in...");
                //Send user to profile
            }
            else{
                System.out.println(false +" User does not exist");
                message_box("User does not exist. Try again!","Ok");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    //Join for users on reservations
    void userReservations(Connection conn, ActionEvent event){
        ArrayList<String> admission_number = new ArrayList<>();
        ArrayList<String> arrival_time = new ArrayList<>();
        ArrayList<String> alight_time = new ArrayList<>();
        ArrayList<String> busId = new ArrayList<>();
        ArrayList<String> submittedOn = new ArrayList<>();
        Statement statement;
        try {
            String query = "select * from Reservations left outer join Users using (ad_no) right outer join";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){

            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getCause());
        }
    }


    //---------------------------------------------------------------------------------------
    // INSERT

    void addReservation(Connection conn, ActionEvent event, String ad_no, String bus_id, String submitted_on){
        Statement statement;
        System.out.println("Adding reservation...");
        try{
            String query = "insert values into Reservations('"+ad_no+"','"+bus_id+"','"+submitted_on+"' )";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Data added successfully!");

        } catch (Exception e) {
            message_box("Failed to add reservation","Try again!");
            throw new RuntimeException(e);
        }

    }

    void createUser(Connection conn, ActionEvent event, String ad_no, String fname, String lname, String email){
        Statement statement;
        try{
            String query = "insert values into Users('"+ad_no+"','"+fname+"','"+lname+"','student','"+email+"' )";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("User "+ad_no+" created!");
            Profile(event);
        } catch (SQLException | IOException e) {
            message_box("Error creating user!","Try again!");
            throw new RuntimeException(e.getCause());
        }

    }

    }