package com.example.larios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    Connection conn;

    int camarero;

    List<Usuario> listaUsuarios = new ArrayList<>();
    List<Mesa> listaMesas = new ArrayList<>();
    List<Plato> listaPlatos = new ArrayList<>();
    List<Plato> platos = new ArrayList<>();
    List<Integer> listaIngredientes = new ArrayList<>();
    List<String> pedidos = new ArrayList<>();
    List<String> mensajes = new ArrayList<>();
    List<String> mensajesAdmin = new ArrayList<>();

    Plato plato = new Plato();

    public DBManager() throws SQLException {
        this.getConnection();
    }

    public void getConnection() throws SQLException {
        // Cambiar usuario y contrasena
        conn = DriverManager.getConnection("jdbc:mysql://localhost/larios", "root", "Cide2050");
    }

    public List<Usuario> getListaUsuarios() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from Usuarios;");
        while (rs.next()){
            listaUsuarios.add(new Usuario(rs.getInt("idPersona"), rs.getString("nombre"), rs.getString("primerApellido"), rs.getString("segundoApellido"), rs.getString("contrasena"), rs.getBoolean("administrador")));
        }
        return listaUsuarios;
    }

    public List<Mesa> getListaMesas() throws SQLException{
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * from mesas order by id_mesa;");
        while (rs.next()){
            listaMesas.add(new Mesa(rs.getInt("id_mesa"), rs.getInt("id_camarero")));
        }
        return listaMesas;
    }

    public List<Plato> getListaPlatos(int categoria) throws SQLException{
        Statement stmt = conn.createStatement();;
        ResultSet rs = stmt.executeQuery("Select * from Platos where id_categoria=" + categoria);
        while (rs.next()){
            listaPlatos.add(new Plato(rs.getInt("id_plato"), rs.getString("nombre"), rs.getInt("id_categoria"),rs.getInt("id_tipo"), rs.getFloat("precio") ));
        }
        return listaPlatos;
    }

    public void insertarMensajes(int camarero, int mesa, String mensaje) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("Insert into mensajes(id_camarero, id_mesa, mensaje) values ( " + camarero +", "+  mesa +",'" + mensaje +"')" );
    }

    public List<String> listarMensaje(int camarero, int mesa) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("Select mensaje from mensajes where id_camarero=" + camarero + " and id_mesa =" + mesa);
        while (rs.next()){
            mensajes.add(rs.getString("mensaje"));
        }
        return mensajes;
    }

    public List<String> listarMensajeAdmin() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("Select mensaje from mensajesAdmin");
        while (rs.next()){
            mensajesAdmin.add(rs.getString("mensaje"));
        }
        return mensajesAdmin;
    }

    public void InsertarMensajeAdmin(String mensaje) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("Insert into mensajesAdmin(mensaje) values ( '" + mensaje + "')");
    }

    public void eliminarMensajeAdmin() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("Delete from mensajesAdmin where id>0");
    }

    public void eliminarMensaje(int camarero, int mesa) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("Delete from mensajes where id_camarero =" + camarero + " and id_mesa= " + mesa);
    }

    public int getCamareroMesa(int mesa) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("Select id_camarero from mesas where id_mesa= " + mesa );
        while (rs.next()){
            camarero = rs.getInt("id_camarero");
        }
        return camarero;
    }

    public List<Plato> getPlatos() throws SQLException {
        Statement stmt = conn.createStatement();;
        ResultSet rs = stmt.executeQuery("Select * from Platos");
        while (rs.next()){
            platos.add(new Plato(rs.getInt("id_plato"), rs.getString("nombre"), rs.getInt("id_categoria"),rs.getInt("id_tipo"), rs.getFloat("precio") ));
        }
        return platos;
    }

    public Plato getPlato(String nombre) throws SQLException {
        Statement stmt = conn.createStatement();;
        ResultSet rs = stmt.executeQuery("Select * from Platos where nombre='" + nombre + "'");
        while (rs.next()){
            plato = new Plato(rs.getInt("id_plato"), rs.getString("nombre"), rs.getInt("id_categoria"), rs.getInt("id_tipo"), rs.getFloat("precio"));
        }
        return plato;
    }


    public List<Plato> getBusquedaPlatos(String clave) throws SQLException{
        Statement stmt = conn.createStatement();;
        ResultSet rs = stmt.executeQuery("Select * from Platos where nombre like '%" + clave + "%'" );
        while (rs.next()){
            listaPlatos.add(new Plato(rs.getInt("id_plato"), rs.getString("nombre"), rs.getInt("id_categoria"),rs.getInt("id_tipo"), rs.getFloat("precio") ));
        }
        return listaPlatos;
    }

    public void insertarUsuario(String nombre, String primerApellido, String segundoApellido, String contrasena) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("Insert into Usuarios(nombre, primerApellido, segundoApellido, contrasena, administrador) values ('" + nombre + "','" + primerApellido + "','" + segundoApellido + "','" + contrasena + "'," + 0 + ")");
    }

    public void asignarMesa(int idCamamrero, int idMesa) throws SQLException {
        Statement stm = conn.createStatement();
        stm.executeUpdate("Update mesas set id_camarero = " + idCamamrero  + " where id_mesa = " + idMesa);
    }

    public List<Integer> listaIngredintes(int plato) throws SQLException {
        Statement stmt = conn.createStatement();;
        ResultSet rs = stmt.executeQuery("Select * from ingredientes_platos where id_plato=" + plato);
        while (rs.next()){
            listaIngredientes.add(rs.getInt("id_ingrediente"));
        }
        return listaIngredientes;
    }


    public Ingrediente obtenerIngrediente(int ingrediente) throws SQLException {
        Ingrediente ingrediente1 = new Ingrediente();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("Select * from ingredientes where id_ingrediente=" + ingrediente);
        while (rs.next()){
            ingrediente1 = new Ingrediente(rs.getInt("id_ingrediente"), rs.getString("nombre"));
        }
        return ingrediente1;
    }

    public void insertarPedido(int camarero, int mesa, String pedido) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("Insert into pedidos(id_camarero, id_mesa, pedido) values (" + camarero + "," + mesa +  " ,'" + pedido + "')");
    }

    public List<String> listarPedidos() throws SQLException {
        Statement stmt = conn.createStatement();;
        ResultSet rs = stmt.executeQuery("Select pedido from pedidos");
        while (rs.next()){
            pedidos.add(rs.getString("pedido"));
        }
        return pedidos;
    }

    public List<String> listarPedidosMesa(int idMesa) throws SQLException {
        Statement stmt = conn.createStatement();;
        ResultSet rs = stmt.executeQuery("Select pedido from pedidos where id_mesa=" + idMesa);
        while (rs.next()){
            pedidos.add(rs.getString("pedido"));
        }
        return pedidos;
    }

    public void actualizarPedido(int mesa, String pedido) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE pedidos set pedido = '" + pedido + "' where id_mesa = " + mesa);
    }

    public void finalizarPedido(int mesa) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("Delete from pedidos where id_mesa = " + mesa);
        stmt.executeUpdate("UPDATE mesas set id_camarero = null where id_mesa =" + mesa);
    }

}

