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
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TablesController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DBManager manager = new DBManager();

    Usuario currentUser;

    @FXML
    Text nombre;

    @FXML
    GridPane grid;

    public TablesController(Usuario u) throws SQLException {
        this.currentUser=u;
    }


    public void setCurrentUser(Usuario usuario){
        nombre.setText(currentUser.getNombre());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentUser(currentUser);
        List<Mesa> mesas = null;
        int column = 0;
        int row = 1;

        try {
            mesas = manager.getListaMesas();
            for (int i = 0; i <mesas.size() ; i++) {
                boolean disponible;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("TableAvailable.fxml"));
                disponible = true;
                AnchorPane anchorPane = fxmlLoader.load();

                TableController tableController = fxmlLoader.getController();
                tableController.setCurrentUser(currentUser);
                tableController.setDisponible(disponible);
                tableController.setCurrentMesa(mesas.get(i));
                if (!(mesas.get(i).id_camarero==0)){
                    if (mesas.get(i).id_camarero == currentUser.getId()){
                        tableController.setImage();
                        tableController.tintAnchor();

                    } else {
                        tableController.setImage();
                        tableController.setDisponible(false);
                    }
                }

                if (column == 2){
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                //userName.setTextContent(usuarios.get(i).getNombre());
                //userName.setText(usuarios.get(i).getNombre());


                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(0, 115, 50, 100));

                // Para futuro: crear la mesaCOntroller y seguir copiando UsersController

            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }

    public void Logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersView.fxml"));
        Parent root = loader.load();
        stage = (Stage) grid.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
