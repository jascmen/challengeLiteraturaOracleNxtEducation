package com.alura.literaturaChallenge.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosConsulta(
        @JsonAlias("results") List<DatosLibros> libros
) {
}
