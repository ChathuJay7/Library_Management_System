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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssistLibrariansAdminController implements Initializable {

    @FXML
    private TableView<Admins> adminTable;

    @FXML
    private Label assistLibrarianAdminEmail;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Admins, String> colEmail;

    @FXML
    private TableColumn<Admins, Integer> colID;

    @FXML
    private TableColumn<Admins, String> colName;

    @FXML
    private TableColumn<Admins, String> colNic;

    @FXML
    private TextField txtSearch;


    public String user;

    public void data(String data) {
        user = data;
        assistLibrarianAdminEmail.setText(user);
    }

    ObservableList<Admins> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        try{
            DatabaseConnector connectNow = new DatabaseConnector();
            Connection connectDB = connectNow.getConnection();

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM admin");


            while (rs.next()){
                obList.add(new Admins("A - " + rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic"), rs.getString("userRole")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(AdminsAdminController.class.getName()).log(Level.SEVERE, null, e);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("AdminID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("AdminName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("AdminEmail"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("AdminNic"));
        adminTable.setItems(obList);


        // Search Function
        FilteredList<Admins> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Admins -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Admins.getAdminName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(Admins.getAdminEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Admins> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(adminTable.comparatorProperty());

        adminTable.setItems(sortedList);

    }



    @FXML
    void HomeClick(ActionEvent event) {
        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarian_view.fxml"));
            Parent root = loader.load();
            AssistLibrarianViewController assistLibrariansViewController = loader.getController();
            assistLibrariansViewController.data(assistLibrarianAdminEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Assistant Librarian Dashboard");
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
