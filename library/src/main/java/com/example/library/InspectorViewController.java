package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InspectorViewController {

    @FXML
    private Button adminsBtn;

    @FXML
    private Button assistLibrarianBtn;

    @FXML
    private Button bookBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private Button eventsBtn;

    @FXML
    private Button librarianBtn;

    @FXML
    private Label inspectorDashboardEmail;

    @FXML
    private Button membersBtn;

    @FXML
    private Button updateBtn;


    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        inspectorDashboardEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        inspectorDashboardEmail.setText(user);
    }


    @FXML
    void AdminsClick(ActionEvent event) {
        AdminDetails();
    }

    public void AdminDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectors_admin_view.fxml"));
            Parent root = loader.load();
            InspectorsAdminController inspectorsAdminController = loader.getController();
            inspectorsAdminController.data(inspectorDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admins Details");
            updateBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void AssistLibrarianClick(ActionEvent event) {
        AssistLibrarianDetails();
    }

    public void AssistLibrarianDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectors_assist_librarian_view.fxml"));
            Parent root = loader.load();
            InspectorsAssistLibrarianController inspectorsAssistLibrarianController = loader.getController();
            inspectorsAssistLibrarianController.data(inspectorDashboardEmail.getText());

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
    void BooksClick(ActionEvent event) {
        BookDetails();
    }

    public void BookDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectors_book_view.fxml"));
            Parent root = loader.load();
            InspectorsBookController inspectorsBookController = loader.getController();
            inspectorsBookController.data(inspectorDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Books Details");
            bookBtn.getScene().getWindow().hide();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectors_event_view.fxml"));
            Parent root = loader.load();
            InspectorsEventController inspectorsEventController = loader.getController();
            inspectorsEventController.data(inspectorDashboardEmail.getText());

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
    void LibrarianClick(ActionEvent event) {
        LibrarianDetails();
    }

    public void LibrarianDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectors_librarian_view.fxml"));
            Parent root = loader.load();
            InspectorsLibrarianController inspectorsLibrarianController = loader.getController();
            inspectorsLibrarianController.data(inspectorDashboardEmail.getText());

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
    void LogoutClick(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("landing_page_view.fxml"));
        Stage window = (Stage) btnLogout.getScene().getWindow();
        window.setTitle("Library Management System");
        window.setScene(new Scene(root));
    }

    @FXML
    void MembersClick(ActionEvent event) {
        MemberDetails();
    }

    public void MemberDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectors_member_view.fxml"));
            Parent root = loader.load();
            InspectorsMemberController inspectorsMemberController = loader.getController();
            inspectorsMemberController.data(inspectorDashboardEmail.getText());

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("inspectors_update_details_view.fxml"));
            Parent root = loader.load();
            InspectorsUpdateDetailsController inspectorsUpdateDetailsController = loader.getController();
            inspectorsUpdateDetailsController.data(inspectorDashboardEmail.getText());

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

}
