package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrariansMemberController implements Initializable {

    @FXML
    private Button HomeBtn;

    @FXML
    private Button addBtn;

    @FXML
    private TextField txtMemberEmail;

    @FXML
    private TextField txtMemberID;

    @FXML
    private TextField txtMemberName;

    @FXML
    private TextField txtMemberNic;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableColumn<Members, String> colEmail;

    @FXML
    private TableColumn<Members, Integer> colID;

    @FXML
    private TableColumn<Members, String> colName;

    @FXML
    private TableColumn<Members, String> colUserRole;

    @FXML
    private TableColumn<Members, String> colBookId;

    @FXML
    private TableColumn<Members, String> colNic;

    @FXML
    private TableColumn<Members, String> colContact;

    @FXML
    private TableView<Members> memberTable;

    @FXML
    private Label librarianMemberEmail;

    @FXML
    private Button changeToAdminBtn;

    @FXML
    private Button changeToAssistLibrarianBtn;

    @FXML
    private Button btnLogout;


    Encryptor encryptor = new Encryptor();

    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        librarianMemberEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        librarianMemberEmail.setText(user);
    }


    //ObservableList<Members> obList = FXCollections.observableArrayList();


    int myIndex;
    int id;


    public void table()
    {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Members> obList = FXCollections.observableArrayList();
        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM users");

            while (rs.next()){
                obList.add(new Members("M - " + rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic"), rs.getString("contact"), "B - " + rs.getString("bookId"), rs.getString("userRole")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("MemberID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("MemberName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("MemberEmail"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("MemberNic"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("MemberContact"));
        memberTable.setItems(obList);

        memberTable.setRowFactory( tv -> {
            TableRow<Members> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  memberTable.getSelectionModel().getSelectedIndex();
                    //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));
                    //txtMemberID.setText(String.valueOf(id));
                    txtMemberID.setText(memberTable.getItems().get(myIndex).getMemberID());
                    txtMemberName.setText(memberTable.getItems().get(myIndex).getMemberName());
                    txtMemberEmail.setText(memberTable.getItems().get(myIndex).getMemberEmail());
                    txtMemberNic.setText(memberTable.getItems().get(myIndex).getMemberNic());

                }
            });
            return myRow;
        });



        // Search Function
        FilteredList<Members> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Members -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Members.getMemberName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if (Members.getMemberEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Members> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(memberTable.comparatorProperty());

        memberTable.setItems(sortedList);
    }


    @FXML
    void AddClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarians_add_new_member_view.fxml"));
        Stage window = (Stage) addBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        AddNewMember();
    }

    public void AddNewMember(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_add_new_member_view.fxml"));
            Parent root = loader.load();
            LibrariansAddNewMemberController librariansAddNewMemberController = loader.getController();
            librariansAddNewMemberController.data(librarianMemberEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Member");
            addBtn.getScene().getWindow().hide();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void ClearClick(ActionEvent event) {
        txtMemberID.clear();
        txtMemberName.clear();
        txtMemberEmail.clear();
        txtMemberNic.clear();
        table();
    }

    @FXML
    void DeleteClick(ActionEvent event) {
        if(txtMemberID.getText().isBlank() == false && txtMemberName.getText().isBlank() == false && txtMemberEmail.getText().isBlank() == false && txtMemberNic.getText().isBlank() == false){
            DeleteMember();
            table();

            txtMemberID.clear();
            txtMemberName.clear();
            txtMemberEmail.clear();
            txtMemberNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Member");

            alert.setHeaderText("Please select a member before press delete");
            alert.setContentText("Delete Failed!");

            alert.showAndWait();
        }

    }

    public void DeleteMember(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String memberid;

        myIndex = memberTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));

        memberid = txtMemberID.getText().substring(4);

        String query = "DELETE FROM users WHERE id = '" + memberid +  "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Member Delete");

            alert.setHeaderText("Member Deleted");
            alert.setContentText("Deleted successfully!");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void ChangeToAdminClick(ActionEvent event) throws NoSuchAlgorithmException {
        /*
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String memberid, membername, memberemail, memberuserrole;

        myIndex = memberTable.getSelectionModel().getSelectedIndex();
        id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));

        memberid = txtMemberID.getText();
        membername = txtMemberName.getText();
        memberemail = txtMemberEmail.getText();
        memberuserrole = txtMemberUserRole.getText();

        //  String query = "UPDATE users SET id = '" + memberid + "' ,name = '" + membername + "' ,email = '" + memberemail + "' ,userRole = '" + memberuserrole +  "' where id = '" + memberid + "'";
        String query = "UPDATE users SET id = '" + memberid + "' ,name = '" + membername + "' ,email = '" + memberemail + "' ,userRole = '" + memberuserrole +  "' where id = '" + memberid + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Member Update");
            alert.setHeaderText("Member Updated");
            alert.setContentText("Updated Successfully!");

            alert.showAndWait();
            txtMemberID.clear();
            txtMemberName.clear();
            txtMemberEmail.clear();
            txtMemberUserRole.clear();
            table();
        }
        catch (SQLException e) {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
        */



        if(txtMemberID.getText().isBlank() == false && txtMemberName.getText().isBlank() == false && txtMemberEmail.getText().isBlank() == false && txtMemberNic.getText().isBlank() == false){

            removeMemberFromMemberTable();
            addMemberToAdminTable();
            table();

            txtMemberID.clear();
            txtMemberName.clear();
            txtMemberEmail.clear();
            txtMemberNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change User Role");

            alert.setHeaderText("Please select a member before press change to Admin");
            alert.setContentText("User Role changing Failed!");

            alert.showAndWait();
        }
    }

    @FXML
    void ChangeToAssistLibrarianClick(ActionEvent event) throws NoSuchAlgorithmException {

        if(txtMemberID.getText().isBlank() == false && txtMemberName.getText().isBlank() == false && txtMemberEmail.getText().isBlank() == false && txtMemberNic.getText().isBlank() == false){

            removeMemberFromMemberTable();
            addMemberToAssistLibrarianTable();
            table();

            txtMemberID.clear();
            txtMemberName.clear();
            txtMemberEmail.clear();
            txtMemberNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change User Role");

            alert.setHeaderText("Please select a member before press change to Assist Librarian");
            alert.setContentText("User Role changing Failed!");

            alert.showAndWait();
        }
    }

    public void removeMemberFromMemberTable(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String memberid;

        myIndex = memberTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));

        memberid = txtMemberID.getText().substring(4);

        String query = "DELETE FROM users WHERE id = '" + memberid +  "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

        }
        catch (SQLException ex)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addMemberToAdminTable() throws NoSuchAlgorithmException {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtMemberName.getText();
        String Email = txtMemberEmail.getText();
        String NIC = txtMemberNic.getText();
        String Password = "abcd1234";
        String HashedPassword = encryptor.encryptString(Password);
        String UserRole = "Admin";
        String securityquestion1 = "Security1";
        String securityquestion2 = "Security2";


        String insertFields = "INSERT INTO admin(name, email, nic, password, userRole, securityQuestion1, securityQuestion2) VALUES ('";
        String insertValues = Name + "','" + Email + "','" + NIC + "','" + HashedPassword + "','" + UserRole +  "','" + securityquestion1 + "','" + securityquestion2 + "')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            // txtMessage.setText("Member registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Role Changing");

            alert.setHeaderText("Member Changed as Admin");
            alert.setContentText("Changed successfully!");

            alert.showAndWait();


        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void addMemberToAssistLibrarianTable() throws NoSuchAlgorithmException {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtMemberName.getText();
        String Email = txtMemberEmail.getText();
        String NIC = txtMemberNic.getText();
        String Password = "abcd1234";
        String HashedPassword = encryptor.encryptString(Password);
        String UserRole = "Assistant Librarian";
        String securityquestion1 = "Security1";
        String securityquestion2 = "Security2";


        String insertFields = "INSERT INTO assistlibrarian (name, email, nic, password, userRole, securityQuestion1, securityQuestion2) VALUES ('";
        String insertValues = Name + "','" + Email + "','" + NIC + "','" + HashedPassword + "','" + UserRole +  "','" + securityquestion1 + "','" + securityquestion2 + "')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            // txtMessage.setText("Member registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Role Changing");

            alert.setHeaderText("Member Changed as Assistant Librarian");
            alert.setContentText("Changed successfully!");

            alert.showAndWait();



        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    @FXML
    void HomeClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarian_view.fxml"));
        Stage window = (Stage) HomeBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
            Parent root = loader.load();
            LibrarianViewController librarianViewController = loader.getController();
            librarianViewController.data(librarianMemberEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Librarian Dashboard");
            HomeBtn.getScene().getWindow().hide();
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table();
    }

}
