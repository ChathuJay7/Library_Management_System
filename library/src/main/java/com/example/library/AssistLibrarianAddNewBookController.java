package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;

public class AssistLibrarianAddNewBookController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnBooks;

    @FXML
    private Button btnLogout;

    @FXML
    private Label assistLibrarianAddNewBookEmail;

    @FXML
    private Label txtMessage;

    @FXML
    private TextField txtName;


    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        assistLibrarianAddNewBookEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        assistLibrarianAddNewBookEmail.setText(user);
    }

    @FXML
    void AddClick(ActionEvent event) {
        if(txtName.getText().isBlank() == false){
            addNewBook();
        }
        else {
            txtMessage.setText("Please Enter Book Name!");
        }
    }

    public void addNewBook(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtName.getText();
        String IsAvailable = "Available";
        String MemberId = "0";

        String insertFields = "INSERT INTO books(bookName, isAvailable, memberID) VALUES ('";
        String insertValues = Name + "','" + IsAvailable + "','" + MemberId + "')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            txtMessage.setText("Book registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book registration");

            alert.setHeaderText("Book Registered");
            alert.setContentText("Registered successfully!");

            alert.showAndWait();

            txtName.clear();

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void BooksClick(ActionEvent event) {
        BookDetails();
    }

    public void BookDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_book_view.fxml"));
            Parent root = loader.load();
            AssistLibrariansBookController assistLibrariansBookController = loader.getController();
            assistLibrariansBookController.data(assistLibrarianAddNewBookEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Books Details");
            btnBooks.getScene().getWindow().hide();
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
