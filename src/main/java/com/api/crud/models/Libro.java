package com.api.crud.models;

import jakarta.persistence.*;

@Entity
public class Libro {
@Id
@GeneratedValue (strategy = GenerationType.IDENTITY)
private Long id;

private String nombrelibro;

@Lob
private String titulo;

@Lob
private String capitulos;


private String contenido;

private String caracteristicas;

@Lob

private String prefacio;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombrelibro() {
        return nombrelibro;
    }

    public void setNombrelibro(String nombrelibro) {
        this.nombrelibro = nombrelibro;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getPrefacio() {
        return prefacio;
    }

    public void setPrefacio(String prefacio) {
        this.prefacio = prefacio;
    }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(String capitulos) {
        this.capitulos = capitulos;
    }
}



