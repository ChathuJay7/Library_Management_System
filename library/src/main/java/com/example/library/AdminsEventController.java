
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
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminsEventController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Events, String> eventDateColumn;

    @FXML
    private TableColumn<Events, Integer> eventIDColumn;

    @FXML
    private TableColumn<Events, String> eventNameColumn;

    @FXML
    private TableView<Events> eventTable;

    @FXML
    private TableColumn<Events, String> eventTimeColumn;

    @FXML
    private DatePicker txtEventDate;

    @FXML
    private TextField txtEventID;

    @FXML
    private TextField txtEventName;

    @FXML
    private TextField txtEventTime;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label adminEventEmail;


    public String user;

    public void data(String data) {
        user = data;
        adminEventEmail.setText(user);
    }


    int myIndex;
    int id;
    java.util.Date date;


    public void table()
    {
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Events> obList = FXCollections.observableArrayList();
        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM events");

            while (rs.next()){
                obList.add(new Events("E - " + rs.getString("id"), rs.getString("name"), rs.getDate("date"), rs.getString("time")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        eventIDColumn.setCellValueFactory(new PropertyValueFactory<>("eventID"));
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        eventTimeColumn.setCellValueFactory(new PropertyValueFactory<>("eventTime"));

        eventTable.setItems(obList);

        eventTable.setRowFactory( tv -> {
            TableRow<Events> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  eventTable.getSelectionModel().getSelectedIndex();
                    //id = Integer.parseInt(String.valueOf(eventTable.getItems().get(myIndex).getEventID()));
                    //txtEventID.setText(String.valueOf(id));
                    txtEventID.setText(eventTable.getItems().get(myIndex).getEventID());
                    txtEventName.setText(eventTable.getItems().get(myIndex).getEventName());
                    date = Date.valueOf(String.valueOf(eventTable.getItems().get(myIndex).getEventDate()));
                    txtEventDate.setValue(LocalDate.parse(String.valueOf(date)));
                    //txtEventDate.setValue(eventTable.getItems().getEventDate());
                    txtEventTime.setText(eventTable.getItems().get(myIndex).getEventTime());

                }
            });
            return myRow;
        });



        // Search Function
        FilteredList<Events> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Events -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Events.getEventName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Events> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(eventTable.comparatorProperty());

        eventTable.setItems(sortedList);
    }



    @FXML
    void AddClick(ActionEvent event) {
        if(txtEventName.getText().isBlank() == false &&  txtEventTime.getText().isBlank() == false){
            AddEvent();
            table();

            txtEventID.clear();
            txtEventName.clear();
            txtEventDate.setValue(null);
            txtEventTime.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Event");

            alert.setHeaderText("Please fill all text fields before press Add Event");
            alert.setContentText("Add Event Failed!");

            alert.showAndWait();
        }
    }

    public void AddEvent(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String eventid;
        String eventname;
        LocalDate eventdate;
        String eventtime;

        eventid = txtEventID.getText();
        eventname = txtEventName.getText();
        eventdate = txtEventDate.getValue();
        eventtime = txtEventTime.getText();
        // memberId = txtMemberID.getText();

        String insertFields = "INSERT INTO events(name, date, time) VALUES ('";
        String insertValues = eventname + "','" + eventdate + "','" + eventtime + "')";
        String query = insertFields + insertValues;

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            //txtMessage.setText("User registered Successfully!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Event Registration");
            alert.setHeaderText("Event Registered");
            alert.setContentText("Registered successfully!");

            alert.showAndWait();

            //txtEventID.clear();
            //txtEventName.clear();
            //txtEventDate.getEditor().clear();;
            //txtEventTime.clear();

            //table();
        }
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    void ClearClick(ActionEvent event) {
        txtEventID.clear();
        txtEventName.clear();
        //txtEventDate.getEditor().clear();
        txtEventDate .setValue(null);
        txtEventTime.clear();
        table();
    }


    @FXML
    void UpdateClick(ActionEvent event) {
        if(txtEventID.getText().isBlank() == false && txtEventName.getText().isBlank() == false &&  txtEventTime.getText().isBlank() == false){
            UpdateEvent();
            table();

            txtEventID.clear();
            txtEventName.clear();
            txtEventDate.setValue(null);
            txtEventTime.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Event Update");

            alert.setHeaderText("Please select a event before press update");
            alert.setContentText("Update Failed!");

            alert.showAndWait();
        }
    }

    public void UpdateEvent(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String eventid,eventname,eventtime;
        LocalDate eventdate;

        myIndex = eventTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(eventTable.getItems().get(myIndex).getEventID()));
        date = Date.valueOf(String.valueOf(eventTable.getItems().get(myIndex).getEventDate()));

        eventid = txtEventID.getText().substring(4);
        eventname = txtEventName.getText();
        eventdate = txtEventDate.getValue();
        eventtime = txtEventTime.getText();

        String query = "UPDATE events SET id = '" + eventid + "' ,name = '" + eventname + "' ,date = '" + eventdate + "' ,time = '" + eventtime + "' where id = '" + eventid + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Event Update");
            alert.setHeaderText("Event Updated");
            alert.setContentText("Updated Successfully!");

            alert.showAndWait();

        }
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    void DeleteClick(ActionEvent event) {
        if(txtEventID.getText().isBlank() == false && txtEventName.getText().isBlank() == false){
            DeleteEvent();
            table();

            txtEventID.clear();
            txtEventName.clear();
            txtEventDate.setValue(null);
            txtEventTime.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Event Delete");

            alert.setHeaderText("Please select an event before press delete");
            alert.setContentText("Delete Failed!");

            alert.showAndWait();
        }
    }

    public void DeleteEvent(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String eventid;

        myIndex = eventTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(eventTable.getItems().get(myIndex).getEventID()));

        eventid = txtEventID.getText().substring(4);

        String query = "DELETE FROM events WHERE id = '" + eventid +  "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Event Delete");

            alert.setHeaderText("Event Deleted");
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
    void HomeClick(ActionEvent event) throws Exception{
        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_view.fxml"));
            Parent root = loader.load();
            AdminViewController adminViewController = loader.getController();
            adminViewController.data(adminEventEmail.getText());

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

