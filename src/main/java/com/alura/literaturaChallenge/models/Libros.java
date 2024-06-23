package com.alura.literaturaChallenge.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "libros")
@Getter @Setter
public class Libros {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;


    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ManyToOne
    @JoinColumn(name="idioma_id")
    private Idioma idioma;

    private Integer descargas;

    public Libros(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo().toUpperCase();
        this.autor =  new Autor( datosLibros.autores().get(0));
        this.idioma = new Idioma(datosLibros.idiomas().get(0).getNombre());
        //verificar si tiene datos en descargas
        if(datosLibros.descargas() !=null)
            this.descargas = datosLibros.descargas() ;
        else
            this.descargas = 0;
    }

    public Libros() {

    }


    @Override
    public String toString() {
        return  "LIBRO: \n " +
                "Titulo='" + titulo + "',\n" +
                "Autor: \n" + autor + "\n" +
                "Idioma=" + idioma + "\n" +
                "Descargas=" + descargas + "\n" +
                "\n";
    }


}
