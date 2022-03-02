package com.example.larios;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SubMenuAnadirController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList<FoodController> controladores = new ArrayList<>();

    static int contador = 0;

    Usuario currentUser;

    int camarero;

    String currentMenu;

    Mesa currenMesa;

    private DBManager manager = new DBManager();

    static List<Integer> ingredientes;
    public int categoria;

    @FXML
    GridPane grid;

    @FXML
    TextField buscador;

    @FXML
    Text nombre;

    public SubMenuAnadirController(int i, Usuario usuario, String menu, Mesa mesa, int cam) throws SQLException {
        this.categoria=i;
        this.currentUser=usuario;
        this.currentMenu=menu;
        this.currenMesa=mesa;
        this.camarero=cam;
    }

    public void setCurrenMesa(Mesa currenMesa) {
        this.currenMesa = currenMesa;
    }

    public void setCurrentUser(Usuario usuario){
        currentUser = usuario;
        nombre.setText(usuario.getNombre());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Plato> platos;
        int column = 0;
        int row = 1;

        try {
            platos = manager.getPlatos();
            for (int i = 0; i <platos.size() ; i++) {
                contador = i;
                ingredientes = manager.listaIngredintes(platos.get(i).getId());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("FoodItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                FoodController foodController = fxmlLoader.getController();
                controladores.add(foodController);
                foodController.setNombre(platos.get(i).getNombre());
                foodController.setCurrentUser(currentUser);
                foodController.setCurrentMenu(currentMenu);
                foodController.setCurrentMesa(currenMesa);
                for (int j = 0; j <ingredientes.size() ; j++) {
                    foodController.setIngredientes(manager.obtenerIngrediente(ingredientes.get(j)).getNombre());
                }

                Button anadir = new Button();
                anadir.setText("anadir");
                anadir.setUserData(i);
                anadir.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Button b = (Button) actionEvent.getSource();
                        currentMenu = currentMenu + platos.get((Integer) b.getUserData()).getNombre() + " " + platos.get((Integer) b.getUserData()).getPrecio() + ",\n";
                        try {
                            manager.insertarMensajes(camarero, currenMesa.getId(), currentMenu);
                            Logout();
                        } catch (SQLException | IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                if (column == 2){
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row );
                grid.add(anadir, column++, row);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(20, 20, 50, 20));
                GridPane.setMargin(anadir, new Insets(0, 0, 0, 350));
                ingredientes.clear();
            }
        } catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }

    public void actualizarMenu(String menu){
        for (int i = 0; i <controladores.size() ; i++) {
            FoodController controller = controladores.get(i);
            controller.setCurrentMenu(currentMenu);
        }
    }



    public void setCurrentMenu(String menu){
        currentMenu = menu;
    }

    public void enviarCocina() throws SQLException {
        List<String> pedido = manager.listarPedidosMesa(currenMesa.getId());
        if (pedido.size()>0){
            manager.actualizarPedido(currenMesa.getId(), currentMenu);
        } else {
            manager.insertarPedido(currentUser.getId(), currenMesa.getId(), currentMenu);
        }
        if (currenMesa.id_camarero == 0){
            manager.asignarMesa(currentUser.getId(), currenMesa.getId());
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Pedido enviado a cocina");
        alert.showAndWait();
    }

    public void verCocina() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CocinaView.fxml"));
        Parent root = loader.load();
        stage = (Stage) grid.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        CocinaController cocinaController = loader.getController();
        cocinaController.setCurrentMenu(currentMenu);
        cocinaController.setCurrentUser(currentUser);
    }

    public void resumen(){
        Alert resumen = new Alert(Alert.AlertType.INFORMATION);
        resumen.setHeaderText(currentMenu);
        resumen.showAndWait();
    }

    public void volver() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TablesView.fxml"));
        Parent root = loader.load();
        stage = (Stage) grid.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        TablesController tablesController = loader.getController();
        tablesController.setCurrentUser(currentUser);
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
