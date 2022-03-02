package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;


public class TableAssignController {

    Usuario currentUser;

    Mesa currentTable;

    DBManager dbManager = new DBManager();

    @FXML
    AnchorPane anchor;

    @FXML
    ImageView imagen;

    Boolean disponible = true;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public TableAssignController() throws SQLException {
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public void asignar() throws SQLException, IOException {
        if (disponible){
            dbManager.asignarMesa(currentUser.getId(), currentTable.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Usuario: " + currentTable.id_camarero + " asignado a la mesa: " + currentTable.getId());
            alert.showAndWait();
            volver();
        } else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Mesa ocupada");
            alert.showAndWait();
        }
    }

    public void volver() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
        Parent root = loader.load();
        stage = (Stage) anchor.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AdminController adminController = loader.getController();
        adminController.setCurrentUser(currentUser);
    }

    public void setCurrentUser(Usuario usuario){
        this.currentUser = usuario;
    }

    public void setCurrentTable(Mesa mesa){
        this.currentTable=mesa;
    }

    public void setImage(){
        URL url = getClass().getResource("camarera.png");
        imagen.setImage(new Image(String.valueOf(url)));

    }

}
