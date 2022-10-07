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

public class LibrariansAdminController implements Initializable {

    @FXML
    private TableView<Admins> adminTable;

    @FXML
    private Button btnHome;

    @FXML
    private Button clearBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button changeToAMemberBtn;

    @FXML
    private Button changeToAssistLibrarianBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private TextField txtAdminEmail;

    @FXML
    private TextField txtAdminID;

    @FXML
    private TextField txtAdminName;

    @FXML
    private TextField txtAdminNic;

    @FXML
    private TableColumn<Admins, String> colEmail;

    @FXML
    private TableColumn<Admins, Integer> colID;

    @FXML
    private TableColumn<Admins, String> colName;

    @FXML
    private TableColumn<Admins, String> colNic;

    @FXML
    private TableColumn<Admins, String> colUserRole;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label librarianAdminEmail;

    //ObservableList<Admins> obList = FXCollections.observableArrayList();

    Encryptor encryptor = new Encryptor();

    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        librarianAdminEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        librarianAdminEmail.setText(user);
    }

    int myIndex;
    int id;

    public void table()
    {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Admins> obList = FXCollections.observableArrayList();
        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM admin");

            while (rs.next()){
                obList.add(new Admins("A - " + rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic"),  rs.getString("userRole")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("AdminID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("AdminName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("AdminEmail"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("AdminNic"));

        adminTable.setItems(obList);

        adminTable.setRowFactory( tv -> {
            TableRow<Admins> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  adminTable.getSelectionModel().getSelectedIndex();
                    //id = Integer.parseInt(String.valueOf(adminTable.getItems().get(myIndex).getAdminID()));
                    //txtAdminID.setText(String.valueOf(id));
                    txtAdminID.setText(adminTable.getItems().get(myIndex).getAdminID());
                    txtAdminName.setText(adminTable.getItems().get(myIndex).getAdminName());
                    txtAdminEmail.setText(adminTable.getItems().get(myIndex).getAdminEmail());
                    txtAdminNic.setText(adminTable.getItems().get(myIndex).getAdminNic());

                }
            });
            return myRow;
        });


        // Search Function
        FilteredList<Admins> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Admins -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Admins.getAdminName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(Admins.getAdminEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Admins> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(adminTable.comparatorProperty());

        adminTable.setItems(sortedList);
    }




    @FXML
    void HomeClick(ActionEvent event) throws Exception {
        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
            Parent root = loader.load();
            LibrarianViewController librarianViewController = loader.getController();
            librarianViewController.data(librarianAdminEmail.getText());

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
    void ClearClick(ActionEvent event) {
        txtAdminID.clear();
        txtAdminName.clear();
        txtAdminEmail.clear();
        txtAdminNic.clear();
        table();
    }

    @FXML
    void DeleteClick(ActionEvent event) {
        if(txtAdminID.getText().isBlank() == false && txtAdminName.getText().isBlank() == false && txtAdminEmail.getText().isBlank() == false && txtAdminNic.getText().isBlank() == false){
            DeleteAdmin();
            table();

            txtAdminID.clear();
            txtAdminName.clear();
            txtAdminEmail.clear();
            txtAdminNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Admin");

            alert.setHeaderText("Please select an admin before press delete");
            alert.setContentText("Delete Failed!");

            alert.showAndWait();
        }
    }

    public void DeleteAdmin(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String memberid;

        myIndex = adminTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(adminTable.getItems().get(myIndex).getAdminID()));

        memberid = txtAdminID.getText().substring(4);

        String query = "DELETE FROM admin WHERE id = '" + memberid +  "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Admin Delete");

            alert.setHeaderText("Admin Deleted");
            alert.setContentText("Deleted successfully!");

            alert.showAndWait();
        }
        catch (SQLException ex)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void AddClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarians_add_new_admin_view.fxml"));
        Stage window = (Stage) addBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        AddNewAdmin();
    }

    public void AddNewAdmin(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_add_new_admin_view.fxml"));
            Parent root = loader.load();
            LibrariansAddNewAdminController librariansAddNewAdminController = loader.getController();
            librariansAddNewAdminController.data(librarianAdminEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Admin");
            addBtn.getScene().getWindow().hide();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void ChangeToAMemberClick(ActionEvent event) throws NoSuchAlgorithmException {

        if(txtAdminID.getText().isBlank() == false && txtAdminName.getText().isBlank() == false && txtAdminEmail.getText().isBlank() == false && txtAdminNic.getText().isBlank() == false){
            removeAdminFromAdminTable();
            addAdminToMemberTable();
            table();

            txtAdminID.clear();
            txtAdminName.clear();
            txtAdminEmail.clear();
            txtAdminNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change User Role");

            alert.setHeaderText("Please select an admin before press change to Member");
            alert.setContentText("User Role changing Failed!");

            alert.showAndWait();
        }
    }

    @FXML
    void ChangeToAssistLibrarianClick(ActionEvent event) throws NoSuchAlgorithmException {
        if(txtAdminID.getText().isBlank() == false && txtAdminName.getText().isBlank() == false && txtAdminEmail.getText().isBlank() == false && txtAdminNic.getText().isBlank() == false){
            removeAdminFromAdminTable();
            addAdminToAssistLibrarianTable();
            table();

            txtAdminID.clear();
            txtAdminName.clear();
            txtAdminEmail.clear();
            txtAdminNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change User Role");

            alert.setHeaderText("Please select an admin before press change to Assist Librarian");
            alert.setContentText("User Role changing Failed!");

            alert.showAndWait();
        }
    }

    public void removeAdminFromAdminTable(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String memberid;

        myIndex = adminTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(adminTable.getItems().get(myIndex).getAdminID()));

        memberid = txtAdminID.getText().substring(4);

        String query = "DELETE FROM admin WHERE id = '" + memberid +  "'";

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


    public void addAdminToMemberTable() throws NoSuchAlgorithmException {

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtAdminName.getText();
        String Email = txtAdminEmail.getText();
        String NIC = txtAdminNic.getText();
        String Contact = "";
        String Password = "abcd1234";
        String HashedPassword = encryptor.encryptString(Password);
        String UserRole = "Member";
        String bookid = "";
        String securityquestion1 = "Security1";
        String securityquestion2 = "Security2";


        String insertFields = "INSERT INTO users(name, nic, email, contact, password, userRole, bookId, securityQuestion1, securityQuestion2) VALUES ('";
        String insertValues = Name + "','" + NIC + "','" + Email + "','" + Contact + "','" + HashedPassword + "','" + UserRole + "','" + bookid +  "','" + securityquestion1 + "','" + securityquestion2 + "')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            // txtMessage.setText("Member registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Role Changing");

            alert.setHeaderText("Admin Changed as Member");
            alert.setContentText("Changed successfully!");

            alert.showAndWait();


        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void addAdminToAssistLibrarianTable() throws NoSuchAlgorithmException {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtAdminName.getText();
        String Email = txtAdminEmail.getText();
        String NIC = txtAdminNic.getText();
        String Password = "abcd1234";
        String HashedPassword = encryptor.encryptString(Password);
        String UserRole = "Assistant Librarian";
        String securityquestion1 = "Security1";
        String securityquestion2 = "Security2";


        String insertFields = "INSERT INTO assistlibrarian (name, nic, email, password, userRole, securityQuestion1, securityQuestion2) VALUES ('";
        String insertValues = Name + "','" + NIC + "','" + Email + "','" + HashedPassword + "','" + UserRole +  "','" + securityquestion1 + "','" + securityquestion2 + "')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            // txtMessage.setText("Member registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Role Changing");

            alert.setHeaderText("Admin Changed as Assistant Librarian");
            alert.setContentText("Changed successfully!");

            alert.showAndWait();



        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
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
