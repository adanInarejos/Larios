package com.example.larios;

import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LogInController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DBManager manager = new DBManager();

    Usuario currentUser;

    @FXML
    private Button botonLogIn;

    @FXML
    private  TextField campoTextoContrasena;

    @FXML
    Text nombre;

    public LogInController() throws SQLException {
    }

    public void setCurrentUser(Usuario usuario){
        currentUser = usuario;
        nombre.setText(currentUser.getNombre());
    }


    @FXML
    public void OnCLick(ActionEvent event) throws IOException, SQLException {
        // !!! PENDIENTE DE REVISION
        if (campoTextoContrasena.getText().equals(currentUser.getContrasena())){
            if (currentUser.administrador){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
                Parent root = loader.load();
                stage = (Stage) botonLogIn.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                AdminController adminController = loader.getController();
                adminController.setCurrentUser(currentUser);

            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("TablesView.fxml"));
                loader.setControllerFactory(type -> {
                    if (type == TablesController.class) {
                        try {
                            return new TablesController(currentUser);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    // default behavior: need this in case there are <fx:include> in the FXML
                    try {
                        return type.getConstructor().newInstance();
                    } catch (Exception exc) {
                        // fatal...
                        throw new RuntimeException(exc);
                    }
                });
                Parent root = loader.load();
                stage = (Stage) botonLogIn.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                //TablesController tablesController = loader.getController();
                //tablesController.setCurrentUser(currentUser);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Contrasena Erronea");
            alert.showAndWait();
        }


    }

}