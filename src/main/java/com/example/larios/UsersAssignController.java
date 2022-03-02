package com.example.larios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
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

public class UsersAssignController implements Initializable {

    private DBManager manager = new DBManager();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    GridPane grid;

    @FXML
    Text userName;



    public UsersAssignController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        List<Usuario> usuarios = null;
        int column = 0;
        int row = 1;
        try {
            usuarios = manager.getListaUsuarios();
            for (int i = 0; i <usuarios.size() ; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("UserAssign.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                UserAssignController userController = fxmlLoader.getController();
                userController.setData(usuarios.get(i));

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

                GridPane.setMargin(anchorPane, new Insets(0, 135, 0, 0));
                //GridPane.setMargin( new Insets(anchorPane, Insets.EMPTY.getTop()));
            }
            //GridPane.setMargin(anchorPane, new Insets(Insets.EMPTY.getRight()));
            //GridPane.setMargin(anchorPane, new Insets(Insets.EMPTY.getBottom()));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onMouseClicked() throws IOException {


    }



}
