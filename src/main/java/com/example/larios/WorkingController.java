package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class WorkingController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    Usuario currentUser;

    @FXML
    Text texto;

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    public void volver() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
        Parent root = loader.load();
        stage = (Stage) texto.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AdminController adminController = loader.getController();
        adminController.setCurrentUser(currentUser);
    }
}
