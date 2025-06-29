package com.busreservation;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.css.Selector;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Home extends Application {
    private Stage stage;
    private Scene scene;


    public void message_box(String error_type, String button_text){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Alert");
        dialog.setContentText(error_type);
        ButtonType type = new ButtonType(button_text, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.show();
    }




    public void Profile(ActionEvent event, ArrayList userdetails)throws IOException{
        DB db = new DB();
        Connection conn = db.connect_to_db("Bus-Reservation-System", "postgres", "");
        db.getBusbyUser(conn, event, (String) userdetails.getFirst());

        Image adminIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("admin.png")));
        Image dashIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("circuit-board.png")));
        Image circleuserIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("circle-user.png")));
        Image houseIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("house.png")));
        Image userIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("user.png")));
        Image saveIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("save.png")));
        Image busIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("bus.png")));
        Image vanIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("van.png")));
        HBox layout = new HBox(10);
        VBox navbar = new VBox(20);
        Button adminbtn = new Button("");
        Button profilebtn = new Button("");
        Button homebtn = new Button("");
        Button dashbtn = new Button("");
        ImageView circleuserI = new ImageView(circleuserIcon);
        DropShadow ds = new DropShadow();
        VBox container = new VBox(5);
        HBox description = new HBox(100);
        HBox orders = new HBox(10);
        ImageView busI = new ImageView(busIcon);
        ImageView vanI = new ImageView(vanIcon);
        ImageView houseI = new ImageView(houseIcon);
        ImageView adminI = new ImageView(adminIcon);
        ImageView dashI = new ImageView(dashIcon);
        ImageView userI = new ImageView(userIcon);
        ImageView saveI = new ImageView(saveIcon);

        //Components for Description
        Label name = new Label(userdetails.get(1)+" "+userdetails.get(2));
        Label admission = new Label((String) userdetails.getFirst());
        Label role = new Label((String) userdetails.get(3));
        Button logoutbtn = new Button("Logout");
        Button reservebtn = new Button("Reserve");
        description.getChildren().addAll(name, admission, role, reservebtn, logoutbtn);
        description.setAlignment(Pos.CENTER);
        description.setEffect(ds);
        orders.setEffect(ds);

        //Components for orders
        VBox innerorder = new VBox(5);
        Label orderlbl = new Label("Previous orders");
        HBox tableheader = new HBox(300);
        Label idlbl = new Label("Reservation Id");
        Label buscodelbl = new Label("Bus code");
        Label datelbl = new Label("Date");

        tableheader.getChildren().addAll(idlbl, buscodelbl, datelbl);
        innerorder.getChildren().addAll(orderlbl, tableheader);
        orders.getChildren().addAll(innerorder);

        ArrayList<ArrayList<String>> ReservationArray = db.getReservation(conn, event, (String) userdetails.getFirst());
        for(int i=0; i<ReservationArray.getFirst().size();i++){
            HBox row = new HBox(400);
            Text reservation = new Text(ReservationArray.getFirst().get(i));
            Text buscode = new Text(ReservationArray.get(1).get(i));
            Text date = new Text(ReservationArray.getLast().get(i));
            row.getChildren().addAll(reservation, buscode, date);
            innerorder.getChildren().addAll(row);
        }

        //Styling
        name.setStyle("-fx-font-size: 20;");
        logoutbtn.setStyle("-fx-background-color: red;");
        ds.setOffsetY(2.0f);
        ds.setOffsetX(2.0f);
        ds.setColor(Color.BLACK);

        homebtn.setGraphic(houseI);
        profilebtn.setGraphic(userI);
        dashbtn.setGraphic(dashI);
        adminbtn.setGraphic(adminI);

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


        //Actions
        dashbtn.setOnAction(e->{
            System.out.println(userdetails);
            try{
                populateDashboard(e, (String) userdetails.getFirst());
            } catch (Exception ex) {
                System.out.println(e);
            }
        });
        homebtn.setOnAction(e->{
            try {
                switchHome(e);
            } catch (IOException ex) {
                System.out.println(e);
            }
        });
        adminbtn.setOnAction(e->{
            try {
                Admin(e);
            } catch (IOException ex) {
                System.out.println(e);
            }
        });


        navbar.getChildren().addAll(adminbtn, homebtn, dashbtn);
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
        DB db = new DB();
        Connection conn = db.connect_to_db("Bus-Reservation-System", "postgres", "");
        HBox layout = new HBox(10);
        Image backIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("back.png")));
        ImageView backI = new ImageView(backIcon);
        Button backbtn = new Button("");
        backbtn.setGraphic(backI);
        //int[] seats = new int[]{10, 20, 30, 40, 50};
        String[] arrivaltimes = new String[]{"6:00am","7:00am", "8:00am", "9:00am"};
        String[] alighttimes = new String[]{"6:30am","7:30am", "8:15am", "9:15am"};
        String[] bustypes = new String[]{"Van","Bus", "Coaster", "Large bus"};


        //Form for buses
        Label busformlbl = new Label("Buses");
        HBox bustableheader = new HBox(200);
        HBox busformbox = new HBox(20);
        TextField busid_input = new TextField("Bus Id");
        ChoiceBox bustype_input = new ChoiceBox(FXCollections.observableArrayList(bustypes));
        ChoiceBox seats_input = new ChoiceBox(FXCollections.observableArrayList(10, 20, 30, 40, 50));
        seats_input.setPrefWidth(200);
        ChoiceBox arrival_input= new ChoiceBox(FXCollections.observableArrayList(arrivaltimes)); //also should be a selector
        arrival_input.setPrefWidth(200);
        ChoiceBox alight_input = new ChoiceBox(FXCollections.observableArrayList(alighttimes));
        alight_input.setPrefWidth(200);
        Button busformbtn = new Button("Add bus");
        HBox busform = new HBox(10);
        busform.setPadding(new Insets(30));
        VBox busesbox = new VBox(5);
        busformbtn.setOnAction(e->{
            try{
                db.addBus(conn, event, busid_input.getText(), (String) bustype_input.getValue(), (Integer) seats_input.getValue(), (String) arrival_input.getValue(), (String) alight_input.getValue());
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        busform.getChildren().addAll(busid_input, bustype_input, seats_input, arrival_input, alight_input, busformbtn);
        busesbox.setPadding(new Insets(20));
        busform.setPadding(new Insets(20));
        //ADD THE TABLE HEADER HERE
        Label busidlbl = new Label("Bus ID");
        Label bustypelbl = new Label("Bus Type");
        Label seatslbl = new Label("Seats");
        Label arrivaltimelbl = new Label("Arrival Time");
        Label alighttimelbl = new Label("Alight Time");
        bustableheader.getChildren().addAll(busidlbl, bustypelbl, seatslbl, arrivaltimelbl, alighttimelbl);
        busesbox.getChildren().add(bustableheader);
        ArrayList<ArrayList<String>> Buses = db.getAllBuses(conn, event);
        for(int i=0;i<Buses.getFirst().size();i++){
            HBox busrow = new HBox(250);
            Text busid_text = new Text(Buses.getFirst().get(i));
            Text bustype_text = new Text(Buses.get(1).get(i));
            Text seats_text = new Text(Buses.get(2).get(i));
            Text arrivaltime_text = new Text(Buses.get(3).get(i));
            Text alighttime_text = new Text(Buses.getLast().get(i));
            busrow.getChildren().addAll(busid_text, bustype_text, seats_text, arrivaltime_text, alighttime_text);
            busesbox.getChildren().add(busrow);
        }
        busesbox.getChildren().add(busformbox);
        busesbox.getChildren().addAll(busform);


        //Reservations
        Label reservationslbl = new Label("Reservations");
        reservationslbl.setStyle("-fx-font-size: 20;");
        VBox ressbox = new VBox(10);
        HBox resheader = new HBox(100);
        Label residlbl = new Label("Reservation ID");
        Label admissionnolbl = new Label("Admission number");
        Label busidreslbl = new Label("Bus ID");
        Label submittedonlbl = new Label("Submitted on");
        resheader.getChildren().addAll(residlbl, admissionnolbl, busidreslbl, submittedonlbl);
        ressbox.getChildren().addAll(reservationslbl, resheader);
        ressbox.setPadding(new Insets(50));
        ArrayList<ArrayList<String>> Reservations = db.getAllReservation(conn,event);
        for(int i=0;i<Reservations.getFirst().size();i++){
            HBox row = new HBox(200);
            Text resid = new Text(Reservations.getFirst().get(i));
            Text admno = new Text(Reservations.get(1).get(i));
            Text busid = new Text(Reservations.get(2).get(i));
            Text submittedon = new Text(Reservations.getLast().get(i));
            row.getChildren().addAll(resid, admno, busid, submittedon);
            ressbox.getChildren().add(row);
        }


        VBox datasection = new VBox(10);
        datasection.setAlignment(Pos.CENTER);
        datasection.getChildren().addAll(busesbox, ressbox);
        layout.setStyle("-fx-background-color: #030f0f;");
        busesbox.setStyle("-fx-padding: 5px; -fx-border-insets: 5px;-fx-background-insets: 5px;-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #e0e0e0; -fx-border-width: 1px;-fx-background-color: #e0e0e0;");
        ressbox.setStyle("-fx-padding: 5px; -fx-border-insets: 5px;-fx-background-insets: 5px;-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #e0e0e0; -fx-border-width: 1px;-fx-background-color: #e0e0e0;");
        layout.getChildren().addAll(backbtn, datasection);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Admin");
        stage.getScene().setRoot(layout);


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


    public void Dashboard(ActionEvent event, ArrayList bus_type, ArrayList seats, ArrayList arrivaltime, ArrayList alighttime, ArrayList bus_id, String ad_no) throws IOException {
        DB db = new DB();
        Connection conn = db.connect_to_db("Bus-Reservation-System", "postgres", "");
        Image circleuserIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("circle-user.png")));
        Image houseIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("house.png")));
        Image userIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("user.png")));
        Image saveIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("save.png")));
        Image westgate = new Image(Objects.requireNonNull(getClass().getResourceAsStream("westgate.png")));
        Image ngara = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Ngara.png")));
        Image town = new Image(Objects.requireNonNull(getClass().getResourceAsStream("Town.png")));
        Image tmall = new Image(Objects.requireNonNull(getClass().getResourceAsStream("T-mall.png")));
        Image busIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("bus.png")));
        Image vanIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("van.png")));
        HBox layout = new HBox(10);
        VBox navbar = new VBox(20);
        Button adminbtn = new Button("");
        Button profilebtn = new Button("");
        Button homebtn = new Button("");
        Button dashbtn = new Button("");
        ImageView img = new ImageView(westgate);
        ImageView busI = new ImageView(busIcon);
        ImageView vanI = new ImageView(vanIcon);
        ImageView houseI = new ImageView(houseIcon);
        ImageView userI = new ImageView(userIcon);
        ImageView saveI = new ImageView(saveIcon);
        ImageView circleuserI = new ImageView(circleuserIcon);
        DropShadow ds = new DropShadow();

        List<String> res = new ArrayList<String>();
        //init arraylist
        res.add("LOCATION");
        res.add("TIME");
        System.out.println("Initialized array list: "+res);

        //components
        StackPane stackp = new StackPane();
        VBox cardSection = new VBox(5);
        VBox resDescription = new VBox(5);
        HBox time_container = new HBox(5);
        Button savebtn = new Button("Save");
        CheckBox bookbus = new CheckBox("Book");
        HBox busheader = new HBox(480);
        HBox seat_container = new HBox(4);
        HBox selectorMenu = new HBox(20);
        VBox container = new VBox(20);
        Label locationlbl = new Label("Select a location: ");
        Label buslbl = new Label("Select a bus: ");
        Text restext = new Text("Pick a location and a bus to generate a receipt.");
        //DropShadow ds = new DropShadow();

        ds.setOffsetY(2.0f);
        ds.setOffsetX(2.0f);
        ChoiceBox locationselector = new ChoiceBox(FXCollections.observableArrayList(db.getLocations(conn, event)));
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

        layout.setStyle("-fx-background-color: #030f0f;");
        cardSection.setStyle("-fx-padding: 5px; -fx-border-insets: 5px;-fx-background-insets: 5px;-fx-border-radius: 5px; -fx-background-radius: 5px; -fx-border-color: #e0e0e0; -fx-border-width: 1px;-fx-background-color: #e0e0e0;");
        layout.setAlignment(Pos.CENTER);
        resDescription.setAlignment(Pos.CENTER);
        container.setStyle("-fx-background-color: #e0e0e0; -fx-border-radius: 25px; -fx-background-radius: 5px;");
        resDescription.setStyle("-fx-background-color: #e0e0e0; -fx-border-radius: 25px; -fx-background-radius: 5px;");
        locationselector.setPrefWidth(500);
        //cardSection.setEffect(ds);
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
        cardSection.setPrefHeight(100);
        cardSection.setPrefWidth(100);

        //Selectors
        locationselector.valueProperty().addListener((observable, oldval, newval)->{
            System.out.println(newval);
            if(Objects.equals(newval.toString(), "Westgate")){
                System.out.println("Westlands picked");
                img.setImage(westgate);
                locationlbl.setText("Westgate");
                res.set(0, newval.toString());
            } else if (Objects.equals(newval.toString(), "Ngara")){
                System.out.println("Ngara picked");
                img.setImage(ngara);
                locationlbl.setText("Ngara");
                res.set(0, newval.toString());
            }else if (Objects.equals(newval.toString(), "Town")){
                System.out.println("Town picked");
                img.setImage(town);
                locationlbl.setText("Town");
                res.set(0, newval.toString());
            }else if (Objects.equals(newval.toString(), "Tmall")){
                System.out.println("Tmall picked");
                img.setImage(tmall);
                locationlbl.setText("Tmall");
                res.set(0, newval.toString());
            }
            System.out.println(res);
        });

        busI.setFitWidth(20);
        busI.setFitHeight(20);
        vanI.setFitWidth(20);
        vanI.setFitHeight(20);
        //populating content


        for(int i=0;i<bus_type.size();i++){
            //Components
            Button pick = new Button("Book "+bus_id.get(i));
            Label cardlbl = new Label((String) bus_type.get(i)); //should be bus id
            Text desc = new Text(seats.get(i)+" seats remaining");
            Label arrival_time = new Label("Arrival Time: "+arrivaltime.get(i));
            Label alight_time = new Label("Alight Time: "+alighttime.get(i));
            HBox card = new HBox(100);
            VBox title = new VBox(5);

            if((int)seats.get(i)<=10){
                System.out.println("Less than 10 changing to red");
                desc.setStyle("-fx-font-size:10px; -fx-background-color: red;");
            }else{
                System.out.println("Greater than 10 changing to green");
                desc.setStyle("-fx-font-size:10px; -fx-text-fill: green");
            }

            //Styling
            cardlbl.setStyle("-fx-font-size: 18px;");
            card.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 5px;");
            card.setPadding(new Insets(20));
            card.setEffect(ds);
            card.setCursor(Cursor.HAND);
            pick.setAlignment(Pos.CENTER_RIGHT);

            pick.setOnAction(e->{
                buslbl.setText(e.getTarget().toString().substring(e.getTarget().toString().length()-3, e.getTarget().toString().length()-1));
                res.set(1,e.getTarget().toString().substring(e.getTarget().toString().length()-3, e.getTarget().toString().length()-1));
                System.out.println(res);

            });
            //Population
            title.getChildren().addAll(cardlbl,desc);
            card.getChildren().addAll(title, arrival_time, alight_time, pick);
            cardSection.getChildren().add(card);
        }
        //Button events
        savebtn.setOnAction(e->{
            try{
                db.addReservation(conn, event, ad_no, res.getLast(), String.valueOf(LocalDate.now()));
            } catch (Exception ex) {
                throw new RuntimeException(ex.getCause());
            }
        });
        adminbtn.setOnAction(e->{
            try {
                Admin(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        profilebtn.setOnAction(e ->{
            db.getUser(conn, e, ad_no);
        } );


        //children
        savebtn.setGraphic(saveI);
        resDescription.getChildren().addAll(restext, locationlbl, buslbl, savebtn);
        homebtn.setGraphic(houseI);
        profilebtn.setGraphic(userI);
        navbar.getChildren().addAll(adminbtn, profilebtn, homebtn);
        selectorMenu.getChildren().add(locationselector);
        stackp.getChildren().addAll(img, selectorMenu);
        StackPane.setAlignment(selectorMenu, Pos.BOTTOM_CENTER);
        container.getChildren().add(stackp);
        container.getChildren().add(cardSection);
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

    public void switchHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        stage.setTitle("Home");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}