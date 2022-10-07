package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MembersEventController implements Initializable {

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Events, String> eventDateColumn;

    @FXML
    private TableColumn<Events, Integer> eventIDColumn;

    @FXML
    private TableColumn<Events, String> eventNameColumn;

    @FXML
    private TableView<Events> eventTable;

    @FXML
    private TableColumn<Events, String> eventTimeColumn;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label memberEventEmail;

    ObservableList<Events> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        try{
            DatabaseConnector connectNow = new DatabaseConnector();
            Connection connectDB = connectNow.getConnection();

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM events");


            while (rs.next()){
                obList.add(new Events("E - " + rs.getString("id"), rs.getString("name"), rs.getDate("date"), rs.getString("time")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(AdminsAdminController.class.getName()).log(Level.SEVERE, null, e);
        }

        eventIDColumn.setCellValueFactory(new PropertyValueFactory<>("EventID"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("EventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("EventDate"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("EventTime"));
        eventTable.setItems(obList);


        // Search Function
        FilteredList<Events> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Events -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Events.getEventName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Events> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(eventTable.comparatorProperty());

        eventTable.setItems(sortedList);

    }


    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        memberEventEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        memberEventEmail.setText(user);
    }

    @FXML
    void HomeClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("member_view.fxml"));
        Stage window = (Stage) btnHome.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        Home();

    }

    public void Home(){
        try{
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("member_view.fxml"));
            Parent root = loader.load();

            CurrentUser currentUser = new CurrentUser(memberEventEmail.getText());
            MemberViewController memberViewController = loader.getController();
            memberViewController.initData(currentUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Member Dashboard");
            stage.show();
            */

            FXMLLoader loader = new FXMLLoader(getClass().getResource("member_view.fxml"));
            Parent root = loader.load();
            MemberViewController memberViewController = loader.getController();
            memberViewController.data(memberEventEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Member Dashboard");
            btnHome.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void LogoutClick(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("landing_page_view.fxml"));
        Stage window = (Stage) btnLogout.getScene().getWindow();
        window.setTitle("Library Management System");
        window.setScene(new Scene(root));
    }


}
