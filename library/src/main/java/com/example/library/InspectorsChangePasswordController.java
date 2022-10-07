package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InspectorsChangePasswordController {

    @FXML
    private Label changePasswordEmail;

    @FXML
    private Button btnChangePassword;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Label txtMessage;

    @FXML
    private PasswordField txtPassword;


    Encryptor encryptor = new Encryptor();

    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        changePasswordEmail.setText(user);
    }

    public void data(String name) {
        user = name;
        changePasswordEmail.setText(user);
    }

    @FXML
    void changePasswordClicked(ActionEvent event) throws NoSuchAlgorithmException {
        if(txtPassword.getText().isBlank() == false && txtConfirmPassword.getText().isBlank() == false){

            if(txtPassword.getText().equals(txtConfirmPassword.getText())){
                ChangePasswordInspector();

            }
            else {
                txtMessage.setText("Password doesn't match");
            }


        }
        else {
            txtMessage.setText("Please enter username and password");
        }
    }

    public void ChangePasswordInspector() throws NoSuchAlgorithmException {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String email, password, confirmpassword, hashedpassword;

        //myIndex = memberTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));

        email = changePasswordEmail.getText();
        password = txtPassword.getText();
        hashedpassword = encryptor.encryptString(password);
        confirmpassword = txtConfirmPassword.getText();

        //  String query = "UPDATE users SET id = '" + memberid + "' ,name = '" + membername + "' ,email = '" + memberemail + "' ,userRole = '" + memberuserrole +  "' where id = '" + memberid + "'";
        String query = "UPDATE inspector SET password = '" + hashedpassword + "' where email = '" + email + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Password Update");
            alert.setHeaderText("Password Updated");
            alert.setContentText("Updated Successfully!");

            alert.showAndWait();

            txtPassword.clear();
            txtConfirmPassword.clear();

            txtMessage.setText("Password Updated Successfully");

        }
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


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
}
