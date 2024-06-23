package com.alura.literaturaChallenge.repository;

import com.alura.literaturaChallenge.models.Autor;
import com.alura.literaturaChallenge.models.Idioma;
import com.alura.literaturaChallenge.models.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libros, Long> {

    //encontrar un libro por su titulo
    Libros findByTitulo(String titulo);

    List<Libros> findByIdioma(Idioma idiomaSeleccionado);

   List <Libros> findByTituloContainingIgnoreCase(String trim);

    List<Libros> findTop5ByOrderByDescargasDesc();

    long countByAutor(Autor autor);

    long countByIdioma(Idioma idioma);
}
