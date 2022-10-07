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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssistLibrariansBookController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Label assistLibrarianBookEmail;

    @FXML
    private TableColumn<Books, Integer> bookIDColumn;

    @FXML
    private TableColumn<Books, String> bookNameColumn;

    @FXML
    private TableView<Books> bookTable;

    @FXML
    private Button btnBookRetuened;

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
    private TableColumn<Books, String> isAvailableColumn;

    @FXML
    private TableColumn<Books, Integer> memberIdColumn;

    @FXML
    private TableColumn<Books, String> dateColumn;

    @FXML
    private TextField txtBookID;

    @FXML
    private TextField txtBookName;

    @FXML
    private TextField txtIsBookAvailable;

    @FXML
    private TextField txtMemberID;

    @FXML
    private TextField txtSearch;

    @FXML
    private ComboBox<String> bookComboBox;

    @FXML
    private ComboBox<String> memberComboBox;


    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        assistLibrarianBookEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        assistLibrarianBookEmail.setText(user);
    }


    int myIndex;
    int id;
    int memberID;

    public void table()
    {



        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Books> obList = FXCollections.observableArrayList();
        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM books");

            while (rs.next()){
                obList.add(new Books("B - " + rs.getString("bookID"), rs.getString("bookName"), rs.getString("isAvailable"), "M - " + rs.getString("memberID"), rs.getString("date")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        isAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("isBookAvailable"));
        memberIdColumn.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("bookDate"));

        bookTable.setItems(obList);

        bookTable.setRowFactory( tv -> {
            TableRow<Books> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  bookTable.getSelectionModel().getSelectedIndex();
                    //id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getBookID()));
                    //txtBookID.setText(String.valueOf(id));
                    txtBookID.setText(bookTable.getItems().get(myIndex).getBookID());
                    txtBookName.setText(bookTable.getItems().get(myIndex).getBookName());
                    txtIsBookAvailable.setText(bookTable.getItems().get(myIndex).getIsBookAvailable());
                    txtMemberID.setText(bookTable.getItems().get(myIndex).getMemberID());
                    //memberID = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getMemberID()));
                    //txtMemberID.setText(String.valueOf(memberID));

                }
            });
            return myRow;
        });


        // Search Function
        FilteredList<Books> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Books -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Books.getBookName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else if (Books.getIsBookAvailable().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Books> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(bookTable.comparatorProperty());

        bookTable.setItems(sortedList);

    }


    public void loadMemberId() {

        try {

            DatabaseConnector connectNow = new DatabaseConnector();
            Connection connectDB = connectNow.getConnection();


            ResultSet rs = connectDB.createStatement().executeQuery("SELECT id FROM users WHERE bookId = 0");
            ObservableList data = FXCollections.observableArrayList();

            while (rs.next()){
                data.add(new String(rs.getString(1)));
            }
            memberComboBox.setItems(data);

        } catch (SQLException e){
            Logger.getLogger(LibrariansBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    void AddClick(ActionEvent event) {
        AddNewBook();
    }

    public void AddNewBook(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_add_new_book_view.fxml"));
            Parent root = loader.load();
            AssistLibrarianAddNewBookController assistLibrarianAddNewBookController = loader.getController();
            assistLibrarianAddNewBookController.data(assistLibrarianBookEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add New Book");
            addBtn.getScene().getWindow().hide();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void ClearClick(ActionEvent event) {
        txtBookID.clear();
        txtBookName.clear();
        txtIsBookAvailable.clear();
        txtMemberID.clear();
        bookComboBox.setValue(null);
        memberComboBox.setValue(null);
        table();
    }

    @FXML
    void DeleteClick(ActionEvent event) {
        if(txtBookID.getText().isBlank() == false && txtBookName.getText().isBlank() == false){
            DeleteBook();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book Delete");

            alert.setHeaderText("Please select a book before press delete");
            alert.setContentText("Delete Failed!");

            alert.showAndWait();
        }
    }

    public void DeleteBook(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String bookid;

        myIndex = bookTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getBookID()));

        bookid = txtBookID.getText().substring(4);

        String query = "DELETE FROM books WHERE bookID = '" + bookid +  "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book Delete");

            alert.setHeaderText("Book Deleted");
            alert.setContentText("Deleted successfully!");

            alert.showAndWait();
            table();
        }
        catch (SQLException ex)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(LibrariansBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            assistLibrariansViewController.data(assistLibrarianBookEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Assistant Librarian Dashboard");
            btnHome.getScene().getWindow().hide();
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void ReturnClick(ActionEvent event) {

        if(txtBookID.getText().isBlank() == false && txtBookName.getText().isBlank() == false){
            bookReturnedMemberUpdate();
            bookReturnedBookUpdate();
            loadMemberId();
            table();

            txtBookID.clear();
            txtBookName.clear();
            txtIsBookAvailable.clear();
            txtMemberID.clear();
            bookComboBox.setValue(null);
            memberComboBox.setValue(null);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book Return");

            alert.setHeaderText("Please select a book before press book returned");
            alert.setContentText("Return Failed!");

            alert.showAndWait();
        }
    }

    public void bookReturnedBookUpdate(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String bookid,memberId,isAvailable,date;

        myIndex = bookTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getBookID()));
        //memberID = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getMemberID()));

        bookid = txtBookID.getText().substring(4);
        // memberId = txtMemberID.getText();
        memberId = "";
        isAvailable = "Available";
        date = "";

        String query = "UPDATE books SET isAvailable = '" + isAvailable + "' ,memberID = '" + memberId + "' ,date = '" + date +  "' where bookID = '" + bookid + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Book Returned Message");
            alert.setHeaderText("Book Returned");
            alert.setContentText("Returned successfully!");

            alert.showAndWait();


        }
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(LibrariansBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void bookReturnedMemberUpdate(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String bookid,memberId;

        myIndex = bookTable.getSelectionModel().getSelectedIndex();
        // id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getBookID()));
        // memberID = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getMemberID()));

        //bookid = txtBookID.getText();
        bookid = "";
        memberId = txtMemberID.getText().substring(4);


        String query = "UPDATE users SET bookID = '" + bookid +  "' where id = '" + memberId + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

        }
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(LibrariansBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    @FXML
    void UpdateClick(ActionEvent event) {

        if(txtBookID.getText().isBlank() == false && txtBookName.getText().isBlank() == false){
            memberUpdate();
            bookUpdate();
            loadMemberId();
            table();

            txtBookID.clear();
            txtBookName.clear();
            txtIsBookAvailable.clear();
            txtMemberID.clear();
            bookComboBox.setValue(null);
            memberComboBox.setValue(null);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book Update");

            alert.setHeaderText("Please select a book before press update");
            alert.setContentText("Update Failed!");

            alert.showAndWait();
        }
    }

    public void bookUpdate(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String bookid,bookname,bookisavailable,bookisavailableComboBox, memberId, memberIdComboBox;

        LocalDate date = LocalDate.now();

        myIndex = bookTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getBookID()));
        //memberID = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getMemberID()));

        bookid = txtBookID.getText().substring(4);
        bookname = txtBookName.getText();
        bookisavailable = txtIsBookAvailable.getText();
        bookisavailableComboBox = bookComboBox.getValue();
        memberId = txtMemberID.getText();
        memberIdComboBox = memberComboBox.getValue();

        String query = "UPDATE books SET bookID = '" + bookid + "' ,bookName = '" + bookname + "' ,isAvailable = '" + bookisavailableComboBox + "' ,memberID = '" + memberIdComboBox + "' ,date = '" + date + "' Where bookID = '" + bookid + "'";
        //String query = "UPDATE books SET bookID = '" + bookid + "' ,bookName = '" + bookname + "' ,isAvailable = '" + bookisavailable + "' ,memberID = '" + memberId + "' WHERE bookID = '" + bookid + "' LIMIT 1 '";


        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Book Update");
            alert.setHeaderText("Book Updated");
            alert.setContentText("Updated Successfully!");

            alert.showAndWait();


        }
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(LibrariansBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void memberUpdate(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String bookid,memberId,memberIdComboBox;

        myIndex = bookTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getBookID()));
        //memberID = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getMemberID()));

        bookid = txtBookID.getText().substring(4);
        memberId = txtMemberID.getText();
        memberIdComboBox = memberComboBox.getValue();

        String query = "UPDATE users SET bookID = '" + bookid +  "' where id = '" + memberIdComboBox + "'";

        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

        }
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(LibrariansBookController.class.getName()).log(Level.SEVERE, null, e);
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

        bookComboBox.setItems(FXCollections.observableArrayList("Available", "Not Available"));

        table();
        loadMemberId();
    }

}
