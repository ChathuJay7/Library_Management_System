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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssistLibrariansMemberController implements Initializable {

    @FXML
    private Button HomeBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Label assistLibrarianMemberEmail;

    @FXML
    private Button clearBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Members, String> colBookId;

    @FXML
    private TableColumn<Members, String> colEmail;

    @FXML
    private TableColumn<Members, Integer> colID;

    @FXML
    private TableColumn<Members, String> colName;

    @FXML
    private TableColumn<Members, String> colNic;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableView<Members> memberTable;

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
    private Button updateBtn;


    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        assistLibrarianMemberEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        assistLibrarianMemberEmail.setText(user);
    }


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
                obList.add(new Members("M - " + rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic"), "B - " + rs.getString("bookId"), rs.getString("userRole")));
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
    void AddClick(ActionEvent event) {
        AddNewMember();
    }

    public void AddNewMember(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_add_new_member_view.fxml"));
            Parent root = loader.load();
            AssistLibrarianAddNewMemberController assistLibrarianAddNewMemberController = loader.getController();
            assistLibrarianAddNewMemberController.data(assistLibrarianMemberEmail.getText());

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
        }
        catch (SQLException ex)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void UpdateClick(ActionEvent event) {
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
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
        */

    }


    @FXML
    void HomeClick(ActionEvent event) {
        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarian_view.fxml"));
            Parent root = loader.load();
            AssistLibrarianViewController assistLibrariansViewController = loader.getController();
            assistLibrariansViewController.data(assistLibrarianMemberEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Assistant Librarian Dashboard");
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
