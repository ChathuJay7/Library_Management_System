package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MembersUpdateDetailsController {

    @FXML
    private Button btnHome;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblPasswordConfirm;

    @FXML
    private Label memberUpdateDetailsEmail;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private Label txtMessage;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtSecurityQuestion1;

    @FXML
    private TextField txtSecurityQuestion2;

    @FXML
    private Button btnChangePassword;


    public String user;

    public void data(String data) {
        user = data;
        memberUpdateDetailsEmail.setText(user);

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Members> obList = FXCollections.observableArrayList();

        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM users WHERE email = '" + user + "'");

            while (rs.next()) {


                // attempt to put it in a textfield
                txtId.setText("M - " + rs.getString("id"));
                txtName.setText(rs.getString("name"));
                txtEmail.setText(rs.getString("email"));
                txtContact.setText(rs.getString("contact"));
                //txtPassword.setText(rs.getString("password"));
                //txtConfirmPassword.setText(rs.getString("password"));
                txtSecurityQuestion1.setText(rs.getString("securityQuestion1"));
                txtSecurityQuestion2.setText(rs.getString("securityQuestion2"));
//                  load_groups.setText(rs.getString("item_groups"));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    void HomeClick(ActionEvent event) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("member_view.fxml"));
        //Stage window = (Stage) btnHome.getScene().getWindow();
        //window.setScene(new Scene(root));
        Home();

    }

    public void Home(){
        try{
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("member_view.fxml"));
            Parent root = loader.load();

            CurrentUser currentUser = new CurrentUser(memberUpdateDetailsEmail.getText());
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
            memberViewController.data(memberUpdateDetailsEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Member Dashboard");
            btnHome.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateClick(ActionEvent event) {
        if(txtId.getText().isBlank() == false &&txtName.getText().isBlank() == false && txtEmail.getText().isBlank() == false && txtContact.getText().isBlank() == false && txtSecurityQuestion1.getText().isBlank() == false && txtSecurityQuestion2.getText().isBlank() == false){

            UpdateDetails();

            txtMessage.setText("Updated Successfully!");



        }
        else {
            txtMessage.setText("Please fill all the text fields!");
        }
    }

    public void UpdateDetails(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String memberid, membername, memberemail, membercontact, memberpassword, memberconfirmpassword, securityquestion1, securityQuestion2;

        //myIndex = memberTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));

        memberid = txtId.getText().substring(4);
        membername = txtName.getText();
        memberemail = txtEmail.getText();
        membercontact = txtContact.getText();
        // memberpassword = txtPassword.getText();
        // memberconfirmpassword = txtConfirmPassword.getText();
        securityquestion1 = txtSecurityQuestion1.getText();
        securityQuestion2 = txtSecurityQuestion2.getText();

        //  String query = "UPDATE users SET id = '" + memberid + "' ,name = '" + membername + "' ,email = '" + memberemail + "' ,userRole = '" + memberuserrole +  "' where id = '" + memberid + "'";
        String query = "UPDATE users SET id = '" + memberid + "' ,name = '" + membername + "' ,email = '" + memberemail + "' ,contact = '" + membercontact + "' ,securityQuestion1 = '" + securityquestion1  + "' ,securityQuestion2 = '" + securityQuestion2 +  "' where id = '" + memberid + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Personal Details Update");
            alert.setHeaderText("Member Updated");
            alert.setContentText("Updated Successfully!");

            alert.showAndWait();
            // txtId.clear();
            // txtName.clear();
            // txtEmail.clear();
            // txtUserRole.clear();
            // txtPassword.clear();
            // txtConfirmPassword.clear();
        }
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    void ChangePasswordClick(ActionEvent event) {
        ChangePasswordPage();
    }

    public void ChangePasswordPage(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("members_change_password_inside_profile_view.fxml"));
            Parent root = loader.load();
            MembersChangePasswordInsideProfileController membersChangePasswordInsideProfileController = loader.getController();
            membersChangePasswordInsideProfileController.data(memberUpdateDetailsEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Member Change Password");
            btnChangePassword.getScene().getWindow().hide();
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


