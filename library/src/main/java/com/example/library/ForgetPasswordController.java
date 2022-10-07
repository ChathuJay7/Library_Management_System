package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ForgetPasswordController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSubmit;

    @FXML
    private Label txtMessage;


    @FXML
    private TextField txtSecurityQuestion1;

    @FXML
    private TextField txtSecurityQuestion2;

    @FXML
    private TextField txtEmail;


    @FXML
    void loginClick(ActionEvent event) {
        toLoginForm();
    }

    public void toLoginForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setTitle("Login Dashboard");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @FXML
    void submitClick(ActionEvent event) throws IOException{

        if(txtEmail.getText().isBlank() == false && txtSecurityQuestion1.getText().isBlank() == false && txtSecurityQuestion2.getText().isBlank() == false){

            memberForgetPassword();
            adminForgetPassword();
            librarianForgetPassword();
            assistLibrarianForgetPassword();
            inspectorForgetPassword();
        }
        else{
            txtMessage.setText("Please enter your email and answer for the security questions");
        }

    }

    public void memberForgetPassword() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{
            String userRole1 = "Member";

            String query = "SELECT count(1) FROM users WHERE email = '" + txtEmail.getText() + "' AND securityQuestion1 = '" + txtSecurityQuestion1.getText() + "' AND securityQuestion2 = '" + txtSecurityQuestion2.getText() + "' AND userRole = '" + userRole1 + "'";


            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    /*
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("members_change_password_view.fxml"));
                    Parent root = loader.load();

                    CurrentUser currentUser = new CurrentUser(txtEmail.getText());
                    ChangePasswordController changePasswordController = loader.getController();
                    changePasswordController.initData(currentUser);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    stage.show();

                    */


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("members_change_password_view.fxml"));
                    Parent root = loader.load();
                    MembersChangePasswordController changePasswordController = loader.getController();
                    changePasswordController.data(txtEmail.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    btnSubmit.getScene().getWindow().hide();
                    stage.show();

                }

                else {
                    txtMessage.setText("Invalid Details!");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }



    public void adminForgetPassword() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{
            String userRole2 = "Admin";

            String query = "SELECT count(1) FROM admin WHERE email = '" + txtEmail.getText() + "' AND securityQuestion1 = '" + txtSecurityQuestion1.getText() + "' AND securityQuestion2 = '" + txtSecurityQuestion2.getText() + "' AND userRole = '" + userRole2 + "'";

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    /*
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("members_change_password_view.fxml"));
                    Parent root = loader.load();

                    CurrentUser currentUser = new CurrentUser(txtEmail.getText());
                    ChangePasswordController changePasswordController = loader.getController();
                    changePasswordController.initData(currentUser);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    stage.show();

                    */


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("admins_change_password_view.fxml"));
                    Parent root = loader.load();
                    AdminsChangePasswordController adminsChangePasswordController = loader.getController();
                    adminsChangePasswordController.data(txtEmail.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    btnSubmit.getScene().getWindow().hide();
                    stage.show();


                }

                else {
                    txtMessage.setText("Invalid Details!");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }

    public void librarianForgetPassword() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{
            String userRole3 = "Librarian";

            String query = "SELECT count(1) FROM librarian WHERE email = '" + txtEmail.getText() + "' AND securityQuestion1 = '" + txtSecurityQuestion1.getText() + "' AND securityQuestion2 = '" + txtSecurityQuestion2.getText() + "' AND userRole = '" + userRole3 + "'";

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    /*
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("members_change_password_view.fxml"));
                    Parent root = loader.load();

                    CurrentUser currentUser = new CurrentUser(txtEmail.getText());
                    ChangePasswordController changePasswordController = loader.getController();
                    changePasswordController.initData(currentUser);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    stage.show();

                    */


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_change_password_view.fxml"));
                    Parent root = loader.load();
                    LibrariansChangePasswordController librariansChangePasswordController = loader.getController();
                    librariansChangePasswordController.data(txtEmail.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    btnSubmit.getScene().getWindow().hide();
                    stage.show();


                }

                else {
                    txtMessage.setText("Invalid Details!");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }



    public void assistLibrarianForgetPassword() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{
            String userRole4 = "Assistant Librarian";

            String query = "SELECT count(1) FROM assistlibrarian WHERE email = '" + txtEmail.getText() + "' AND securityQuestion1 = '" + txtSecurityQuestion1.getText() + "' AND securityQuestion2 = '" + txtSecurityQuestion2.getText() + "' AND userRole = '" + userRole4 + "'";

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    /*
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("members_change_password_view.fxml"));
                    Parent root = loader.load();

                    CurrentUser currentUser = new CurrentUser(txtEmail.getText());
                    ChangePasswordController changePasswordController = loader.getController();
                    changePasswordController.initData(currentUser);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    stage.show();

                    */


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_change_password_view.fxml"));
                    Parent root = loader.load();
                    AssistLibrariansChangePasswordController assistLibrariansChangePasswordController = loader.getController();
                    assistLibrariansChangePasswordController.data(txtEmail.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    btnSubmit.getScene().getWindow().hide();
                    stage.show();


                }

                else {
                    txtMessage.setText("Invalid Details!");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }


    public void inspectorForgetPassword() {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        try{
            String userRole5 = "Inspector";

            String query = "SELECT count(1) FROM inspector WHERE email = '" + txtEmail.getText() + "' AND securityQuestion1 = '" + txtSecurityQuestion1.getText() + "' AND securityQuestion2 = '" + txtSecurityQuestion2.getText() + "' AND userRole = '" + userRole5 + "'";

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(query);


            while(queryResult.next()){

                if(queryResult.getInt(1) == 1){
                    /*
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("members_change_password_view.fxml"));
                    Parent root = loader.load();

                    CurrentUser currentUser = new CurrentUser(txtEmail.getText());
                    ChangePasswordController changePasswordController = loader.getController();
                    changePasswordController.initData(currentUser);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    stage.show();

                    */


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectors_change_password_view.fxml"));
                    Parent root = loader.load();
                    InspectorsChangePasswordController inspectorsChangePasswordController = loader.getController();
                    inspectorsChangePasswordController.data(txtEmail.getText());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Change Password");
                    btnSubmit.getScene().getWindow().hide();
                    stage.show();


                }

                else {
                    txtMessage.setText("Invalid Details!");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }


    }

}
