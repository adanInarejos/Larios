package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CocinaController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    String currentMenu;

    Usuario currentUser;

    Mesa currenMesa;

    DBManager manager = new DBManager();

    @FXML
    GridPane grid;

    public CocinaController() throws SQLException {
    }

    public void setCurrentUser(Usuario usuario){
        currentUser = usuario;
    }

    public void setCurrentMenu(String menu){
        currentMenu = menu;
    }

    public void setCurrenMesa(Mesa currenMesa) {
        this.currenMesa = currenMesa;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 1;

        try {
            List<String> pedidos = manager.listarPedidos();

            for (int i = 0; i < pedidos.size() ; i++) {
                Text pedido = new Text();
                pedido.setText(pedidos.get(i));
                pedido.setStyle("-fx-font: 24 arial");

                if (column == 1){
                    column = 0;
                    row++;
                }

                grid.add(pedido, column++, row);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(pedido, new Insets(20, 20, 50, 20));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void volver() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuBeta.fxml"));
        loader.setControllerFactory(type -> {
            if (type == MainMenuController.class) {
                try {
                    return new MainMenuController(currenMesa, currentUser);
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
