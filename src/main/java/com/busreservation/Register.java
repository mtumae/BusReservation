package com.busreservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Objects;



public class Register extends Home{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welcomeText;
    public TextField  ad_no, fname, lname, email, pass1, pass2;

    public void Signin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signin-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Sing in");
        stage.getScene().setRoot(root);
    }

    public void registerUser(ActionEvent event) throws IOException {
        DB db = new DB();
        Connection conn = db.connect_to_db("Bus-Reservation-System", "postgres", "");
        //validation
        if (Objects.equals(ad_no.getText(), "") || Objects.equals(pass1.getText(), "") || Objects.equals(fname.getText(), "")|| Objects.equals(lname.getText(), "")){
            System.out.println("Ensure you have entered data into all fields");
            message_box("Invalid data! Ensure you have entered correct data into all fields","Try again");

        } else if (ad_no.getLength() != 6){
            System.out.println("Invalid Admission number. Has to be 6 digits!");
            message_box("Invalid Admission number. Has to be 6 digits!","Ok");
        }
        else if (pass1.getLength() < 8){
            System.out.println("Password is too short. Has to be more than 8 characters!");
            message_box("Password is too short. Has to be more than 8 characters!","Ok");
        } else if (!Objects.equals(pass1.getText(), pass2.getText()) ){
            System.out.println("Passwords do not match! Try again");
            message_box("Passwords do not match! Try again!","Ok");
        } else{
            db.createUser(conn, event, ad_no.getText(), fname.getText(), lname.getText(), email.getText(), pass1.getText());
        }}


}