package com.example.larios;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;


import java.io.IOException;

public class HelloApplication extends Application {



    @Override
    public void start(Stage stage) throws IOException, SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/larios", "root", "Cide2050");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UsersView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 1000);
        stage.setTitle("Larios");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}