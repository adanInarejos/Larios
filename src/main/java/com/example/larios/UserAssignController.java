package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UserAssignController {

    private Usuario currenUser;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Text userName;

    @FXML
    ImageView icon;


    public void setData(Usuario usuario){
        userName.setText(usuario.getNombre());
        currenUser = usuario;
    }

    public void ImageClick() throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("TablesAssignView.fxml"));
        loader.setControllerFactory(type -> {
            if (type == TablesAssignController.class) {
                try {
                    return new TablesAssignController(currenUser);
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
        stage = (Stage) icon.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        TablesAssignController tablesAssignController = loader.getController();
        tablesAssignController.setCurrentUser(currenUser);



    }







}
