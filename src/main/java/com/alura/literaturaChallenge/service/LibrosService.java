package com.alura.literaturaChallenge.service;

import com.alura.literaturaChallenge.models.*;
import com.alura.literaturaChallenge.repository.AutorRepository;
import com.alura.literaturaChallenge.repository.IdiomaRepository;
import com.alura.literaturaChallenge.repository.LibroRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibrosService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository librosRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    private final ConvierteDatos convierteDatos = new ConvierteDatos();

    @Transactional
    public void guardarLibros(String respuesta, int limite) {
        DatosConsulta datosConsulta = convierteDatos.obtenerDatos(respuesta, DatosConsulta.class);

        if (datosConsulta.libros() != null && !datosConsulta.libros().isEmpty()) {
            List<Libros> librosList = datosConsulta.libros().stream()
                    .limit(limite)
                    .map(libroDTO -> {
                        Libros existingLibro = librosRepository.findByTitulo(libroDTO.titulo().toUpperCase().trim());
                        if (existingLibro != null) {
                            System.out.println("El libro '" + libroDTO.titulo() + "' ya está registrado.");
                            return null;
                        }

                        Libros libro = new Libros(libroDTO);
                        libro.setAutor(obtenerGuardarAutor(libro.getAutor()));
                        libro.setIdioma(obtenerGuardarIdioma(libro.getIdioma()));

                        return libro;
                    })
                    .filter(libro -> libro != null)
                    .collect(Collectors.toList());

            librosRepository.saveAll(librosList);
            librosList.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros.");
        }
    }

    private Autor obtenerGuardarAutor(Autor autor) {
        if (autor != null) {
            String nombreAutorNormalizado = autor.getNombre().toUpperCase();
            Autor existingAutor = autorRepository.findByNombre(nombreAutorNormalizado);
            if (existingAutor != null) {
                return existingAutor;
            } else {
                autor.setNombre(nombreAutorNormalizado);
                return autorRepository.save(autor);
            }
        }
        return null;
    }

    private Idioma obtenerGuardarIdioma(Idioma idioma) {
        if (idioma != null) {
            String nombreIdiomaNormalizado = idioma.getNombre().toUpperCase();
            Idioma existingIdioma = idiomaRepository.findByNombre(nombreIdiomaNormalizado);
            if (existingIdioma != null) {
                return existingIdioma;
            } else {
                idioma.setNombre(nombreIdiomaNormalizado);
                return idiomaRepository.save(idioma);
            }
        }
        return null;
    }

    public void listarLibrosRegistrados() {
        List<Libros> libros = librosRepository.findAll();
        imprimirTablaLibros(libros, "No hay libros registrados.");
    }

    public void listarLibrosPorIdioma(Idioma idiomaSeleccionado) {
        List<Libros> libros = librosRepository.findByIdioma(idiomaSeleccionado);
        imprimirTablaLibros(libros, "No hay libros registrados en el idioma " + idiomaSeleccionado.getNombre() + ".");
    }

    public void buscarLibrosPorTitulo(String titulo) {
        List<Libros> libros = librosRepository.findByTituloContainingIgnoreCase(titulo.toUpperCase().trim());
        imprimirTablaLibros(libros, "No se encontraron libros con el título '" + titulo + "'.");
    }

    private void imprimirTablaLibros(List<Libros> libros, String mensajeVacio) {
        if (libros.isEmpty()) {
            System.out.println(mensajeVacio);
        } else {
            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow("ID", "Título", "Autor", "Idioma", "Descargas");
            at.addRule();

            for (Libros libro : libros) {
                at.addRow(
                        libro.getId(),
                        libro.getTitulo(),
                        libro.getAutor() != null ? libro.getAutor().getNombre() : "N/A",
                        libro.getIdioma() != null ? libro.getIdioma().getNombre() : "N/A",
                        libro.getDescargas() != null ? libro.getDescargas() : 0
                );
                at.addRule();
            }

            at.getContext().setGrid(A7_Grids.minusBarPlusEquals());
            String rend = at.render();
            System.out.println(rend);
        }
    }

    public void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        imprimirTablaAutores(autores, "No hay autores registrados.");
    }

    public void listarAutoresVivosEnDeterminadoAnio(String anio) {
        List<Autor> autores = autorRepository.findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(Integer.parseInt(anio), Integer.parseInt(anio));
        imprimirTablaAutores(autores, "No hay autores vivos en el año " + anio + ".");
    }

    private void imprimirTablaAutores(List<Autor> autores, String mensajeVacio) {
        if (autores.isEmpty()) {
            System.out.println(mensajeVacio);
        } else {
            AsciiTable at = new AsciiTable();
            at.addRule();
            at.addRow("ID", "Nombre", "Fecha de nacimiento", "Fecha de muerte");
            at.addRule();

            for (Autor autor : autores) {
                at.addRow(
                        autor.getId(),
                        autor.getNombre(),
                        autor.getFechaNacimiento() != null ? autor.getFechaNacimiento().toString() : "N/A",
                        autor.getFechaMuerte() != null ? autor.getFechaMuerte().toString() : "N/A"
                );
                at.addRule();
            }

            at.getContext().setGrid(A7_Grids.minusBarPlusEquals());
            String rend = at.render();
            System.out.println(rend);
        }
    }

    public void buscarAutoresPorNombre(String nombre) {
        List<Autor> autores = autorRepository.findByNombreContainingIgnoreCase(nombre.trim());
        imprimirTablaAutores(autores, "No se encontraron autores con el nombre '" + nombre + "'.");
    }

    public void listarLibrosMasDescargados() {
        List<Libros> libros = librosRepository.findTop5ByOrderByDescargasDesc();
        imprimirTablaLibros(libros, "No se encontraron libros.");
    }

    public void mostrarEstadisticas() {
        Long cantidadLibros = librosRepository.count();
        Long cantidadAutores = autorRepository.count();
        Long cantidadIdiomas = idiomaRepository.count();
        Long totalDescargas = librosRepository.findAll().stream().mapToLong(Libros::getDescargas).sum();
        Autor autorConMasLibros = obtenerAutorConMasLibros();
        Idioma idiomaConMasLibros = obtenerIdiomaConMasLibros();
        Optional<Libros> libroConMasDescargas = obtenerLibroConMasDescargas();
        Optional<Libros> libroConMenosDescargas = obtenerLibroConMenosDescargas();

        System.out.println("Estadísticas:");
        System.out.println("Cantidad de libros registrados: " + cantidadLibros);
        System.out.println("Cantidad de autores registrados: " + cantidadAutores);
        System.out.println("Cantidad de idiomas registrados: " + cantidadIdiomas);
        System.out.println("Total de descargas de libros: " + totalDescargas);
        System.out.println("Autor con más libros registrados: " + (autorConMasLibros != null ? autorConMasLibros.getNombre() : "N/A"));
        System.out.println("Idioma con más libros registrados: " + (idiomaConMasLibros != null ? idiomaConMasLibros.getNombre() : "N/A"));
        System.out.println("Libro con más descargas: " + (libroConMasDescargas.isPresent() ? libroConMasDescargas.get().getTitulo() : "N/A"));
        System.out.println("Libro con menos descargas: " + (libroConMenosDescargas.isPresent() ? libroConMenosDescargas.get().getTitulo() : "N/A"));
        System.out.println("\n");
    }

    private Autor obtenerAutorConMasLibros() {
        List<Autor> autores = autorRepository.findAll();
        return autores.stream()
                .max(Comparator.comparingLong(autor -> librosRepository.countByAutor(autor)))
                .orElse(null);
    }

    private Idioma obtenerIdiomaConMasLibros() {
        List<Idioma> idiomas = idiomaRepository.findAll();
        return idiomas.stream()
                .max(Comparator.comparingLong(idioma -> librosRepository.countByIdioma(idioma)))
                .orElse(null);
    }

    private Optional<Libros> obtenerLibroConMasDescargas() {
        return librosRepository.findAll().stream()
                .max(Comparator.comparingLong(Libros::getDescargas));
    }

    private Optional<Libros> obtenerLibroConMenosDescargas() {
        return librosRepository.findAll().stream()
                .min(Comparator.comparingLong(Libros::getDescargas));
    }
}