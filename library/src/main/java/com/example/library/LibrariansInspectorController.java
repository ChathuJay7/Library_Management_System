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

public class LibrariansInspectorController implements Initializable {

    @FXML
    private Button HomeBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Inspector, String> colEmail;

    @FXML
    private TableColumn<Inspector, Integer> colID;

    @FXML
    private TableColumn<Inspector, String> colName;

    @FXML
    private TableColumn<Inspector, String> colNic;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label librarianInspectorEmail;

    @FXML
    private TableView<Inspector> inspectorTable;

    @FXML
    private TextField txtInspectorEmail;

    @FXML
    private TextField txtInspectorID;

    @FXML
    private TextField txtInspectorName;

    @FXML
    private TextField txtInspectorNic;

    @FXML
    private TextField txtSearch;

    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        librarianInspectorEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        librarianInspectorEmail.setText(user);
    }


    int myIndex;
    int id;


    public void table()
    {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Inspector> obList = FXCollections.observableArrayList();
        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM inspector");

            while (rs.next()){
                obList.add(new Inspector("I - " + rs.getString("id"), rs.getString("name"), rs.getString("email"), rs.getString("nic")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        colID.setCellValueFactory(new PropertyValueFactory<>("InspectorID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("InspectorName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("InspectorEmail"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("InspectorNic"));

        inspectorTable.setItems(obList);

        inspectorTable.setRowFactory( tv -> {
            TableRow<Inspector> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  inspectorTable.getSelectionModel().getSelectedIndex();
                    //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));
                    //txtMemberID.setText(String.valueOf(id));
                    txtInspectorID.setText(inspectorTable.getItems().get(myIndex).getInspectorID());
                    txtInspectorName.setText(inspectorTable.getItems().get(myIndex).getInspectorName());
                    txtInspectorEmail.setText(inspectorTable.getItems().get(myIndex).getInspectorEmail());
                    txtInspectorNic.setText(inspectorTable.getItems().get(myIndex).getInspectorNic());

                }
            });
            return myRow;
        });



        // Search Function
        FilteredList<Inspector> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Members -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Members.getInspectorName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if (Members.getInspectorEmail().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Inspector> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(inspectorTable.comparatorProperty());

        inspectorTable.setItems(sortedList);
    }


    @FXML
    void AddClick(ActionEvent event) {
        AddNewInspector();
    }

    public void AddNewInspector(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_add_new_inspector_view.fxml"));
            Parent root = loader.load();
            LibrariansAddNewInspectorController librariansAddNewInspectorController = loader.getController();
            librariansAddNewInspectorController.data(librarianInspectorEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Inspector");
            addBtn.getScene().getWindow().hide();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void ClearClick(ActionEvent event) {
        txtInspectorID.clear();
        txtInspectorName.clear();
        txtInspectorEmail.clear();
        txtInspectorNic.clear();
        table();
    }

    @FXML
    void DeleteClick(ActionEvent event) {
        if(txtInspectorID.getText().isBlank() == false && txtInspectorName.getText().isBlank() == false && txtInspectorEmail.getText().isBlank() == false && txtInspectorNic.getText().isBlank() == false){
            DeleteMember();
            table();

            txtInspectorID.clear();
            txtInspectorName.clear();
            txtInspectorEmail.clear();
            txtInspectorNic.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Inspector");

            alert.setHeaderText("Please select a inspector before press delete");
            alert.setContentText("Delete Failed!");

            alert.showAndWait();
        }
    }

    public void DeleteMember(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String inspectorid;

        myIndex = inspectorTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(memberTable.getItems().get(myIndex).getMemberID()));

        inspectorid = txtInspectorID.getText().substring(4);

        String query = "DELETE FROM inspector WHERE id = '" + inspectorid +  "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inspector Delete");

            alert.setHeaderText("Inspector Deleted");
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
    void HomeClick(ActionEvent event) {
        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
            Parent root = loader.load();
            LibrarianViewController librarianViewController = loader.getController();
            librarianViewController.data(librarianInspectorEmail.getText());

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
