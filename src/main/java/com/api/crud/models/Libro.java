package com.api.crud.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del libro es obligatorio")
    @Size(max = 200, message = "Máximo 200 caracteres")
    private String nombrelibro;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    private String rutaPortada;

    @Lob
    private String titulo;

    @Lob
    private String capitulos;

    private String contenido;

    private String caracteristicas;

    @Lob
    private String prefacio;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombrelibro() { return nombrelibro; }
    public void setNombrelibro(String nombrelibro) { this.nombrelibro = nombrelibro; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getRutaPortada() { return rutaPortada; }
    public void setRutaPortada(String rutaPortada) { this.rutaPortada = rutaPortada; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCapitulos() { return capitulos; }
    public void setCapitulos(String capitulos) { this.capitulos = capitulos; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getCaracteristicas() { return caracteristicas; }
    public void setCaracteristicas(String caracteristicas) { this.caracteristicas = caracteristicas; }

    public String getPrefacio() { return prefacio; }
    public void setPrefacio(String prefacio) { this.prefacio = prefacio; }
}