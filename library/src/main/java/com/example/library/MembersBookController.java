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

public class MembersBookController implements Initializable {

    @FXML
    private TableView<Books> bookTable;

    @FXML
    private TableColumn<Books, Integer> bookIDColumn;

    @FXML
    private TableColumn<Books, String> bookNameColumn;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Books, String> isAvailableColumn;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label memberBookEmail;



    ObservableList<Books> obList = FXCollections.observableArrayList();

    // PreparedStatement preparedStatement = null;
    // ResultSet rs = null;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        try{
            DatabaseConnector connectNow = new DatabaseConnector();
            Connection connectDB = connectNow.getConnection();

            //String query = "Select * from `books`";
            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM books");
            // preparedStatement = connectDB.prepareStatement(rs);
            // preparedStatement.executeQuery();

            while (rs.next()){
                obList.add(new Books("B - " + rs.getString( "bookID"), rs.getString("bookName"), rs.getString("isAvailable"), "M - " + rs.getString("memberID")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        // bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        isAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("isBookAvailable"));

        bookTable.setItems(obList);


        // Search Function
        FilteredList<Books> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Books -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Books.getBookName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if (Books.getIsBookAvailable().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Books> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(bookTable.comparatorProperty());

        bookTable.setItems(sortedList);

    }

    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        memberBookEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        memberBookEmail.setText(user);
    }

    @FXML
    void HomeClick(ActionEvent event) throws Exception {
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

            CurrentUser currentUser = new CurrentUser(memberBookEmail.getText());
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
            memberViewController.data(memberBookEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Events");
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