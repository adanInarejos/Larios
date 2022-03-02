package com.example.larios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    String currentMenu;

    Usuario currentUser;

    Mesa currentMesa;

    DBManager manager = new DBManager();

    @FXML
    Button botonEntrantes;

    @FXML
    Text nombre;

    Boolean nuevo = true;

    public MainMenuController(Mesa mesa, Usuario usuario) throws SQLException {
        this.currentMesa=mesa;
        this.currentUser=usuario;
    }



    public void ampliarSubMenuEntrantes() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SubMenu.fxml"));
        loader.setControllerFactory(type -> {
            if (type == SubMenuController.class) {
                try {
                    return new SubMenuController(1, currentUser, currentMenu, currentMesa);
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
        stage = (Stage) botonEntrantes.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        SubMenuController subMenuController = loader.getController();
        subMenuController.setCurrentUser(currentUser);
        //subMenuController.setCurrentMenu(currentMenu);
        //subMenuController.setCurrenMesa(currentMesa);
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentMesa(Mesa currentMesa) {
        this.currentMesa = currentMesa;
    }

    public void setCurrentUser(Usuario usuario){
        currentUser = usuario;
        nombre.setText(currentUser.getNombre());
    }

    public void setCurrentMenu(String menu){
        currentMenu = menu;
    }

    public void ampliarSubMenuPrincipales() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SubMenu.fxml"));
        loader.setControllerFactory(type -> {
            if (type == SubMenuController.class) {
                try {
                    return new SubMenuController(2, currentUser, currentMenu, currentMesa);
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
        stage = (Stage) botonEntrantes.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        SubMenuController subMenuController = loader.getController();
        subMenuController.setCurrentUser(currentUser);
        //subMenuController.setCurrentMenu(currentMenu);
        //subMenuController.setCurrenMesa(currentMesa);
    }

    public void ampliarSubMenuPostres() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SubMenu.fxml"));
        loader.setControllerFactory(type -> {
            if (type == SubMenuController.class) {
                try {
                    return new SubMenuController(3, currentUser, currentMenu, currentMesa);
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
        stage = (Stage) botonEntrantes.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        SubMenuController subMenuController = loader.getController();
        subMenuController.setCurrentUser(currentUser);
        //subMenuController.setCurrentMenu(currentMenu);
        //subMenuController.setCurrenMesa(currentMesa);
    }

    public void ampliarSubMenuBebidas() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SubMenu.fxml"));
        loader.setControllerFactory(type -> {
            if (type == SubMenuController.class) {
                try {
                    return new SubMenuController(4, currentUser, currentMenu, currentMesa);
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
        stage = (Stage) botonEntrantes.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        SubMenuController subMenuController = loader.getController();
        subMenuController.setCurrentUser(currentUser);
        //subMenuController.setCurrentMenu(currentMenu);
        //subMenuController.setCurrenMesa(currentMesa);
    }

    public void Logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersView.fxml"));
        Parent root = loader.load();
        stage = (Stage) botonEntrantes.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public boolean comprobarBD(){
        return true;
    }

    public void setNuevo(){
        currentMenu = "Mesa: " + currentMesa.getId() + "\n";
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (currentMenu == null){
                List<String> menu = manager.listarPedidosMesa(currentMesa.getId());
                if (menu.size()>0){
                    currentMenu = menu.get(0);
                } else {
                    setNuevo();
                }
            }

            List<String> mensajes = manager.listarMensaje(currentUser.getId(), currentMesa.getId());
            if (mensajes.size()>0){
                for (int i = 0; i < mensajes.size() ; i++) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Nuevo Mensaje");
                    alert.setHeaderText("Otro camarero quiere anadir el siguente producto a tu comanda:\n" + mensajes.get(i));
                    //alert.showAndWait();
                    Optional<ButtonType> action = alert.showAndWait();
                    if (action.get() == ButtonType.OK){
                        currentMenu = currentMenu + mensajes.get(i);
                        manager.eliminarMensaje(currentUser.getId(), currentMesa.getId());
                    } else {
                        manager.eliminarMensaje(currentUser.getId(), currentMesa.getId());
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
