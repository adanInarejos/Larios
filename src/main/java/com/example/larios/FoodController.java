package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class FoodController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList<String> listaIngredientes = new ArrayList<>();

    DBManager dbManager = new DBManager();

    @FXML
    Text nombre;

    @FXML
    Text ingredientes;

    Usuario currentUser;
    String currentMenu;

    Mesa currentMesa;

    public FoodController() throws SQLException {
    }

    public void setNombre(String nombre){
        this.nombre.setText(nombre);
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes.setText(this.ingredientes.getText()+ingredientes + ", ");
        listaIngredientes.add(ingredientes);
    }

    public void setCurrentMesa(Mesa currentMesa) {
        this.currentMesa = currentMesa;
    }

    public void setCurrentUser(Usuario usuario){
        currentUser = usuario;
    }

    public void setCurrentMenu(String menu){
        currentMenu = menu;
    }

    public void modificarIngredinetes() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IngredientesView.fxml"));
        loader.setControllerFactory(type -> {
            if (type == IngredientesController.class) {
                try {
                    return new IngredientesController(dbManager.getPlato(nombre.getText()), listaIngredientes);
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
        stage = (Stage) nombre.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        IngredientesController ingredientesController = loader.getController();
        ingredientesController.setCurrentMenu(currentMenu);
        ingredientesController.setCurrentUser(currentUser);
        ingredientesController.setCurrentMesa(currentMesa);
    }
}
