package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssistLibrariansChangePasswordInsideProfileController {

    @FXML
    private Button btnChangePassword;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnLogout;

    @FXML
    private Label changePasswordEmail;

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
    void UpdateClick(ActionEvent event) {
        UpdateProfilePage();
    }

    public void UpdateProfilePage(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_update_view.fxml"));
            Parent root = loader.load();
            AssistLibrariansUpdateController assistLibrariansUpdateController = loader.getController();
            assistLibrariansUpdateController.data(changePasswordEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Details");
            btnUpdate.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void changePasswordClicked(ActionEvent event) throws NoSuchAlgorithmException {
        if(txtPassword.getText().isBlank() == false && txtConfirmPassword.getText().isBlank() == false){

            if(txtPassword.getText().equals(txtConfirmPassword.getText())){
                ChangePasswordAssistLibrarian();

            }
            else {
                txtMessage.setText("Password doesn't match");
            }


        }
        else {
            txtMessage.setText("Please enter username and password");
        }
    }

    public void ChangePasswordAssistLibrarian() throws NoSuchAlgorithmException {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String email, password, confirmpassword,hashedpassword;

        //myIndex = memberTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));

        email = changePasswordEmail.getText();
        password = txtPassword.getText();
        hashedpassword = encryptor.encryptString(password);
        confirmpassword = txtConfirmPassword.getText();

        //  String query = "UPDATE users SET id = '" + memberid + "' ,name = '" + membername + "' ,email = '" + memberemail + "' ,userRole = '" + memberuserrole +  "' where id = '" + memberid + "'";
        String query = "UPDATE assistlibrarian SET password = '" + hashedpassword + "' where email = '" + email + "'";

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
    void LogoutClick(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("landing_page_view.fxml"));
        Stage window = (Stage) btnLogout.getScene().getWindow();
        window.setTitle("Library Management System");
        window.setScene(new Scene(root));
    }

}

