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

public class MemberViewController {

    @FXML
    private Button btnBooks;

    @FXML
    private Button btnEvent;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button cancelBtn;

    @FXML
    private Label memberDashboardEmail;


    public String user;

    public void initData(CurrentUser currentUser) {
        user = currentUser.getCurrentUser();
        memberDashboardEmail.setText(user);
    }

    public void data(String data) {
        user = data;
        memberDashboardEmail.setText(user);
    }

    @FXML
    void BooksClick(ActionEvent event) throws Exception{
        /*
        Parent root = FXMLLoader.load(getClass().getResource("members_book_view.fxml"));
        Stage window = (Stage) btnBooks.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        Books();
    }

    public void Books(){
        try{
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("members_book_view.fxml"));
            Parent root = loader.load();

            CurrentUser currentUser = new CurrentUser(memberDashboardEmail.getText());
            MembersBookController membersBookController = loader.getController();
            membersBookController.initData(currentUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Books");
            stage.show();
            */

            FXMLLoader loader = new FXMLLoader(getClass().getResource("members_book_view.fxml"));
            Parent root = loader.load();
            MembersBookController membersBookController = loader.getController();
            membersBookController.data(memberDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Books");
            btnBooks.getScene().getWindow().hide();
            stage.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    @FXML
    void EventsClick(ActionEvent event) throws IOException {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("members_event_view.fxml"));
        Stage window = (Stage) btnEvent.getScene().getWindow();
        window.setScene(new Scene(root));
        */

        Events();

    }

    public void Events(){
        try{
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("members_event_view.fxml"));
            Parent root = loader.load();

            CurrentUser currentUser = new CurrentUser(memberDashboardEmail.getText());
            MembersEventController membersEventController = loader.getController();
            membersEventController.initData(currentUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Events");
            stage.show();
            */

            FXMLLoader loader = new FXMLLoader(getClass().getResource("members_event_view.fxml"));
            Parent root = loader.load();
            MembersEventController membersEventController = loader.getController();
            membersEventController.data(memberDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Events");
            btnEvent.getScene().getWindow().hide();
            stage.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void LogoutClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("landing_page_view.fxml"));
        Stage window = (Stage) btnLogout.getScene().getWindow();
        window.setTitle("Library Management System");
        window.setScene(new Scene(root));
    }

    @FXML
    void UpdateClick(ActionEvent event) {
        UpdateDetails();

    }

    public void UpdateDetails(){
        try{

            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("members_update_details_view.fxml"));
            Parent root = loader.load();

            CurrentUser currentUser = new CurrentUser(memberDashboardEmail.getText());
            MembersUpdateDetailsController membersUpdateDetailsController = loader.getController();
            membersUpdateDetailsController.initData(currentUser);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Details");
            stage.show();
            */

            FXMLLoader loader = new FXMLLoader(getClass().getResource("members_update_details_view.fxml"));
            Parent root = loader.load();
            MembersUpdateDetailsController membersUpdateDetailsController = loader.getController();
            membersUpdateDetailsController.data(memberDashboardEmail.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Details");
            btnUpdate.getScene().getWindow().hide();
            stage.show();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }





}