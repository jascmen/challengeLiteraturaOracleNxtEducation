package com.alura.literaturaChallenge.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "idioma")
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "idioma",cascade = CascadeType.ALL)
    private Set<Libros> libros;
    public Idioma(String idioma) {
        this.nombre = idioma;
    }

    public Idioma() {

    }

    @Override
    public String toString() {
        return nombre;
    }
}
