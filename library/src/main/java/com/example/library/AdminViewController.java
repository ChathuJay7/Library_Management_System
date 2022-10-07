package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminViewController {

    @FXML
    private Button adminsBtn;

    @FXML
    private Button booksBtn;

    @FXML
    private Button inspectorBtn;

    @FXML
    private Button librarianBtn;

    @FXML
    private Button membersBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private Button eventsBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button assistLibrarianBtn;

    @FXML
    private Label adminDashboardEmail;


    public String user;

    public void data(String data) {
        user = data;
        adminDashboardEmail.setText(user);
    }

    @FXML
    void adminsClick(ActionEvent event) throws Exception {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("admins_admin_view.fxml"));
        Stage window = (Stage) adminsBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        AdminDetails();
    }

    public void AdminDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admins_admin_view.fxml"));
            Parent root = loader.load();
            AdminsAdminController adminsAdminController = loader.getController();
            adminsAdminController.data(adminDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Details");
            adminsBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void booksClick(ActionEvent event) throws Exception {
        //toBooks();
        /*
        Parent root = FXMLLoader.load(getClass().getResource("admins_book_view.fxml"));
        Stage window = (Stage) booksBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        BookDetails();
    }

    public void BookDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admins_book_view.fxml"));
            Parent root = loader.load();
            AdminsBookController adminsBookController = loader.getController();
            adminsBookController.data(adminDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Details");
            booksBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void inspectorClick(ActionEvent event) throws IOException {

    }

    @FXML
    void librarianClick(ActionEvent event) throws Exception {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("admins_librarian_view.fxml"));
        Stage window = (Stage) librarianBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        LibrarianDetails();
    }

    public void LibrarianDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admins_librarian_view.fxml"));
            Parent root = loader.load();
            AdminsLibrarianController adminsLibrarianController = loader.getController();
            adminsLibrarianController.data(adminDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Librarian Details");
            librarianBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void assistLibrariansClick(ActionEvent event) {
        AssistLibrarianDetails();
    }

    public void AssistLibrarianDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admins_assist_librarian_view.fxml"));
            Parent root = loader.load();
            AdminsAssistLibrarianController adminsAssistLibrarianController = loader.getController();
            adminsAssistLibrarianController.data(adminDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Assist Librarian Details");
            assistLibrarianBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void membersClick(ActionEvent event) throws Exception {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("admins_member_view.fxml"));
        Stage window = (Stage) membersBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        MemberDetails();
    }

    public void MemberDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admins_member_view.fxml"));
            Parent root = loader.load();
            AdminsMemberController adminsMemberController = loader.getController();
            adminsMemberController.data(adminDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Members Details");
            membersBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void eventClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("admins_event_view.fxml"));
        Stage window = (Stage) eventsBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        EventDetails();
    }

    public void EventDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admins_event_view.fxml"));
            Parent root = loader.load();
            AdminsEventController adminsEventController = loader.getController();
            adminsEventController.data(adminDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Events Details");
            eventsBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void updateClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("admins_update_view.fxml"));
        Stage window = (Stage) updateBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        UpdateDetails();
    }

    public void UpdateDetails(){
        try{


            FXMLLoader loader = new FXMLLoader(getClass().getResource("admins_update_view.fxml"));
            Parent root = loader.load();
            AdminsUpdateController adminsUpdateController = loader.getController();
            adminsUpdateController.data(adminDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Details");
            updateBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
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
