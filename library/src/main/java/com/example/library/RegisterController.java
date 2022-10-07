package com.example.library;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label txtMessage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtContact;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Label lblPasswordConfirm;

    @FXML
    private TextField txtSecurity1;

    @FXML
    private TextField txtSecurity2;

    @FXML
    private TextField txtNic;

    Encryptor encryptor = new Encryptor();

    @FXML
    void cancelClicked(ActionEvent event) {
        toLandingPage();
    }


    public void toLandingPage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("landing_page_view.fxml"));
            Stage stage = (Stage) btnCancel.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setTitle("Library Management System");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }



    @FXML
    void registerClicked(ActionEvent event) {


        if(txtName.getText().isBlank() == false && txtEmail.getText().isBlank() == false && txtNic.getText().isBlank() == false && txtPassword.getText().isBlank() == false && txtConfirmPassword.getText().isBlank()  == false && txtSecurity1.getText().isBlank() == false && txtSecurity1.getText().isBlank() == false && txtSecurity2.getText().isBlank() == false){

            if(txtPassword.getText().equals(txtConfirmPassword.getText())){

                // Pattern p = Pattern.compile("\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
                // Pattern p  = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
                Pattern p  = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

                Matcher m = p.matcher(txtEmail.getText());

                if(m.find()){
                    checkForSameEmail();
                }

                else{
                    txtMessage.setText("Invalid Email Type");
                }

                //registerUser();
                //checkForSameEmail();

            }
            else {
                lblPasswordConfirm.setText("Password doesn't match");
            }


        }
        else {
            txtMessage.setText("Please fill all the details!");
        }


    }

    public void registerUser() throws NoSuchAlgorithmException {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtName.getText();
        String Email = txtEmail.getText();
        String Nic = txtNic.getText();
        String Contact = txtContact.getText();
        String Password = txtPassword.getText();
        String hashedPassword = encryptor.encryptString(Password);
        String SecurityQuestion1 = txtSecurity1.getText();
        String SecurityQuestion2 = txtSecurity2.getText();
        String UserRole = "Member";
        String BookID = "";

        String insertFields = "INSERT INTO users(name, nic, email, contact, password, userRole, bookId, securityQuestion1, securityQuestion2) VALUES ('";
        String insertValues = Name + "','" + Nic + "','" + Email + "','" + Contact + "','" + hashedPassword + "','" + UserRole + "','" + BookID + "','" + SecurityQuestion1 + "','" + SecurityQuestion2 + "')";
        String insertToRegister = insertFields + insertValues;

        try{

                Statement statement = connectDB.createStatement();
                statement.executeUpdate(insertToRegister);

                txtMessage.setText("User registered Successfully!");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User registration");

                alert.setHeaderText("User Registered");
                alert.setContentText("Registered successfully!");

                alert.showAndWait();

                txtName.clear();
                txtNic.clear();
                txtEmail.clear();
                txtPassword.clear();
                txtConfirmPassword.clear();
                txtSecurity1.clear();
                txtSecurity2.clear();

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
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
                registerUser();
            }


        }
        catch (SQLException | NoSuchAlgorithmException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}