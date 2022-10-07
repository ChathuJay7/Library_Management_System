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

public class InspectorsLibrarianController implements Initializable {

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Librarian, String> colEmail;

    @FXML
    private TableColumn<Librarian, Integer> colID;

    @FXML
    private TableColumn<Librarian, String> colName;

    @FXML
    private TableColumn<Librarian, String> colNic;

    @FXML
    private Label inspectorLibrarianEmail;

    @FXML
    private TableView<Librarian> tableLibrarian;


    public String user;

    public void data(String data) {
        user = data;
        inspectorLibrarianEmail.setText(user);
    }


    ObservableList<Librarian> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        try{
            DatabaseConnector connectNow = new DatabaseConnector();
            Connection connectDB = connectNow.getConnection();

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM librarian");


            while (rs.next()){
                obList.add(new Librarian("L - " + rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(AdminsAdminController.class.getName()).log(Level.SEVERE, null, e);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("LibrarianID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("LibrarianName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("LibrarianEmail"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("LibrarianNic"));
        tableLibrarian.setItems(obList);

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
            inspectorViewController.data(inspectorLibrarianEmail.getText());

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
