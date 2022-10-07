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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrariansStoreController implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnLogout;

    @FXML
    private TableColumn<Store, String> colItemCount;

    @FXML
    private TableColumn<Store, Integer> colItemId;

    @FXML
    private TableColumn<Store, String> colItemName;

    @FXML
    private TableView<Store> storeTable;

    @FXML
    private TextField txtItemCount;

    @FXML
    private TextField txtItemID;

    @FXML
    private TextField txtItemtName;

    @FXML
    private TextField txtSearch;

    @FXML
    private Label librarianStoreEmail;


    /*
    public ObservableList<Store> data;

    private List<Store> initializeTable() {

        //String query = "SELECT * FROM store";

        data = FXCollections.observableArrayList();

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sf = cfg.buildSessionFactory();

        Iterator ite = sf.hibernateQuerry().list().iterator();
        while (ite.hasNext()) {
            Store obj = (Store) ite.next();

            data.add(obj);
        }



        return data;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colID.setCellValueFactory(new PropertyValueFactory<Store, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Store, String>("name"));

        table.getItems().setAll(initializeTable());
    }

    */


    /*
    Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
    SessionFactory sf = cfg.buildSessionFactory();

    void initialize()
    {

        colID.setCellValueFactory(new PropertyValueFactory<Store,Integer>("id")); // here id is a variable name which is define in pojo.
        colName.setCellValueFactory(new PropertyValueFactory<Store,String>("name"));

        public ObservableList<Store> data;

        data  =  FXCollections.observableArrayList();
        sf = cfg.buildSessionFactory();
        Session sess  = sf.openSession();
        Query qee = sess.createQuery("from store");
        Iterator ite  = qee.iterate();
        while(ite.hasNext())
        {
            Store obj = (Store) ite.next();

            data.add(obj);
        }
        table.setItems(data);

    }
    */


    int myIndex;
    int id;



    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        librarianStoreEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        librarianStoreEmail.setText(user);
    }

    public void table()
    {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sf = cfg.buildSessionFactory();

        DatabaseConnector connectNow = new DatabaseConnector();
        Connection connectDB = connectNow.getConnection();

        ObservableList<Store> obList = FXCollections.observableArrayList();
        try{

            ResultSet rs = connectDB.createStatement().executeQuery("SELECT * FROM store");

            while (rs.next()){
                obList.add(new Store(rs.getInt("id"), rs.getString("name"), rs.getString("count")));
            }

        }
        catch (SQLException e){
            Logger.getLogger(MembersBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        colItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colItemCount.setCellValueFactory(new PropertyValueFactory<>("count"));


        storeTable.setItems(obList);

        storeTable.setRowFactory( tv -> {
            TableRow<Store> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    myIndex =  storeTable.getSelectionModel().getSelectedIndex();
                    id = Integer.parseInt(String.valueOf(storeTable.getItems().get(myIndex).getId()));
                    txtItemID.setText(String.valueOf(id));
                    //txtEventID.setText(eventTable.getItems().get(myIndex).getEventID());
                    txtItemtName.setText(storeTable.getItems().get(myIndex).getName());
                    txtItemCount.setText(storeTable.getItems().get(myIndex).getCount());

                }
            });
            return myRow;
        });



        // Search Function
        FilteredList<Store> filteredList = new FilteredList<> (obList, b -> true);

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filteredList.setPredicate(Store -> {

                // If no search value were found display all the results
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if(Store.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<Store> sortedList = new SortedList<>(filteredList);

        sortedList.comparatorProperty().bind(storeTable.comparatorProperty());

        storeTable.setItems(sortedList);


    }



    @FXML
    void AddClick(ActionEvent event) {

        if(txtItemtName.getText().isBlank() == false && txtItemCount.getText().isBlank() == false){
            AddItem();
            table();

            txtItemID.clear();
            txtItemtName.clear();
            txtItemCount.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Item to Store");

            alert.setHeaderText("Please fill all text fields before press Add Item");
            alert.setContentText("Add Item Failed!");

            alert.showAndWait();
        }

    }

    public void AddItem(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sf = cfg.buildSessionFactory();

        Store store = new Store();
        //store.setId(2);
        store.setName(txtItemtName.getText());
        store.setCount(txtItemCount.getText());

        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(store);

        System.out.println(txtItemtName.getText());
        System.out.println(txtItemCount.getText());

        session.getTransaction().commit();
        session.close();
    }

    @FXML
    void DeleteClick(ActionEvent event) {

        if(txtItemtName.getText().isBlank() == false && txtItemCount.getText().isBlank() == false){
            DeleteItem();
            table();

            txtItemID.clear();
            txtItemtName.clear();
            txtItemCount.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Item");

            alert.setHeaderText("Please select item from table before click Delete!");
            alert.setContentText("Delete Item Failed!");

            alert.showAndWait();
        }


    }

    public void DeleteItem(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sf = cfg.buildSessionFactory();

        Store store = new Store();
        store.getName();


        Session session = sf.openSession();
        session.beginTransaction();

        store = (Store) session.get(Store.class, txtItemID.getText());

        //store.setId(2);
        //store.setName(txtItemID.getText());
        //store.setName(txtItemtName.getText());
        //store.setCount(txtItemCount.getText());

        session.delete(store);

        System.out.println(txtItemID.getText());
        System.out.println(txtItemtName.getText());
        System.out.println(txtItemCount.getText());

        session.getTransaction().commit();
        session.close();
    }


    @FXML
    void UpdateClick(ActionEvent event) {


        if(txtItemtName.getText().isBlank() == false && txtItemCount.getText().isBlank() == false){
            UpdateItem();
            table();

            txtItemID.clear();
            txtItemtName.clear();
            txtItemCount.clear();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Item");

            alert.setHeaderText("Please select item from table before click Update!");
            alert.setContentText("Update Item Failed!");

            alert.showAndWait();
        }
    }

    public void UpdateItem(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sf = cfg.buildSessionFactory();

        Store store = new Store();
        store.getName();


        Session session = sf.openSession();
        session.beginTransaction();

        store = (Store) session.get(Store.class, txtItemID.getText());

        //store.setId(2);
        store.setName(txtItemID.getText());
        store.setName(txtItemtName.getText());
        store.setCount(txtItemCount.getText());

        session.update(store);

        System.out.println(txtItemID.getText());
        System.out.println(txtItemtName.getText());
        System.out.println(txtItemCount.getText());

        session.getTransaction().commit();
        session.close();
    }


    @FXML
    void HomeClick(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarian_view.fxml"));
            Parent root = loader.load();
            LibrarianViewController librarianViewController = loader.getController();
            librarianViewController.data(librarianStoreEmail.getText());

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
        txtItemID.clear();
        txtItemtName.clear();
        txtItemCount.clear();
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
