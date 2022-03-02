package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;

import javax.xml.transform.Source;

public class TableController {
    private Stage stage;
    private Scene scene;

    DBManager manager = new DBManager();

    private Parent root;

    Boolean disponible;

    Usuario currentUser;

    int camarero;

    Mesa currentMesa;

    @FXML
    AnchorPane anchor;

    @FXML
    ImageView imagen;

    public TableController() throws SQLException {
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public void setCurrentMesa(Mesa currentMesa) {
        this.currentMesa = currentMesa;
    }

    public void ImageClick() throws IOException, SQLException {

        if (disponible){
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
            //mainMenuController.setCurrentMesa(currentMesa);
        } else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Mesa ocupada por otro camarero desea anadirle un producto?");
            Optional<ButtonType> action = alert.showAndWait();
            camarero = manager.getCamareroMesa(currentMesa.getId());
            if (action.get() == ButtonType.OK){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SubMenuAnadir.fxml"));
                loader.setControllerFactory(type -> {
                    if (type == SubMenuAnadirController.class) {
                        try {
                            return new SubMenuAnadirController(4, currentUser, "", currentMesa, camarero);
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
            } else {

            }

        }


    }

    public void tintAnchor(){
        anchor.setStyle("-fx-background-color: #ebb746; -fx-background-radius: 20");
    }

    public void setCurrentUser(Usuario usuario){
        this.currentUser = usuario;
    }

    public void setImage(){
        URL url = getClass().getResource("camarera.png");
        imagen.setImage(new Image(String.valueOf(url)));

    }


}
