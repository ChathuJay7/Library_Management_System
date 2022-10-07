package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutUsController {

    @FXML
    private Button btnHome;

    @FXML
    void HomeClick(ActionEvent event) {
        toLandingPage();
    }

    public void toLandingPage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("landing_page_view.fxml"));
            Stage stage = (Stage) btnHome.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setTitle("Library Management System");
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

}
