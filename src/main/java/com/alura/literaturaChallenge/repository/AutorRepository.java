package com.alura.literaturaChallenge.repository;

import com.alura.literaturaChallenge.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AutorRepository  extends JpaRepository<Autor, Long> {
    //encuentra un autor por su nombre
    Autor findByNombre(String nombre);

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(int i, int i1);

    List<Autor> findByNombreContainingIgnoreCase(String trim);
}
