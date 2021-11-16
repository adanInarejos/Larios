package com.example.larios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios {

    Connection conn;

    List<Usuario> listaUsuarios = new ArrayList<>();

    public void cargarUsuarios() throws SQLException {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Larios", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from Usuarios;");
            while (rs.next()){
                listaUsuarios.add(new Usuario(rs.getInt("idPersona"), rs.getString("nombre"), rs.getString("primerApellido"), rs.getString("segundoApellido"), rs.getString("contrasena"), rs.getBoolean("administrador")));
            }
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
}
