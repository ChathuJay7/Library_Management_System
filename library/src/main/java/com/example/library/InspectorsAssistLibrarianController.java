package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InspectorsAssistLibrarianController implements Initializable {

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<AssistLibrarian, String> colEmail;

    @FXML
    private TableColumn<AssistLibrarian, Integer> colID;

    @FXML
    private TableColumn<AssistLibrarian, String> colName;

    @FXML
    private TableColumn<AssistLibrarian, String> colNic;

    @FXML
    private Label inspectorAssistLibrarianEmail;

    @FXML
    private TableView<AssistLibrarian> tableAssistLibrarian;

    public String user;

    public void data(String data) {
        user = data;
        inspectorAssistLibrarianEmail.setText(user);
    }

    ObservableList<AssistLibrarian> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        try{
            DatabaseConnector connectNow = new DatabaseConnector();
            Connection connectDB = connectNow.getConnection();

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM assistlibrarian");


            while (rs.next()){
                obList.add(new AssistLibrarian("AL - " + rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic"), rs.getString("userRole")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(AdminsAdminController.class.getName()).log(Level.SEVERE, null, e);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("AssistLibrarianID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("AssistLibrarianName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("AssistLibrarianEmail"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("AssistLibrarianNic"));

        tableAssistLibrarian.setItems(obList);

    }


    @FXML
    void HomeClick(ActionEvent event) {
        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspector_view.fxml"));
            Parent root = loader.load();
            InspectorViewController inspectorViewController = loader.getController();
            inspectorViewController.data(inspectorAssistLibrarianEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Inspector Dashboard");
            btnHome.getScene().getWindow().hide();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void LogoutClick(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("landing_page_view.fxml"));
        Stage window = (Stage) btnLogout.getScene().getWindow();
        window.setTitle("Library Management System");
        window.setScene(new Scene(root));
    }

}
