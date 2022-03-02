package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MensajesController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    DBManager manager = new DBManager();

    String currentMenu;

    Usuario currentUser;

    Mesa currentMesa;

    @FXML
    AnchorPane anchor;

    @FXML
    TextField asunto;

    @FXML
    TextArea mensaje;

    public MensajesController() throws SQLException {
    }

    public void setCurrentUser(Usuario usuario){
        currentUser = usuario;
    }

    public void setCurrentMenu(String menu){
        currentMenu = menu;
    }

    public void setCurrentMesa(Mesa currentMesa) {
        this.currentMesa = currentMesa;
    }


    public void enviar() throws SQLException {
        manager.InsertarMensajeAdmin(asunto.getText() + ":\n" + mensaje.getText());
    }



    public void volver() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuBeta.fxml"));
        loader.setControllerFactory(type -> {
            if (type == MainMenuController.class) {
                try {
                    return new MainMenuController(currentMesa, currentUser);
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
        stage = (Stage) anchor.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setCurrentUser(currentUser);
        mainMenuController.setCurrentMenu(currentMenu);
    }
}
