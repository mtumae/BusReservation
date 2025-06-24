package com.busreservation;

import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

    public class Profile {
        public void logout(ActionEvent event) throws IOException {
            Home home = new Home();
            home.start(new Stage());
            ((javafx.stage.Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow()).close();
        }

        public void showProfile(ActionEvent event) {
            // Profile display logic can be added here
            System.out.println("Showing user profile...");
        }
    }

    //Logout

