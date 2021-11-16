package com.example.larios;

import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DBManager manager = new DBManager();

    @FXML
    private Button botonLogIn;

    @FXML
    private TextField campoTextoUsuario;

    @FXML
    private  TextField campoTextoContrasena;

    public LogInController() throws SQLException {
    }


    @FXML
    public void OnCLick(ActionEvent event) throws IOException, SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Larios", "root", "");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from Usuarios;");
        boolean alerta = true;
        //while (rs.next()){
        //    if (campoTextoUsuario.getText().equals(rs.getString("nombre")) && campoTextoContrasena.getText().equals(rs.getString("contrasena"))){
        //        Parent root = FXMLLoader.load(getClass().getResource("logInWins.fxml"));
        //        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //        scene = new Scene(root);
        //        stage.setScene(scene);
        //        stage.show();
        //        alerta=false;
        //    } else {

        //    }
        //}
        
        List<Usuario> usuarios = manager.getListaUsuarios();
        for (Usuario usuario : usuarios){
            if (campoTextoUsuario.getText().equals(usuario.getNombre()) && campoTextoContrasena.getText().equals(usuario.getContrasena())){
                        Parent root = FXMLLoader.load(getClass().getResource("logInWins.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        alerta=false;
                    } else {

                    }
        }
        if (alerta){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Inicio de sesion");
            alert.setContentText("Contrasena introducida no valida");
            alert.showAndWait();
        }



    }

}