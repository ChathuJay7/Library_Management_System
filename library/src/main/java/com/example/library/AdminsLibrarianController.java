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

public class AdminsLibrarianController implements Initializable {

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
    private TableView<Librarian> tableLibrarian;

    @FXML
    private Label adminLibrarianEmail;

    public String user;

    public void data(String data) {
        user = data;
        adminLibrarianEmail.setText(user);
    }



    ObservableList<Librarian> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        try{
            DatabaseConnector connectNow = new DatabaseConnector();
            Connection connectDB = connectNow.getConnection();

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM librarian");


            while (rs.next()){
                obList.add(new Librarian("L - " + rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic")));
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
    void HomeClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("admin_view.fxml"));
        Stage window = (Stage) btnHome.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_view.fxml"));
            Parent root = loader.load();
            AdminViewController adminViewController = loader.getController();
            adminViewController.data(adminLibrarianEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
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
