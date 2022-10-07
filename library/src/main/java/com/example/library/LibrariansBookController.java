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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrariansBookController implements Initializable{

    @FXML
    private TableColumn<Books, Integer> bookIDColumn;

    @FXML
    private TableColumn<Books, String> bookNameColumn;

    @FXML
    private TableView<Books> bookTable;

    @FXML
    private Button btnHome;

    @FXML
    private TableColumn<Books, String> isAvailableColumn;

    @FXML
    private TableColumn<Books, Integer> memberIdColumn;

    @FXML
    private TableColumn<Books, String> dateColumn;

    @FXML
    private ComboBox<String> bookComboBox;

    @FXML
    private ComboBox<String> memberComboBox;

    @FXML
    private TextField txtBookID;

    @FXML
    private TextField txtBookName;

    @FXML
    private TextField txtIsBookAvailable;

    @FXML
    private TextField txtMemberID;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnIssue;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private Button addBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnBookRetuened;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label librarianBookEmail;


    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        librarianBookEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        librarianBookEmail.setText(user);
    }



    //ObservableList<Books> obList = FXCollections.observableArrayList();


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
                //obList.add(new Books(rs.getInt("bookID"), rs.getString("bookName"), rs.getString("isAvailable"), (ObservableList) rs.getString("isAvailable1"), rs.getInt("memberID")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        bookIDColumn.setCellValueFactory(new PropertyValueFactory<>( "bookID"));
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

                    //memberID = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getMemberID()));
                    //txtMemberID.setText(String.valueOf(memberID));
                    txtMemberID.setText(bookTable.getItems().get(myIndex).getMemberID());

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
                } //else if (Books.getIsBookAvailable().toLowerCase().indexOf(searchKeyword) > -1){
                    //return true;
                //}
                else {
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
    void AddClick(ActionEvent event) throws Exception{

        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarians_add_new_book_view.fxml"));
        Stage window = (Stage) btnHome.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        AddNewBook();
    }

    public void AddNewBook(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_add_new_book_view.fxml"));
            Parent root = loader.load();
            LibrariansAddNewBookController librariansAddNewBookController = loader.getController();
            librariansAddNewBookController.data(librarianBookEmail.getText());

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
        //id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getBookID()));
        //memberID = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getMemberID()));

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
    void IssueBookClick(ActionEvent event) {

        if(txtBookID.getText().isBlank() == false && txtBookName.getText().isBlank() == false){

            if(bookComboBox.getSelectionModel().isEmpty() == false && memberComboBox.getSelectionModel().isEmpty() == false){
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
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Book Issue");

                alert.setHeaderText("Please select Book Availablility and Member ID before select Issue Book");
                alert.setContentText("Issued Failed!");

                alert.showAndWait();
            }


        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book Update");

            alert.setHeaderText("Please Select a Book From table Before select Issue Book");
            alert.setContentText("Update Failed!");

            alert.showAndWait();
        }
    }


    public void bookUpdate(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String bookid,bookname,bookisavailable,bookisavailableComboBox,memberId, memberIdComboBox;

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

            alert.setTitle("Book Issue");
            alert.setHeaderText("Book Issued");
            alert.setContentText("Issued Successfully!");

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
    void UpdateBookNameClick(ActionEvent event) {
        if(txtBookID.getText().isBlank() == false && txtBookName.getText().isBlank() == false){
            bookUpdateName();
            table();

            txtBookID.clear();
            txtBookName.clear();
            txtIsBookAvailable.clear();
            txtMemberID.clear();
            bookComboBox.setValue(null);
            memberComboBox.setValue(null);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Book Update");
            alert.setHeaderText("Please Select a Book From Table");
            alert.setContentText("Update Failed!");

            alert.showAndWait();
        }
    }


    public void bookUpdateName(){
        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        String bookid,bookname,bookisavailable,bookisavailableComboBox,memberId, memberIdComboBox, date;

        myIndex = bookTable.getSelectionModel().getSelectedIndex();
        //id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getBookID()));
        //memberID = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getMemberID()));

        bookid = txtBookID.getText().substring(4);
        bookname = txtBookName.getText();
        bookisavailable = txtIsBookAvailable.getText();
        bookisavailableComboBox = bookComboBox.getValue();
        memberId = txtMemberID.getText().substring(4);
        memberIdComboBox = memberComboBox.getValue();

        String query = "UPDATE books SET bookID = '" + bookid + "' ,bookName = '" + bookname + "' ,isAvailable = '" + bookisavailable + "' ,memberID = '" + memberId + "' Where bookID = '" + bookid + "'";
        //String query = "UPDATE books SET bookID = '" + bookid + "' ,bookName = '" + bookname + "' ,isAvailable = '" + bookisavailable + "' ,memberID = '" + memberId + "' WHERE bookID = '" + bookid + "' LIMIT 1 '";


        try
        {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(query);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Book Name Update");
            alert.setHeaderText("Book Name Updated");
            alert.setContentText("Updated Successfully!");

            alert.showAndWait();
        }
        catch (SQLException e)
        {
            //Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(LibrariansBookController.class.getName()).log(Level.SEVERE, null, e);
        }
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
        // id = Integer.parseInt(String.valueOf(bookTable.getItems().get(myIndex).getBookID()));

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
    void ClearClick(ActionEvent event) {
        txtBookID.clear();
        txtBookName.clear();
        txtIsBookAvailable.clear();
        bookComboBox.setValue(null);
        txtMemberID.clear();
        memberComboBox.setValue(null);
        table();
    }


    @FXML
    void HomeClick(ActionEvent event) throws Exception {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarian_view.fxml"));
        Stage window = (Stage) btnHome.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        Home();
    }

    public void Home(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
            Parent root = loader.load();
            LibrarianViewController librarianViewController = loader.getController();
            librarianViewController.data(librarianBookEmail.getText());

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

        bookComboBox.setItems(FXCollections.observableArrayList("Available", "Not Available"));

        table();
        loadMemberId();


    }

}

