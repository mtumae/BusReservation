package com.busreservation;
import javafx.event.ActionEvent;

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


    ArrayList<String> getLocations(Connection conn, ActionEvent event){
        Statement statement;
        ArrayList<String> locationdetails = new ArrayList<>();
        try{
            String query = "select * from Locations;";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                locationdetails.add(result.getString("title"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return locationdetails;
    }

    ArrayList<ArrayList<String>> getAllBuses(Connection conn, ActionEvent event){
        Statement statement;
        ArrayList<ArrayList<String>> busdetails = new ArrayList<>();
        ArrayList<String> busId = new ArrayList<>();
        ArrayList<String> bustype = new ArrayList<>();
        ArrayList<String> seats = new ArrayList<>();
        ArrayList<String> arrivaltime = new ArrayList<>();
        ArrayList<String> alighttime = new ArrayList<>();
        try{
            String query = "select * from Bus";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                busId.add(result.getString("bus_id"));
                bustype.add(result.getString("bus_type"));
                seats.add(result.getString("seats"));
                arrivaltime.add(result.getString("arrival_time"));
                alighttime.add(result.getString("alight_time"));
            }
            busdetails.add(busId);
            busdetails.add(bustype);
            busdetails.add(seats);
            busdetails.add(arrivaltime);
            busdetails.add(alighttime);
            System.out.println("Bus Details: \n"+busdetails);
        }catch (Exception e){
            System.out.println(e);
        }
        return busdetails;
    };

    ArrayList<ArrayList<String>> getAllReservation(Connection conn, ActionEvent event){
        ArrayList<ArrayList<String>> reservationDetails = new ArrayList<>();
        ArrayList<String> resId = new ArrayList<>();
        ArrayList<String> adnos = new ArrayList<>();
        ArrayList<String> busId = new ArrayList<>();
        ArrayList<String> submittedOn = new ArrayList<>();
        Statement statement;
        try{
            String query = "select * from Reservations";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                resId.add(result.getString("reservation_id"));
                adnos.add(result.getString("admission_number"));
                busId.add(result.getString("bus_id"));
                submittedOn.add(result.getString("submitted_on"));
            }
            reservationDetails.add(resId);
            reservationDetails.add(adnos);
            reservationDetails.add(busId);
            reservationDetails.add(submittedOn);
            System.out.println("User reservation details: \n"+reservationDetails);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } return reservationDetails;
    }


    ArrayList<ArrayList<String>> getReservation(Connection conn, ActionEvent event, String ad_no){
        ArrayList<ArrayList<String>> reservationDetails = new ArrayList<>();
        ArrayList<String> resId = new ArrayList<>();
        ArrayList<String> busId = new ArrayList<>();
        ArrayList<String> submittedOn = new ArrayList<>();
        Statement statement;
        try{
            String query = "select * from Reservations where admission_number like '"+ad_no+"'";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                resId.add(result.getString("reservation_id"));
                busId.add(result.getString("bus_id"));
                submittedOn.add(result.getString("submitted_on"));
            }
            reservationDetails.add(resId);
            reservationDetails.add(busId);
            reservationDetails.add(submittedOn);
            System.out.println("User reservation details: \n"+reservationDetails);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } return reservationDetails;
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
        ArrayList<Integer> seats = new ArrayList<>();
        ArrayList<String> arrival_time = new ArrayList<>();
        ArrayList<String> alight_time = new ArrayList<>();
        Statement statement;
        try {
            String query = "select * from Bus where seats>0";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                bus_id.add(result.getString("bus_id"));
                bus_type.add(result.getString("bus_type"));
                seats.add(result.getInt("seats"));
                arrival_time.add(result.getString("arrival_time"));
                alight_time.add(result.getString("alight_time"));
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

            Dashboard(event, bus_type, seats, arrival_time, alight_time, bus_id, ad_no);
        }catch (SQLException | IOException e) {
            System.out.println(e);
        };
    }


    //Query specific user
    public void getUser(Connection conn, ActionEvent event, String ad_no){
        ArrayList<String> userDetails = new ArrayList<>();
        Statement statement;
        try {
            String query = "select * from Users where admission_number='" + ad_no + "' limit 1;";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                userDetails.add(result.getString("admission_number"));
                userDetails.add(result.getString("fname"));
                userDetails.add(result.getString("lname"));
                userDetails.add(result.getString("user_role"));
                userDetails.add(result.getString("email"));
            }
            System.out.println("User details: "+userDetails);
            Profile(event, userDetails);
        } catch (IOException | SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }


    void checkUser(Connection conn, ActionEvent event, String ad_no, String pass){
        Statement statement;
        try {
            String query = "select * from Users where admission_number='" + ad_no + "' limit 1;";
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);
            boolean exists = result.next();
            if(exists){
                if(Objects.equals(pass, result.getString("password"))){
                    System.out.println(true +" User exists and password is correct");
                    message_box("Signed in successfully","Ok");
                    getUser(conn, event, ad_no);
                }
                else{
                    System.out.println(true +" User exists but password is not correct");
                    System.out.println(pass+" should equal "+result.getString("password")+" is your password");
                    message_box("Password is incorrect","Try again");
                }
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
    void getBusbyUser(Connection conn, ActionEvent event, String ad_no){
        ArrayList<String> arrival_time = new ArrayList<>();
        ArrayList<String> alight_time = new ArrayList<>();
        ArrayList<String> busId = new ArrayList<>();
        ArrayList<String> submittedOn = new ArrayList<>();
        Statement statement;
        try {
            String query = "select * from Reservations where admission_number='"+ ad_no +"' ";
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
        System.out.println("Deducting seats by 1...");
        try{
            String query = "update Bus set seats=seats-1 where bus_id like '"+bus_id+"' and seats>1;";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Seats updated successfully!");
        } catch (Exception e) {
            message_box("Error updating seats!","Try again or pick another bus.");
            System.out.println(e);
        }
        System.out.println("Adding reservation...");
        try{
            String query = "insert into Reservations(admission_number, bus_id, submitted_on) values('"+ad_no+"','"+bus_id+"','"+submitted_on+"' )";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Reservation made successfully!");
            message_box("Your reservation has been made!", "Ok.");
        } catch (Exception e) {
            message_box("Failed to add reservation","Try again!");
            System.out.println(e);
        }

    }

    void addBus(Connection conn, ActionEvent event, String bus_id, String bus_type, Integer seats, String arrivaltime, String alighttime){
        Statement statement;
        try {
            String query = "insert into Bus values('"+bus_id+"','"+bus_type+"','"+seats+"','"+arrivaltime+"','"+alighttime+"');";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Bus added!");
            message_box(bus_id+": has been added!", "Ok.");
            Admin(event);
        } catch (SQLException e) {
            message_box("Failed to add: "+bus_id, "Ok.");
            System.out.println(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void createUser(Connection conn, ActionEvent event, String ad_no, String fname, String lname, String email, String pass){
        System.out.println("Creating user...");
        Statement statement;
        try{
            String query = "insert into Users values('"+ad_no+"','"+fname+"','"+lname+"','student','"+email+"','"+pass+"')";
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("User "+ad_no+" created!");
            getUser(conn, event, ad_no);
        } catch (Exception e) {
            message_box("Error creating user!","Try again!");
            System.out.println(e);
        }
    }
    }