package com.alura.literaturaChallenge.repository;

import com.alura.literaturaChallenge.models.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    Idioma findByNombre(String nombre);


}
