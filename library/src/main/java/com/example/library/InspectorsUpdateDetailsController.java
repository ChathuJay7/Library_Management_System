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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InspectorsUpdateDetailsController {

    @FXML
    private Button btnChangePassword;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnLogout;

    @FXML
    private Label inspectorUpdateDetailsEmail;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private Label txtMessage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSecurityQuestion1;

    @FXML
    private TextField txtSecurityQuestion2;

    @FXML
    private TextField txtUserRole;


    public String user;


    public void data(String data) {
        user = data;
        inspectorUpdateDetailsEmail.setText(user);


        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Admins> obList = FXCollections.observableArrayList();

        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM inspector WHERE email = '" + user + "'");

            while (rs.next()) {

                // attempt to put it in a textfield
                txtId.setText("A - " + rs.getString("id"));
                txtName.setText(rs.getString("name"));
                txtEmail.setText(rs.getString("email"));
                txtUserRole.setText(rs.getString("userRole"));
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
    void ChangePasswordClick(ActionEvent event) {
        ChangePasswordPage();
    }

    public void ChangePasswordPage(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectors_change_password_inside_profile_view.fxml"));
            Parent root = loader.load();
            InspectorsChangePasswordInsideProfileController inspectorsChangePasswordInsideProfileController = loader.getController();
            inspectorsChangePasswordInsideProfileController.data(inspectorUpdateDetailsEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Inspector Change Password");
            btnChangePassword.getScene().getWindow().hide();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
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
            inspectorViewController.data(inspectorUpdateDetailsEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Dashboard");
            btnHome.getScene().getWindow().hide();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateClick(ActionEvent event) {
        if(txtId.getText().isBlank() == false &&txtName.getText().isBlank() == false && txtEmail.getText().isBlank() == false && txtUserRole.getText().isBlank() == false &&  txtSecurityQuestion1.getText().isBlank() == false && txtSecurityQuestion2.getText().isBlank() == false){

            UpdateDetails();

            txtMessage.setText("Updated Successfully!");

        }
        else {
            txtMessage.setText("Please fill all the text fields");
        }
    }


    public void UpdateDetails(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String inspectorid, inspectorname, inspectoremail, inspectoruserrole, inspectorpassword, inspectorconfirmpassword, inspectorsecurityquestion1, inspectorsecurityquestion2;

        //myIndex = memberTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));

        inspectorid = txtId.getText().substring(4);
        inspectorname = txtName.getText();
        inspectoremail = txtEmail.getText();
        inspectoruserrole = txtUserRole.getText();
        //inspectorpassword = txtPassword.getText();
        //inspectorconfirmpassword = txtConfirmPassword.getText();
        inspectorsecurityquestion1 = txtSecurityQuestion1.getText();
        inspectorsecurityquestion2 = txtSecurityQuestion2.getText();


        //  String query = "UPDATE users SET id = '" + memberid + "' ,name = '" + membername + "' ,email = '" + memberemail + "' ,userRole = '" + memberuserrole +  "' where id = '" + memberid + "'";
        String query = "UPDATE inspector SET id = '" + inspectorid + "' ,name = '" + inspectorname + "' ,email = '" + inspectoremail + "' ,userRole = '" + inspectoruserrole + "' ,securityQuestion1 = '" + inspectorsecurityquestion1  + "' ,securityQuestion2 = '" + inspectorsecurityquestion2  +  "' where id = '" + inspectorid + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);


            alert.setTitle("Personal Details Update");
            alert.setHeaderText("Inspector Updated");
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
    void LogoutClick(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("landing_page_view.fxml"));
        Stage window = (Stage) btnLogout.getScene().getWindow();
        window.setTitle("Library Management System");
        window.setScene(new Scene(root));
    }

}
