package com.busreservation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Objects;

public class Signin extends Home{
    public TextField ad_no;
    public TextField name;
    public PasswordField pass;
    private Stage stage;
    private Scene scene;
    private Parent root;


    //Sign in button click
    public void singIn(ActionEvent event) throws IOException {
        //PUT VALIDATION HERE PLEASE
        DB db = new DB();
        Connection conn = db.connect_to_db("Bus-Reservation-System", "postgres", "");
        db.checkUser(conn, event, ad_no.getText(), pass.getText());

    }



}


