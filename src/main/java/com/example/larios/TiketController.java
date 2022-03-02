package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TiketController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    String currentMenu;

    Usuario currentUser;

    Mesa currenMesa;

    DBManager manager = new DBManager();

    @FXML
    GridPane grid;

    public TiketController() throws SQLException {
    }

    public void setCurrentUser(Usuario usuario){
        currentUser = usuario;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int column = 0;
        int row = 1;

        try {
            List<String> pedidos = manager.listarPedidos();

            for (int i = 0; i < pedidos.size() ; i++){
                String linea = pedidos.get(i);
                String[] menus = linea.split("\n");
                for (int j = 0; j <menus.length ; j++) {
                    Text pedido = new Text();
                    if (j == 0){
                        pedido.setText(menus[j]);
                        pedido.setStyle("-fx-font: 24 arial");
                        grid.add(pedido, column++, row);
                    } else {
                        pedido.setText(menus[j] + "------------ X");
                        pedido.setStyle("-fx-font: 24 arial");
                        grid.add(pedido, column++, row);
                    }
                    if (column == 1){
                        column = 0;
                        row++;
                    }
                    if (j ==( menus.length-1)){
                        GridPane.setMargin(pedido, new Insets(20, 20, 50, 20));
                    } else {
                        GridPane.setMargin(pedido, new Insets(20, 20, 0, 20));
                    }
                }
//                Text pedido = new Text();
//                pedido.setText(pedidos.get(i));
//                pedido.setStyle("-fx-font: 24 arial");




                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);



            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void volver() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
        Parent root = loader.load();
        stage = (Stage) grid.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        AdminController adminController = loader.getController();
        adminController.setCurrentUser(currentUser);
    }
}
