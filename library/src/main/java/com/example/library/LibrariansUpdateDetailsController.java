package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrariansUpdateDetailsController {

    public String user;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnLogout;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserRole;

    @FXML
    private Label librarianUpdateDetailsEmail;

    @FXML
    private Label txtMessage;

    @FXML
    private Label lblPasswordConfirm;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField txtSecurityQuestion1;

    @FXML
    private TextField txtSecurityQuestion2;

    public void data(String data) {
        user = data;
        librarianUpdateDetailsEmail.setText(user);


        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Librarian> obList = FXCollections.observableArrayList();

        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM librarian WHERE email = '" + user + "'");

            while (rs.next()) {

                // attempt to put it in a textfield
                txtId.setText("L - " + rs.getString("id"));
                txtName.setText(rs.getString("name"));
                txtEmail.setText(rs.getString("email"));
                txtUserRole.setText(rs.getString("userRole"));
                txtPassword.setText(rs.getString("password"));
                txtConfirmPassword.setText(rs.getString("password"));
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
    void UpdateClick(ActionEvent event) {
        if(txtId.getText().isBlank() == false &&txtName.getText().isBlank() == false && txtEmail.getText().isBlank() == false && txtUserRole.getText().isBlank() == false && txtPassword.getText().isBlank() == false && txtConfirmPassword.getText().isBlank() == false && txtSecurityQuestion1.getText().isBlank() == false && txtSecurityQuestion2.getText().isBlank() == false){
            //String userName = username.getText();
            //String userPassword = password.getText();
            if(txtPassword.getText().equals(txtConfirmPassword.getText())){
                UpdateDetails();

                txtMessage.setText("Updated Successfully!");
                lblPasswordConfirm.setText("You are set!");
            }
            else {
                txtMessage.setText("Update Failed!");
                lblPasswordConfirm.setText("Password doesn't match");
            }


        }
        else {
            txtMessage.setText("Please Fill all the text fields");
        }
    }

    public void UpdateDetails(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String librarianid, librarianname, librarianemail, librarianuserrole, llibrarianpassword, librarianconfirmpassword, llibrariansecurityquestion1, llibrariansecurityquestion2;

        //myIndex = memberTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));

        librarianid = txtId.getText().substring(4);
        librarianname = txtName.getText();
        librarianemail = txtEmail.getText();
        librarianuserrole = txtUserRole.getText();
        llibrarianpassword = txtPassword.getText();
        librarianconfirmpassword = txtConfirmPassword.getText();
        llibrariansecurityquestion1 = txtSecurityQuestion1.getText();
        llibrariansecurityquestion2 = txtSecurityQuestion2.getText();

        //  String query = "UPDATE users SET id = '" + memberid + "' ,name = '" + membername + "' ,email = '" + memberemail + "' ,userRole = '" + memberuserrole +  "' where id = '" + memberid + "'";
        String query = "UPDATE librarian SET id = '" + librarianid + "' ,name = '" + librarianname + "' ,email = '" + librarianemail + "' ,userRole = '" + librarianuserrole + "' ,password = '" + llibrarianpassword + "' ,securityQuestion1 = '" + llibrariansecurityquestion1 + "' ,securityQuestion2= '" + llibrariansecurityquestion2  +  "' where id = '" + librarianid + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);


            alert.setTitle("Personal Details Update");
            alert.setHeaderText("Librarian Updated");
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
    void HomeClick(ActionEvent event) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("librarian_view.fxml"));
        //Stage window = (Stage) btnHome.getScene().getWindow();
        //window.setScene(new Scene(root));
        Home();
    }

    public void Home(){
        try{
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
            Parent root = loader.load();

            CurrentUser currentUser = new CurrentUser(librarianUpdateDetailsEmail.getText());
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
            librarianViewController.data(librarianUpdateDetailsEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Librarian Dashboard");
            btnHome.getScene().getWindow().hide();
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
