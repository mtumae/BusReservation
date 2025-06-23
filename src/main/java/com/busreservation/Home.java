package com.busreservation;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home extends Application {
    private Stage stage;
    private Scene scene;
    Image dashIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("circuit-board.png")));
    Image circleuserIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("circle-user.png")));
    Image houseIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("house.png")));
    Image userIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("user.png")));
    Image saveIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("save.png")));
    Image westgate = new Image(Objects.requireNonNull(getClass().getResourceAsStream("westgate.png")));
    Image ngara = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Ngara.png")));
    Image town = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Town.png")));
    Image tmall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("T-mall.png")));
    Image bus_coaster = new Image(Objects.requireNonNull(getClass().getResourceAsStream("coaster.jpg")));
    Image big_bus = new Image(Objects.requireNonNull(getClass().getResourceAsStream("bigbus.jpeg")));
    HBox layout = new HBox(10);
    VBox navbar = new VBox(20);
    Button adminbtn = new Button("");
    Button profilebtn = new Button("");
    Button homebtn = new Button("");
    Button dashbtn = new Button("");
    ImageView img = new ImageView(westgate);
    ImageView bus1 = new ImageView(bus_coaster);
    ImageView houseI = new ImageView(houseIcon);
    ImageView dashI = new ImageView(dashIcon);
    ImageView userI = new ImageView(userIcon);
    ImageView saveI = new ImageView(saveIcon);
    ImageView circleuserI = new ImageView(circleuserIcon);
    DropShadow ds = new DropShadow();

    public void message_box(String error_type, String button_text){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Alert");
        dialog.setContentText(error_type);
        ButtonType type = new ButtonType(button_text, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.show();
    }


    public void Profile(ActionEvent event)throws IOException{
        HBox layout = new HBox(5);
        VBox container = new VBox(5);
        HBox description = new HBox(100);
        HBox orders = new HBox(10);

        //Components for Description
        Label name = new Label("Mtume Owino Mutere");
        Label ad_no = new Label("188916");
        Label role = new Label("Admin");
        Button logoutbtn = new Button("Logout");
        Button reservebtn = new Button("Reserve");
        description.getChildren().addAll(name, ad_no, role, reservebtn, logoutbtn);
        description.setAlignment(Pos.CENTER);
        description.setEffect(ds);
        orders.setEffect(ds);


        //Components for orders
        VBox innerorder = new VBox(5);
        Label orderlbl = new Label("Previous orders");
        HBox tableheader = new HBox(100);
        Label datelbl = new Label("Date");
        Label stagelbl = new Label("Stage");
        Label destinationlbl = new Label("Destination");
        Label buscodelbl = new Label("Bus code");
        Label timelbl = new Label("Time");
        tableheader.getChildren().addAll(datelbl, stagelbl, destinationlbl, buscodelbl, timelbl);
        innerorder.getChildren().addAll(orderlbl, tableheader);
        orders.getChildren().addAll(innerorder);

        //Styling
        name.setStyle("-fx-font-size: 20;");
        logoutbtn.setStyle("-fx-background-color: red;");

        ds.setOffsetY(2.0f);
        ds.setOffsetX(2.0f);
        ds.setColor(Color.BLACK);

        homebtn.setGraphic(houseI);
        profilebtn.setGraphic(userI);
        dashbtn.setGraphic(dashI);

        houseI.setFitHeight(20);
        houseI.setFitWidth(20);
        dashI.setFitHeight(20);
        dashI.setFitWidth(20);
        userI.setFitWidth(20);
        userI.setFitHeight(20);
        saveI.setFitWidth(20);
        saveI.setFitHeight(20);

        description.setPrefWidth(1300);
        orders.setPrefWidth(1300);
        description.setPrefHeight(200);
        orders.setPrefHeight(800);

        navbar.setAlignment(Pos.CENTER);
        navbar.setPadding(new Insets(20));
        layout.setPadding(new Insets(20));
        description.setPadding(new Insets(20));
        orders.setPadding(new Insets(20));


        navbar.getChildren().addAll(adminbtn, profilebtn, homebtn, dashbtn);
        description.setStyle("-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #e0e0e0; -fx-border-width: 1px;-fx-background-color: #e0e0e0;");
        orders.setStyle("-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #e0e0e0; -fx-border-width: 1px;-fx-background-color: #e0e0e0;");
        layout.setStyle("-fx-background-color: #030f0f");

        container.getChildren().addAll(description, orders);
        layout.getChildren().addAll(navbar, container);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Profile");
        stage.getScene().setRoot(layout);
    }

    public void Register(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Register");
        stage.getScene().setRoot(root);
    }

    public void Admin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Admin");
        stage.getScene().setRoot(root);
    }

    public void Signin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signin-view.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Sing in");
        stage.getScene().setRoot(root);
    }


    public void populateDashboard(ActionEvent event, String ad_no){
        DB db = new DB();
        Connection conn = db.connect_to_db("Bus-Reservation-System", "postgres", "");
        db.getBus(conn, event, ad_no);
    }


    public void Dashboard(ActionEvent event, ArrayList locations, ArrayList seats, ArrayList times, String ad_no) throws IOException {
        //Arrays
//        String[] locations = { "Westgate", "T-Mall", "Galleria", "Town", "Ngara" };
//        String[] seats = { "s1", "s2", "s3", "s4", "s5", "s6", "s7" };
//        String[] times = { "8:00am", "9:00am", "10:00am", "11:00am" };


        List<String> res = new ArrayList<String>();
        //init arraylist
        res.add("LOCATION");
        res.add("TIME");
        res.add("SEAT");
        System.out.println("Initialized array list: "+res);

        //components
        StackPane stackp = new StackPane();
        HBox cardContainer = new HBox(5);
        VBox cardDescription = new VBox(5);
        VBox resDescription = new VBox(5);
        HBox time_container = new HBox(5);
        Button savebtn = new Button("Save");
        CheckBox bookbus = new CheckBox("Book");
        HBox busheader = new HBox(480);
        HBox seat_container = new HBox(4);
        HBox selectorMenu = new HBox(20);
        VBox container = new VBox(20);
        Label locationlbl = new Label("Select a location: ");
        Label seatlbl = new Label("Select a seat: ");
        Label timelbl = new Label("Select a time: ");
        Label buslbl = new Label("Coaster");
        Text rating = new Text("⭐⭐⭐⭐");
        Text bustext = new Text("Compact, mid-sized minibus designed to seat around 22–30 passengers. It has a boxy shape with large windows, dual rear wheels for stability, and typically features a sliding passenger door. Inside, it offers rows of forward-facing seats with a narrow center aisle, and may include air conditioning.");
        Text restext = new Text("Start clicking stuff to generate a receipt");
        DropShadow ds = new DropShadow();

        ds.setOffsetY(2.0f);
        ds.setOffsetX(2.0f);
        ChoiceBox locationselector = new ChoiceBox(FXCollections.observableArrayList(locations));
        ChoiceBox seatselector = new ChoiceBox(FXCollections.observableArrayList(seats));
        ChoiceBox timeselector = new ChoiceBox(FXCollections.observableArrayList(times));



        navbar.setAlignment(Pos.CENTER);
        ds.setColor(Color.GRAY);

        navbar.setPadding(new Insets(20));
        layout.setPadding(new Insets(10));
        resDescription.setPadding(new Insets(20));
        container.setPadding(new Insets(20));
        locationselector.setStyle("-fx-background-color: white;");

        adminbtn.setCursor(Cursor.HAND);
        profilebtn.setCursor(Cursor.HAND);
        homebtn.setCursor(Cursor.HAND);
        dashbtn.setCursor(Cursor.HAND);
        savebtn.setCursor(Cursor.HAND);
        dashI.setFitHeight(20);
        dashI.setFitWidth(20);


        layout.setStyle("-fx-background-color: #030f0f;");
        buslbl.setStyle("-fx-font-size: 18px; -fx-text-fill: #030f0f;");
        bustext.setStyle("-fx-text-fill: #e0e0e0;");
        rating.setStyle("-fx-font-size: 18px; -fx-text-fill: #fdbf04;");
        seatselector.setStyle("-fx-background-color: white;");
        seatselector.setEffect(ds);
        timeselector.setEffect(ds);
        timeselector.setStyle("-fx-background-color: white;");
        cardContainer.setStyle("-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #e0e0e0; -fx-border-width: 1px;-fx-background-color: #e0e0e0;");
        cardDescription.setStyle("-fx-padding: 5px; -fx-border-insets: 5px;-fx-background-insets: 5px;");
        layout.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: #e0e0e0; -fx-border-radius: 25px; -fx-background-radius: 5px;");
        resDescription.setStyle("-fx-background-color: #e0e0e0; -fx-border-radius: 25px; -fx-background-radius: 5px;");
        locationselector.setPrefWidth(500);
        cardContainer.setEffect(ds);
        seatselector.setMaxWidth(300);
        seatselector.setEffect(ds);
        timeselector.setMaxWidth(300);
        timeselector.setEffect(ds);
        img.setFitWidth(800);
        img.setFitHeight(300);

        //Button Icons
        houseI.setFitHeight(20);
        houseI.setFitWidth(20);
        userI.setFitWidth(20);
        userI.setFitHeight(20);
        saveI.setFitWidth(20);
        saveI.setFitHeight(20);
        selectorMenu.setMaxWidth(300);
        selectorMenu.setMaxHeight(50);
        bustext.setWrappingWidth(600);
        cardDescription.setPrefHeight(100);
        cardDescription.setPrefWidth(100);
        bus1.setFitWidth(190);
        bus1.setFitHeight(120);

        dashbtn.setGraphic(dashI);

        //Selectors
        locationselector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                // set the text for the label to the selected item
                System.out.println("Selecting "+ locations.get(new_value.intValue()));
                locationlbl.setText(locations.get(new_value.intValue()) + " selected");
                locationlbl.setStyle("-fx-text-fill: green;");
            }
        });

        locationselector.setOnAction(e->{
            if(Objects.equals(locationselector.getValue().toString(), "Ngara")){
                img.setImage(ngara);
                res.set(0, locationselector.getValue().toString());
                System.out.println(res);
            } else if (Objects.equals(locationselector.getValue().toString(), "Westgate")) {
                img.setImage(westgate);
                res.set(0, locationselector.getValue().toString());
                System.out.println(res);
            }else if (Objects.equals(locationselector.getValue().toString(), "Town")) {
                img.setImage(town);
                res.set(0, locationselector.getValue().toString());
                System.out.println(res);
            }else if (Objects.equals(locationselector.getValue().toString(), "T-Mall")) {
                img.setImage(tmall);
                res.set(0, locationselector.getValue().toString());
                System.out.println(res);
            }
        });
        seatselector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                // set the text for the label to the selected item
                System.out.println("Selecting "+ seats.get(new_value.intValue()));
                seatlbl.setText(seats.get(new_value.intValue()) +" selected.");
                seatlbl.setStyle("-fx-text-fill: green;");
                res.set(2, (String) seats.get(new_value.intValue()));
                System.out.println("Updated array: "+res);
            }
        });

        timeselector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                System.out.println("Selecting "+ times.get(new_value.intValue()));
                timelbl.setText(times.get(new_value.intValue()) +" selected.");
                timelbl.setStyle("-fx-text-fill: green;");
                res.set(1, (String) times.get(new_value.intValue()));
                System.out.println("Updated array: "+res);
            }
        });


        //Button events
        savebtn.setOnAction(e->{
            try{
                //db.addReservation(conn, e, );
            } catch (Exception ex) {
                throw new RuntimeException(ex.getCause());
            }
        });

        /*
        adminbtn.setOnAction(e->{
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profile-view.fxml")));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setTitle("Profile");
                stage.getScene().setRoot(root);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        profilebtn.setOnAction(e ->{
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("profile-view.fxml")));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setTitle("Profile");
                stage.getScene().setRoot(root);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } );
        */

        //children
        savebtn.setGraphic(saveI);
        resDescription.getChildren().addAll(restext, locationlbl, seatlbl, timelbl, savebtn);
        homebtn.setGraphic(houseI);
        profilebtn.setGraphic(userI);
        busheader.getChildren().addAll(buslbl, bookbus);
        cardDescription.getChildren().addAll(busheader, rating, bustext);
        cardContainer.getChildren().addAll(bus1, cardDescription);
        navbar.getChildren().addAll(adminbtn, profilebtn, homebtn, dashbtn);

        selectorMenu.getChildren().add(locationselector);
        stackp.getChildren().addAll(img, selectorMenu);
        StackPane.setAlignment(selectorMenu, Pos.BOTTOM_CENTER);
        container.getChildren().add(stackp);
        container.getChildren().add(cardContainer);
        layout.getChildren().add(navbar);
        layout.getChildren().add(container);
        layout.getChildren().add(resDescription);


        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.getScene().setRoot(layout);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Home");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}