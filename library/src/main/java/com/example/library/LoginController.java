package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    public CurrentUser currentUser;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label msgLabel;

    @FXML
    private Button btnReg;

    @FXML
    private Button btnForget;

    Encryptor encryptor = new Encryptor();



    @FXML
    void cancelClicked(ActionEvent event) {
        toLandingPage();
    }


    public void toLandingPage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("landing_page_view.fxml"));
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setTitle("Library Management System");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }


    @FXML
    void loginClicked(ActionEvent event) throws IOException {

        if(username.getText().isBlank() == false && password.getText().isBlank() == false){
            //String userName = username.getText();
            //String userPassword = password.getText();

            userLogin();
            adminLogin();
            librarianLogin();
            assistLibrarianLogin();
            inspectorLogin();

            //Stage stage = (Stage) cancelBtn.getScene().getWindow();
            //stage.close();

        }
        else {
            msgLabel.setText("Please enter username and password");
        }
    }


    public void userLogin() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{
            String userRole1 = "Member";
            String Password, HashedPassword;

            Password = password.getText();
            HashedPassword = encryptor.encryptString(Password);

            //String query = "SELECT count(1) FROM users WHERE email = '" + username.getText() + "' AND password = '" + password.getText() + "' AND userRole = '" + userRole1 + "'";
            String query = "SELECT count(1) FROM users WHERE email = '" + username.getText() + "' AND password = '" + HashedPassword + "' AND userRole = '" + userRole1 + "'";


            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    // msgLabel.setText("Congratulations");

                    /*
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("member_view.fxml"));
                    Parent root = loader.load();

                    CurrentUser currentUser = new CurrentUser(username.getText());
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
                    memberViewController.data(username.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Member Dashboard");
                    loginBtn.getScene().getWindow().hide();
                    stage.show();
                }

                else {
                  msgLabel.setText("Login Failed");
                 }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }


    public void adminLogin() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{
            String Password, HashedPassword;

            String userRole2 = "Admin";
            Password = password.getText();
            HashedPassword = encryptor.encryptString(Password);

            String query = "SELECT count(1) FROM admin WHERE email = '" + username.getText() + "' AND password = '" + HashedPassword + "' AND userRole = '" + userRole2 + "'";

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    // msgLabel.setText("Congratulations");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_view.fxml"));
                    Parent root = loader.load();
                    AdminViewController adminViewController = loader.getController();
                    adminViewController.data(username.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Admin Dashboard");
                    loginBtn.getScene().getWindow().hide();
                    stage.show();


                }

                else {
                    msgLabel.setText("Login Failed");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }



    public void librarianLogin() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{
            String userRole3 = "Librarian";

            String query = "SELECT count(1) FROM librarian WHERE email = '" + username.getText() + "' AND password = '" + password.getText() + "' AND userRole = '" + userRole3 + "'";

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    // msgLabel.setText("Congratulations");

                    /*
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
                    Parent root = loader.load();

                    CurrentUser currentUser = new CurrentUser(username.getText());
                    LibrarianViewController librarianViewController = loader.getController();
                    librarianViewController.initData(currentUser);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Librarian Dashboard");
                    stage.show();

                    */

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
                    Parent root = loader.load();
                    LibrarianViewController librarianViewController = loader.getController();
                    librarianViewController.data(username.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Librarian Dashboard");
                    loginBtn.getScene().getWindow().hide();
                    stage.show();

                }

                else {
                     msgLabel.setText("Login Failed");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }


    public void assistLibrarianLogin() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{

            String Password, HashedPassword;

            String userRole4 = "Assistant Librarian";
            Password = password.getText();
            HashedPassword = encryptor.encryptString(Password);

            String query = "SELECT count(1) FROM assistLibrarian WHERE email = '" + username.getText() + "' AND password = '" + HashedPassword + "' AND userRole = '" + userRole4 + "'";

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    // msgLabel.setText("Congratulations");

                    /*
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
                    Parent root = loader.load();

                    CurrentUser currentUser = new CurrentUser(username.getText());
                    LibrarianViewController librarianViewController = loader.getController();
                    librarianViewController.initData(currentUser);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Librarian Dashboard");
                    stage.show();

                    */

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarian_view.fxml"));
                    Parent root = loader.load();
                    AssistLibrarianViewController assistLibrariansViewController = loader.getController();
                    assistLibrariansViewController.data(username.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Librarian Dashboard");
                    loginBtn.getScene().getWindow().hide();
                    stage.show();

                }

                else {
                    msgLabel.setText("Login Failed");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }



    public void inspectorLogin() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{
            String Password, HashedPassword;

            String userRole5 = "Inspector";
            Password = password.getText();
            HashedPassword = encryptor.encryptString(Password);

            String query = "SELECT count(1) FROM inspector WHERE email = '" + username.getText() + "' AND password = '" + HashedPassword + "' AND userRole = '" + userRole5 + "'";

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    // msgLabel.setText("Congratulations");

                    /*
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
                    Parent root = loader.load();

                    CurrentUser currentUser = new CurrentUser(username.getText());
                    LibrarianViewController librarianViewController = loader.getController();
                    librarianViewController.initData(currentUser);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Librarian Dashboard");
                    stage.show();

                    */

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("inspector_view.fxml"));
                    Parent root = loader.load();
                    InspectorViewController inspectorViewController = loader.getController();
                    inspectorViewController.data(username.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Inspector Dashboard");
                    loginBtn.getScene().getWindow().hide();
                    stage.show();

                }

                else {
                    msgLabel.setText("Login Failed");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }



    @FXML
    void forgetClicked(ActionEvent event) {
        toForgetPassword();
    }

    public void toForgetPassword(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("forget_password_view.fxml"));
            Stage stage = (Stage) btnForget.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setTitle("Forget Password");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

}
