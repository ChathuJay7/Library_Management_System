package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AssistLibrarianViewController {

    @FXML
    private Button adminsBtn;

    @FXML
    private Button librarianBtn;

    @FXML
    private Label assistLibrarianDashboardEmail;

    @FXML
    private Button bookBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private Button eventsBtn;

    @FXML
    private Button membersBtn;

    @FXML
    private Button updateBtn;


    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        assistLibrarianDashboardEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        assistLibrarianDashboardEmail.setText(user);
    }

    @FXML
    void AdminsClick(ActionEvent event) {
        AdminDetails();
    }

    public void AdminDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_admin_view.fxml"));
            Parent root = loader.load();
            AssistLibrariansAdminController assistLibrariansAdminController = loader.getController();
            assistLibrariansAdminController.data(assistLibrarianDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Details");
            adminsBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void LibrarianClick(ActionEvent event) {
        LibrarianDetails();
    }

    public void LibrarianDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_librarian_view.fxml"));
            Parent root = loader.load();
            AssistLibrariansLibrarianController assistLibrariansLibrarianController = loader.getController();
            assistLibrariansLibrarianController.data(assistLibrarianDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Librarian Details");
            membersBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void BooksClick(ActionEvent event) {
        BookDetails();
    }

    public void BookDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_book_view.fxml"));
            Parent root = loader.load();
            AssistLibrariansBookController assistLibrariansBookController = loader.getController();
            assistLibrariansBookController.data(assistLibrarianDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Book Details");
            membersBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void EventsClick(ActionEvent event) {
        EventDetails();
    }

    public void EventDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_event_view.fxml"));
            Parent root = loader.load();
            AssistLibrariansEventController assistLibrariansEventController = loader.getController();
            assistLibrariansEventController.data(assistLibrarianDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Event Details");
            eventsBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void MembersClick(ActionEvent event) {
        MemberDetails();
    }

    public void MemberDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_member_view.fxml"));
            Parent root = loader.load();
            AssistLibrariansMemberController assistLibrariansMemberController = loader.getController();
            assistLibrariansMemberController.data(assistLibrarianDashboardEmail.getText());

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
    void UpdateClick(ActionEvent event) {
        UpdateDetails();
    }

    public void UpdateDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("assist_librarians_update_view.fxml"));
            Parent root = loader.load();
            AssistLibrariansUpdateController assistLibrariansUpdateController = loader.getController();
            assistLibrariansUpdateController.data(assistLibrarianDashboardEmail.getText());

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
