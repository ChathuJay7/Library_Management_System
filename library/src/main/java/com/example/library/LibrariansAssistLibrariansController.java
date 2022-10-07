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

public class LibrariansAssistLibrariansController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private TableView<AssistLibrarian> assistLibrarianTable;

    @FXML
    private Button btnHome;

    @FXML
    private Button changeToAMemberBtn;

    @FXML
    private Button changeToAdminBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<AssistLibrarian, String> colEmail;

    @FXML
    private TableColumn<AssistLibrarian, Integer> colID;

    @FXML
    private TableColumn<AssistLibrarian, String> colName;

    @FXML
    private TableColumn<AssistLibrarian, String> colUserRole;

    @FXML
    private TableColumn<AssistLibrarian, String> colNic;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label librarianAdssistLibrarianEmail;

    @FXML
    private TextField txtAssistLibrarianEmail;

    @FXML
    private TextField txtAssistLibrarianID;

    @FXML
    private TextField txtAssistLibrarianName;

    @FXML
    private TextField txtAssistLibrarianNic;

    @FXML
    private TextField txtSearch;


    Encryptor encryptor = new Encryptor();


    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        librarianAdssistLibrarianEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        librarianAdssistLibrarianEmail.setText(user);
    }

    int myIndex;
    int id;

    public void table()
    {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<AssistLibrarian> obList = FXCollections.observableArrayList();
        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM assistLibrarian");

            while (rs.next()){
                obList.add(new AssistLibrarian("AL - " + rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic"),rs.getString("userRole")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("AssistLibrarianID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("AssistLibrarianName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("AssistLibrarianEmail"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("AssistLibrarianNic"));

        assistLibrarianTable.setItems(obList);

        assistLibrarianTable.setRowFactory( tv -> {
            TableRow<AssistLibrarian> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  assistLibrarianTable.getSelectionModel().getSelectedIndex();
                    //id = Integer.parseInt(String.valueOf(assistLibrarianTable.getItems().get(myIndex).getAssistLibrarianID()));
                    //txtAssistLibrarianID.setText(String.valueOf(id));
                    txtAssistLibrarianID.setText(assistLibrarianTable.getItems().get(myIndex).getAssistLibrarianID());
                    txtAssistLibrarianName.setText(assistLibrarianTable.getItems().get(myIndex).getAssistLibrarianName());
                    txtAssistLibrarianEmail.setText(assistLibrarianTable.getItems().get(myIndex).getAssistLibrarianEmail());
                    txtAssistLibrarianNic.setText(assistLibrarianTable.getItems().get(myIndex).getAssistLibrarianNic());

                }
            });
            return myRow;
        });


        // Search Function
        FilteredList<AssistLibrarian> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(AssistLibrarian -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(AssistLibrarian.getAssistLibrarianName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if(AssistLibrarian.getAssistLibrarianEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<AssistLibrarian> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(assistLibrarianTable.comparatorProperty());

        assistLibrarianTable.setItems(sortedList);
    }


    @FXML
    void AddClick(ActionEvent event) {
        AddNewAssistLibrarian();
    }

    public void AddNewAssistLibrarian(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_add_new_assist_librarian_view.fxml"));
            Parent root = loader.load();
            LibrariansAddNewAssistLibrarianController librariansAddNewAssistLibrarianController = loader.getController();
            librariansAddNewAssistLibrarianController.data(librarianAdssistLibrarianEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Assist Librarian");
            addBtn.getScene().getWindow().hide();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void ChangeToAMemberClick(ActionEvent event) throws NoSuchAlgorithmException {

        if(txtAssistLibrarianID.getText().isBlank() == false && txtAssistLibrarianName.getText().isBlank() == false && txtAssistLibrarianEmail.getText().isBlank() == false && txtAssistLibrarianNic.getText().isBlank() == false) {
            removeAssistLibrarianFromAssistLibrarianTable();
            addAssistLibrarianToMemberTable();
            table();

            txtAssistLibrarianID.clear();
            txtAssistLibrarianName.clear();
            txtAssistLibrarianEmail.clear();
            txtAssistLibrarianNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change User Role");

            alert.setHeaderText("Please select an Assist Librarian before press change to Member");
            alert.setContentText("User Role changing Failed!");

            alert.showAndWait();
        }
    }

    @FXML
    void ChangeToAdminClick(ActionEvent event) throws NoSuchAlgorithmException {

        if(txtAssistLibrarianID.getText().isBlank() == false && txtAssistLibrarianName.getText().isBlank() == false && txtAssistLibrarianEmail.getText().isBlank() == false && txtAssistLibrarianNic.getText().isBlank() == false){
            removeAssistLibrarianFromAssistLibrarianTable();
            addAssistLibrarianToAdminTable();
            table();

            txtAssistLibrarianID.clear();
            txtAssistLibrarianName.clear();
            txtAssistLibrarianEmail.clear();
            txtAssistLibrarianNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Change User Role");

            alert.setHeaderText("Please select an Assist Librarian before press change to Admin");
            alert.setContentText("User Role changing Failed!");

            alert.showAndWait();
        }
    }

    public void removeAssistLibrarianFromAssistLibrarianTable(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String memberid;

        myIndex = assistLibrarianTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(assistLibrarianTable.getItems().get(myIndex).getAssistLibrarianID()));

        memberid = txtAssistLibrarianID.getText().substring(5);

        String query = "DELETE FROM assistlibrarian WHERE id = '" + memberid +  "'";

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

    public void addAssistLibrarianToMemberTable() throws NoSuchAlgorithmException {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtAssistLibrarianName.getText();
        String Email = txtAssistLibrarianEmail.getText();
        String Nic = txtAssistLibrarianNic.getText();
        String Contact = "";
        String Password = "abcd1234";
        String HashedPassword = encryptor.encryptString(Password);
        String UserRole = "Member";
        String bookid = "";
        String securityquestion1 = "Security1";
        String securityquestion2 = "Security2";


        String insertFields = "INSERT INTO users(name, nic, email, contact, password, userRole, bookId, securityQuestion1, securityQuestion2) VALUES ('";
        String insertValues = Name + "','" + Nic + "','" + Email + "','" + Contact + "','" + HashedPassword + "','" + UserRole + "','" + bookid +  "','" + securityquestion1 + "','" + securityquestion2 + "')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            // txtMessage.setText("Member registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Role Changing");

            alert.setHeaderText("Assist Librarian Changed as Member");
            alert.setContentText("Changed successfully!");

            alert.showAndWait();


        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void addAssistLibrarianToAdminTable() throws NoSuchAlgorithmException {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String Name = txtAssistLibrarianName.getText();
        String Email = txtAssistLibrarianEmail.getText();
        String Nic = txtAssistLibrarianNic.getText();
        String Password = "abcd1234";
        String HashedPassword = encryptor.encryptString(Password);
        String UserRole = "Admin";
        String securityquestion1 = "Security1";
        String securityquestion2 = "Security2";


        String insertFields = "INSERT INTO admin(name, nic, email, password, userRole, securityQuestion1, securityQuestion2) VALUES ('";
        String insertValues = Name + "','" + Nic + "','" + Email + "','" + HashedPassword + "','" + UserRole +  "','" + securityquestion1 + "','" + securityquestion2 + "')";
        String insertToRegister = insertFields + insertValues;

        try{

            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            // txtMessage.setText("Member registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Role Changing");

            alert.setHeaderText("Assist Librarian Changed as Admin");
            alert.setContentText("Changed successfully!");

            alert.showAndWait();


        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void ClearClick(ActionEvent event) {
        txtAssistLibrarianID.clear();
        txtAssistLibrarianName.clear();
        txtAssistLibrarianEmail.clear();
        txtAssistLibrarianNic.clear();
        table();
    }

    @FXML
    void DeleteClick(ActionEvent event) {
        if(txtAssistLibrarianID.getText().isBlank() == false && txtAssistLibrarianName.getText().isBlank() == false && txtAssistLibrarianEmail.getText().isBlank() == false && txtAssistLibrarianNic.getText().isBlank() == false){

            DeleteAssistLibrarian();
            table();

            txtAssistLibrarianID.clear();
            txtAssistLibrarianName.clear();
            txtAssistLibrarianEmail.clear();
            txtAssistLibrarianNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Assist Librarian");

            alert.setHeaderText("Please select an Assist Librarian before press delete");
            alert.setContentText("Delete Failed!");

            alert.showAndWait();
        }
    }

    public void DeleteAssistLibrarian(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String memberid;

        myIndex = assistLibrarianTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(assistLibrarianTable.getItems().get(myIndex).getAssistLibrarianID()));

        memberid = txtAssistLibrarianID.getText().substring(5);
        String query = "DELETE FROM assistlibrarian WHERE id = '" + memberid +  "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Assistant Librarian Delete");

            alert.setHeaderText("Assistant Librarian Deleted");
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
    void HomeClick(ActionEvent event) {
        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
            Parent root = loader.load();
            LibrarianViewController librarianViewController = loader.getController();
            librarianViewController.data(librarianAdssistLibrarianEmail.getText());

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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table();
    }

}
