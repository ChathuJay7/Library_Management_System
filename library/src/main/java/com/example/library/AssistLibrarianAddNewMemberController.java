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

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssistLibrarianAddNewMemberController {

    @FXML
    private Label assistLlibrarianAddNewMemberEmail;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnMember;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblPasswordConfirm;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label txtMessage;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtContact;

    @FXML
    private PasswordField txtPassword;

    Encryptor encryptor = new Encryptor();

    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        assistLlibrarianAddNewMemberEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        assistLlibrarianAddNewMemberEmail.setText(user);
    }

    @FXML
    void AddMemberClick(ActionEvent event) {
        /*
        if(txtPassword.getText().equals(txtConfirmPassword.getText())){
            registerMember();

            lblPasswordConfirm.setText("");
        }
        else {
            lblPasswordConfirm.setText("Password doesn't match!");
        }
        */

        // registerMember();

        if(txtName.getText().isBlank() == false && txtEmail.getText().isBlank() == false && txtNic.getText().isBlank() == false && txtContact.getText().isBlank() == false){
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
                registerMember();
            }


        }
        catch (SQLException | NoSuchAlgorithmException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void registerMember() throws NoSuchAlgorithmException {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtName.getText();
        String Email = txtEmail.getText();
        String Nic = txtNic.getText();
        String Contact = txtContact.getText();
        String Password = "abcd1234";
        String HashedPassword = encryptor.encryptString(Password);
        String UserRole = "Member";
        String bookid = "";
        String securityquestion1 = "Security1";
        String securityquestion2 = "Security2";

        //String insertFields = "INSERT INTO users(name, email, password, userRole) VALUES ('";
        //String insertValues = Name + "','" + Email + "','" + Password + "','" + UserRole + "')";
        //String insertToRegister = insertFields + insertValues;

        String insertFields = "INSERT INTO users(name, nic, email, contact, password, userRole, bookId, securityQuestion1, securityQuestion2) VALUES ('";
        String insertValues = Name + "','" + Nic + "','" + Email + "','" + Contact + "','" + HashedPassword + "','" + UserRole + "','" + bookid + "','" + securityquestion1 + "','" + securityquestion2 + "')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            txtMessage.setText("Member registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Member registration");

            alert.setHeaderText("Member Registered");
            alert.setContentText("Registered successfully!");

            alert.showAndWait();

            txtName.clear();
            txtEmail.clear();
            txtNic.clear();
            txtContact.clear();

        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void MemberClick(ActionEvent event) {
        MemberDetails();
    }

    public void MemberDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_member_view.fxml"));
            Parent root = loader.load();
            AssistLibrariansMemberController assistLibrariansMemberController = loader.getController();
            assistLibrariansMemberController.data(assistLlibrarianAddNewMemberEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Members Details");
            btnMember.getScene().getWindow().hide();
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
