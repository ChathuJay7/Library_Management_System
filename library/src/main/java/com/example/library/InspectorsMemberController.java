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

public class InspectorsMemberController implements Initializable {

    @FXML
    private Button HomeBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Members, Integer> colBookID;

    @FXML
    private TableColumn<Members, String> colEmail;

    @FXML
    private TableColumn<Members, Integer> colID;

    @FXML
    private TableColumn<Members, String> colName;

    @FXML
    private TableColumn<Members, String> colNic;

    @FXML
    private Label inspectorMemberEmail;

    @FXML
    private TableView<Members> memberTable;

    @FXML
    private TextField txtSearch;


    public String user;

    public void data(String data) {
        user = data;
        inspectorMemberEmail.setText(user);
    }


    ObservableList<Members> obList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        try{
            DatabaseConnector connectNow = new DatabaseConnector();
            Connection connectDB = connectNow.getConnection();

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM users");


            while (rs.next()){
                obList.add(new Members("M - " + rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic"), "B - " + rs.getString("bookId"), rs.getString("userRole")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(AdminsAdminController.class.getName()).log(Level.SEVERE, null, e);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("MemberID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("MemberName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("MemberEmail"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("MemberNic"));
        colBookID.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        memberTable.setItems(obList);


        // Search Function
        FilteredList<Members> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Members -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Members.getMemberName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if (Members.getMemberEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Members> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(memberTable.comparatorProperty());

        memberTable.setItems(sortedList);

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
            inspectorViewController.data(inspectorMemberEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Inspector Dashboard");
            HomeBtn.getScene().getWindow().hide();
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
