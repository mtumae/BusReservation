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
        public void handleSignIn(ActionEvent event) throws IOException {
            if (ad_no.getText().isEmpty() || pass.getText().isEmpty()) {
                message_box("Please enter admission number and password", "OK");
                return;
            }

            DB db = new DB();
            try (Connection conn = db.connect_to_db("OOP_II-Project", "postgres", "admin123")) {
                if (db.checkUser(conn, ad_no.getText(), pass.getText())) {
                    Dashboard dashboard = new Dashboard();
                    dashboard.start(new Stage());
                    ((Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow()).close();
                } else {
                    message_box("Invalid admission number or password", "OK");
                }
            } catch (Exception e) {
                message_box("Error connecting to database", "OK");
                e.printStackTrace();
            }
        }
    }



