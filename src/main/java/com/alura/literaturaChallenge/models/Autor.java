package com.alura.literaturaChallenge.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "autores")
@Getter @Setter
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;

    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL)
    private List<Libros> libros;


    public Autor(DatosAutor d) {
        this.nombre = d.nombre();
        this.fechaNacimiento = d.fechaNacimiento();
        this.fechaMuerte = d.fechaMuerte();
    }

    public Autor() {

    }

    public Autor(String nombre, Integer integer, Integer integer1) {
        this.nombre = nombre;
        this.fechaNacimiento = integer;
        this.fechaMuerte = integer1;
    }

    @Override
    public String toString() {
        return
                "nombre='" + nombre + "\n" +
                "fechaNacimiento=" + fechaNacimiento + "\n" +
                "fechaMuerte=" + fechaMuerte;
    }


}
