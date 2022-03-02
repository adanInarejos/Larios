package com.example.larios;

public class Ingrediente {
    int id;
    String nombre;

    public Ingrediente(int id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    public Ingrediente(){

    }

    public String getNombre() {
        return nombre;
    }
}
