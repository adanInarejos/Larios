create database larios;

use larios;

select * from mesas;

insert into Usuarios(nombre, primerApellido, segundoApellido, contrasena, administrador) values ("Tofol", "Sastre", "Perez", "Cide2050", 1);

create table Usuarios (
	idPersona int(10) auto_increment,
    nombre varchar(255),
    primerApellido varchar(255),
    segundoApellido varchar(255),
    contrasena varchar(50),
    administrador boolean,
    PRIMARY KEY (idPersona) 
);

select * from Platos where nombre like "%brasa%";

insert into Usuarios(nombre, primerApellido, segundoApellido, contrasena, administrador) values ("Adan", "Inarejos", "Palma", "Cide2050", 0);
insert into Usuarios(nombre, primerApellido, segundoApellido, contrasena, administrador) values ("Ismael", "Martin", "Gonzalez", "Cide2050", 0);
insert into Usuarios(nombre, primerApellido, segundoApellido, contrasena, administrador) values ("Carlos", "Pomares", "Parpal", "Cide2050", 0);

create database biblioteca;

create table Libros(
id_libro int(10) primary key,
titulo varchar(50),
ejemplares int(10),
editorial int(10),
numero_paginas int(10), 
año_edición date
);

create table Socios (
id_socio int(10) primary key, 
nombre varchar(50),
apellidos varchar(50),
edad int(10), 
direccion varchar(50),
telefono int(20)
);

create table Prestamos (
id_libro int(10),
id_socio int(10),
fecha_inicio date,
fecha_fin date,
foreign key(id_libro) references Libros(id_libros),
foreign key(id_socio) references Socios (id_socios),
primary key(id_libro, id_socio)
);
