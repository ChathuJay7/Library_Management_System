package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LandingPageController {

    @FXML
    private Button btnAbouUs;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    void ClickAboutUs(ActionEvent event) {
        toAboutUs();
    }

    public void toAboutUs(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("about_us_view.fxml"));
            Stage stage = (Stage) btnAbouUs.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setTitle("About Us");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @FXML
    void ExitClick(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void RegisterClick(ActionEvent event) {
        toRegistrationForm();
    }

    public void toRegistrationForm(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Stage stage = (Stage) btnRegister.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setTitle("Registration");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @FXML
    void loginClick(ActionEvent event) {
        toRLoginForm();
    }

    public void toRLoginForm(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setTitle("Login");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

}
