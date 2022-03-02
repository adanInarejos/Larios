package com.example.larios;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IngredientesController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    static ArrayList<String> listaIngredientes;
    static Plato currentPlato;

    String currentMenu;

    Usuario currentUser;

    Mesa currentMesa;

    static ArrayList<String> extras = new ArrayList<>();

    @FXML
    GridPane grid;

    public IngredientesController(Plato plato, ArrayList<String> listaIngredientes){
        this.currentPlato=plato;
        this.listaIngredientes = listaIngredientes;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 1;

        for (int i = 0; i < listaIngredientes.size() ; i++) {
            Text ingrediente = new Text();
            ingrediente.setText(listaIngredientes.get(i) + "  -------------------------- X");
            ingrediente.setStyle("-fx-font: 24 arial;");
            ingrediente.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    String a = ingrediente.getText();
                    String[] strings = a.split(" ");
                    ingrediente.setText(strings[0] + " -------------------------- 0");
                    extras.add("sin " + strings[0] + ", ");
                }
            });
            if (column == 1){
                column = 0;
                row++;
            }


            grid.add(ingrediente, column++, row);

            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(ingrediente, new Insets(20, 20, 50, 20));


        }
    }

    public void modificar(){
        currentMenu = currentMenu + currentPlato.getNombre()  + " " + extras + " " + currentPlato.getPrecio() + ",\n";
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
        stage = (Stage) grid.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        MainMenuController mainMenuController = loader.getController();
        mainMenuController.setCurrentUser(currentUser);
        mainMenuController.setCurrentMenu(currentMenu);
    }
}
