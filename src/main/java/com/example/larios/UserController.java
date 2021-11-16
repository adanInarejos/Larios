package com.example.larios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;

public class UserController  {

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

    public void ImageClick() throws IOException {
       // System.out.println(currenUser.getNombre());
    }







}
