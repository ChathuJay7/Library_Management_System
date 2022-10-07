package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LibrariansAddNewAssistLibrarianController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAssistLibrarian;

    @FXML
    private Button btnLogout;

    @FXML
    private Label librarianAddNewAssistLibrarianEmail;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label txtMessage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;


    Encryptor encryptor = new Encryptor();

    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        librarianAddNewAssistLibrarianEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        librarianAddNewAssistLibrarianEmail.setText(user);
    }

    @FXML
    void AddAssistLibrarianClick(ActionEvent event) {

        if(txtName.getText().isBlank() == false && txtEmail.getText().isBlank() == false && txtNic.getText().isBlank() == false){
            Pattern p  = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

            Matcher m = p.matcher(txtEmail.getText());

            if(m.find()){
                checkForSameEmail();
            }

            else{
                txtMessage.setText("Invalid Email Type");
            }
        }
        else {
            txtMessage.setText("Please enter email and password");
        }

        // checkForSameEmail();
        // registerAssistLibrarian();

    }

    public void checkForSameEmail(){
        String userEmail = txtEmail.getText();

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Members> obList = FXCollections.observableArrayList();

        try{

            ResultSet rs1 = connectDB.createStatement().executeQuery("SELECT * FROM users WHERE email = '" + userEmail + "'");
            ResultSet rs2 = connectDB.createStatement().executeQuery("SELECT * FROM admin WHERE email = '" + userEmail + "'");
            ResultSet rs3 = connectDB.createStatement().executeQuery("SELECT * FROM librarian WHERE email = '" + userEmail + "'");
            ResultSet rs4 = connectDB.createStatement().executeQuery("SELECT * FROM assistlibrarian WHERE email = '" + userEmail + "'");
            ResultSet rs5 = connectDB.createStatement().executeQuery("SELECT * FROM inspector WHERE email = '" + userEmail + "'");
            //ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM users");

            if(rs1.isBeforeFirst()){
                txtMessage.setText("Email already registered");
            }
            else if(rs2.isBeforeFirst()){
                txtMessage.setText("Email already registered");
            }
            else if(rs3.isBeforeFirst()){
                txtMessage.setText("Email already registered");
            }
            else if(rs4.isBeforeFirst()){
                txtMessage.setText("Email already registered");
            }
            else if(rs5.isBeforeFirst()){
                txtMessage.setText("Email already registered");
            }
            else{
                registerAssistLibrarian();
            }


        }
        catch (SQLException | NoSuchAlgorithmException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void registerAssistLibrarian() throws NoSuchAlgorithmException {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtName.getText();
        String Email = txtEmail.getText();
        String Nic = txtNic.getText();
        String Password = "abcd1234";
        String HashedPassword = encryptor.encryptString(Password);
        String UserRole = "Assistant Librarian";
        String SecurityQuestion1 = "Security1";
        String SecurityQuestion2 = "Security2";

        String insertFields = "INSERT INTO assistlibrarian(name, nic, email, password, userRole, securityQuestion1, securityQuestion2) VALUES ('";
        String insertValues = Name + "','" + Nic + "','" + Email + "','" + HashedPassword + "','" + UserRole + "','" + SecurityQuestion1 + "','" + SecurityQuestion2 + "')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            txtMessage.setText("Assist Librarian registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Assist Librarian registration");

            alert.setHeaderText("Assist Librarian Registered");
            alert.setContentText("Registered successfully!");

            alert.showAndWait();

            txtName.clear();
            txtEmail.clear();
            txtNic.clear();

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void AssistLibrarianClick(ActionEvent event) {
        AssistLibrarianDetails();
    }

    public void AssistLibrarianDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_assist_librarian_view.fxml"));
            Parent root = loader.load();
            LibrariansAssistLibrariansController librariansAssistLibrariansController = loader.getController();
            librariansAssistLibrariansController.data(librarianAddNewAssistLibrarianEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Assist Librarian Details");
            btnAssistLibrarian.getScene().getWindow().hide();
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
