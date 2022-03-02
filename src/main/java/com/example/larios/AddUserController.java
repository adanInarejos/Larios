package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddUserController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DBManager manager = new DBManager();

    Usuario currentUser;

    @FXML
    TextArea nombre1;

    @FXML
    TextArea primerApellido1;

    @FXML
    TextArea segundoApellido1;

    @FXML
    TextArea contrasena1;

    @FXML
    Text nombre;


    public AddUserController() throws SQLException {
    }

    public void setCurrentUse(Usuario usuario){
        currentUser = usuario;
        nombre.setText(usuario.getNombre());
    }

    public void agregarUsuario() throws SQLException {
        manager.insertarUsuario(nombre1.getText(), primerApellido1.getText(), segundoApellido1.getText(), contrasena1.getText());
    }

    public void volver() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
        Parent root = loader.load();
        stage = (Stage) contrasena1.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AdminController adminController = loader.getController();
        adminController.setCurrentUser(currentUser);
    }
}
