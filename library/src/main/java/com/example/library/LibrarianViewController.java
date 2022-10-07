package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LibrarianViewController {



    @FXML
    private Button adminsBtn;

    @FXML
    private Button bookBtn;

    @FXML
    private Button eventsBtn;

    @FXML
    private Button membersBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button assistLibrarianBtn;

    @FXML
    private Button inspectorBtn;

    @FXML
    private Button storeBtn;

    @FXML
    private Button btnLogout;

    @FXML
    private Label librarianDashboardEmail;



    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        librarianDashboardEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        librarianDashboardEmail.setText(user);
    }

    @FXML
    void AdminsClick(ActionEvent event) throws Exception {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarians_admin_view.fxml"));
        Stage window = (Stage) adminsBtn.getScene().getWindow();
        window.setTitle("Admins");
        window.setScene(new Scene(root));
        */

        AdminDetails();
    }

    public void AdminDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_admin_view.fxml"));
            Parent root = loader.load();
            LibrariansAdminController librariansAdminController = loader.getController();
            librariansAdminController.data(librarianDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Admins Details");
            adminsBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void BooksClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarians_book_view.fxml"));
        Stage window = (Stage) bookBtn.getScene().getWindow();
        window.setTitle("Books");
        window.setScene(new Scene(root));
        */

        BookDetails();
    }

    public void BookDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_book_view.fxml"));
            Parent root = loader.load();
            LibrariansBookController librariansBookController = loader.getController();
            librariansBookController.data(librarianDashboardEmail.getText());

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
    void EventsClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarians_event_view.fxml"));
        Stage window = (Stage) eventsBtn.getScene().getWindow();
        window.setTitle("Events");
        window.setScene(new Scene(root));
        */

        EventDetails();
    }

    public void EventDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_event_view.fxml"));
            Parent root = loader.load();
            LibrariansEventController librariansEventController = loader.getController();
            librariansEventController.data(librarianDashboardEmail.getText());

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
    void MembersClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarians_member_view.fxml"));
        Stage window = (Stage) membersBtn.getScene().getWindow();
        window.setTitle("Members");
        window.setScene(new Scene(root));
        */

        MemberDetails();
    }

    public void MemberDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_member_view.fxml"));
            Parent root = loader.load();
            LibrariansMemberController librariansMemberController = loader.getController();
            librariansMemberController.data(librarianDashboardEmail.getText());

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

        /*
        Parent root = FXMLLoader.load(getClass().getResource("librarians_update_details_view.fxml"));
        Stage window = (Stage) updataBtn.getScene().getWindow();
        window.setTitle("Update Details");
        window.setScene(new Scene(root));
        */

        UpdateDetails();
    }

    public void UpdateDetails(){
        try{
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_update_details_view.fxml"));
            Parent root = loader.load();

            CurrentUser currentUser = new CurrentUser(librarianDashboardEmail.getText());
            LibrariansUpdateDetailsController librariansUpdateDetailsController = loader.getController();
            librariansUpdateDetailsController.initData(currentUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Details");
            stage.show();
            */


            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_update_details_view.fxml"));
            Parent root = loader.load();
            LibrariansUpdateDetailsController librariansUpdateDetailsController = loader.getController();
            librariansUpdateDetailsController.data(librarianDashboardEmail.getText());

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
    void AssistLibrarianClick(ActionEvent event) {
        AssistLibrarianDetails();
    }

    public void AssistLibrarianDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_assist_librarian_view.fxml"));
            Parent root = loader.load();
            LibrariansAssistLibrariansController librariansAssistLibrariansController = loader.getController();
            librariansAssistLibrariansController.data(librarianDashboardEmail.getText());

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
    void StoreClick(ActionEvent event) {
        StoreDetails();
    }

    public void StoreDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_store_view.fxml"));
            Parent root = loader.load();
            LibrariansStoreController librariansStoreController = loader.getController();
            librariansStoreController.data(librarianDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Inspector Details");
            storeBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void InspectorClick(ActionEvent event) {
        InspectorDetails();
    }

    public void InspectorDetails(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("librarians_inspector_view.fxml"));
            Parent root = loader.load();
            LibrariansInspectorController librariansInspectorController = loader.getController();
            librariansInspectorController.data(librarianDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Inspector Details");
            inspectorBtn.getScene().getWindow().hide();
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void LogoutClick(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("landing_page_view.fxml"));
        Stage window = (Stage) btnLogout.getScene().getWindow();
        window.setTitle("Library Management System");
        window.setScene(new Scene(root));
    }


}
