package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    Usuario currentUser;

    DBManager manager = new DBManager();


    @FXML
    Button boton;

    @FXML
    Text nombre;

    public AdminController() throws SQLException {
    }

    public void setCurrentUser(Usuario usuario){
        currentUser=usuario;
        nombre.setText(currentUser.getNombre());
    }

    public void agregarUsuario() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUserView.fxml"));
        Parent root = loader.load();
        stage = (Stage) boton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AddUserController addUserController = loader.getController();
        addUserController.setCurrentUse(currentUser);
    }

    public void asignarCamarero() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersAssignView.fxml"));
        Parent root = loader.load();
        stage = (Stage) boton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        UsersAssignController usersAssignController = loader.getController();

    }

    public void modificarTicket() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TiketView.fxml"));
        Parent root = loader.load();
        stage = (Stage) boton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        TiketController tiketController = loader.getController();
        tiketController.setCurrentUser(currentUser);
    }

    public void mirarMesas() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TablesAssignView.fxml"));
        loader.setControllerFactory(type -> {
            if (type == TablesAssignController.class) {
                try {
                    return new TablesAssignController(currentUser);
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
        stage = (Stage) boton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        TablesAssignController tablesAssignController = loader.getController();
        tablesAssignController.setCurrentUser(currentUser);
    }

    public void working() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Working.fxml"));
        Parent root = loader.load();
        stage = (Stage) boton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        WorkingController workingController = loader.getController();
        workingController.setCurrentUser(currentUser);

    }

    public void Logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersView.fxml"));
        Parent root = loader.load();
        stage = (Stage) boton.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<String> mensajes = manager.listarMensajeAdmin();
            if (mensajes.size()>0){
                for (int i = 0; i < mensajes.size() ; i++) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(mensajes.get(i));
                    alert.showAndWait();
                }

            } else {

            }
            manager.eliminarMensajeAdmin();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
