package com.example.larios;

public class Usuario {
    int id;
    String nombre;
    String apellido1;
    String apellido2;
    String contrasena;
    boolean administrador;

    public Usuario(int id, String nombre, String apellido1, String apellido2, String contrasena, boolean administrador){
        this.id= id;
        this.nombre=nombre;
        this.apellido1=apellido1;
        this.apellido2=apellido2;
        this.contrasena=contrasena;
        this.administrador=administrador;
    }

    // Setters y Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
}
