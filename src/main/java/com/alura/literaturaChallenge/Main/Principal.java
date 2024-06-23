package com.alura.literaturaChallenge.Main;

import com.alura.literaturaChallenge.models.Idioma;
import com.alura.literaturaChallenge.service.ConsumoAPI;
import com.alura.literaturaChallenge.service.IdiomaService;
import com.alura.literaturaChallenge.service.LibrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


@Component
public class Principal {

    Scanner scanner = new Scanner(System.in);
    ConsumoAPI consultaApi = new ConsumoAPI();

    @Autowired
    private LibrosService librosService;

    @Autowired
    private IdiomaService idiomaService;

    int limite = 3;

    private String urlBase = "https://gutendex.com/books";
    public void mostrarMenu() throws IOException, InterruptedException {
        boolean continuar = true;

        while (continuar) {
            System.out.println("Bienvenido a la aplicación de literatura");
            var menu = """
                    1. Consultar libros en la API
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    6. Buscar libros registrados por titulo 
                    7. Buscar autores registrados por nombre
                    8. Listar 5 libros más descargados
                    9. Mostrar estadísticas de libros registrados
                    
                    0. Salir
                    
                    Ingrese la opcion deseada:
                    """;
            System.out.println(menu);

            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> consultarLibrosAPI();
                    case 2 -> listarLibrosRegistrados();
                    case 3 -> ListarAutoresRegistrados();
                    case 4 -> listarAutoresEnDeterminadoAnio();
                    case 5 -> listarLibrosPorIdioma();
                    case 6-> buscarLibrosRegistradosPorTitulo();
                    case 7->  buscarAutoresRegistradosPorNombre();
                    case 8 -> listarLibrosMasDescargados();
                    case 9-> mostrarEstadisticasDeLibrosRegistrados();
                    case 0 -> {
                        System.out.println("Saliendo de la aplicación...");
                        continuar = false;
                    }
                    default -> System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un número válido.");
            }
        }
    }



    private void consultarLibrosAPI() throws IOException, InterruptedException {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = scanner.nextLine();

        var formateoNombre = nombreLibro.replace(" ", "+");
        String Busqueda = urlBase + "?search=" + formateoNombre;

       var respuesta = consultaApi.consultarApi(Busqueda);
        //System.out.println(respuesta);

        librosService.guardarLibros(respuesta,limite);


    }

    private void listarLibrosRegistrados() {
        librosService.listarLibrosRegistrados();
    }

    private void ListarAutoresRegistrados() {
        librosService.listarAutoresRegistrados();
    }

    private void listarAutoresEnDeterminadoAnio() {
        System.out.println("Escribe el año que deseas buscar:");
        String anio;
        while (true) {
            anio = scanner.nextLine();
            if (anio.matches("\\d{4}")) { // Validar que el año tenga exactamente 4 dígitos
                break;
            } else {
                System.out.println("Por favor, ingresa un año válido (4 dígitos, por ejemplo: 1879, 1998).");
            }
        }

        librosService.listarAutoresVivosEnDeterminadoAnio(anio);
    }

    private void listarLibrosPorIdioma() {
        idiomaService.listarIdiomas();
        System.out.println("Ingrese el número del idioma deseado:");
        int seleccion = Integer.parseInt(scanner.nextLine());

        List<Idioma> idiomas = idiomaService.obtenerIdiomas();
        if (seleccion > 0 && seleccion <= idiomas.size()) {
            Idioma idiomaSeleccionado = idiomas.get(seleccion - 1);
            librosService.listarLibrosPorIdioma(idiomaSeleccionado);
        } else {
            System.out.println("Selección inválida.");
        }


    }

    private void buscarLibrosRegistradosPorTitulo() {
        System.out.println("Escribe el título del libro que deseas buscar:");
        String titulo = scanner.nextLine();
        librosService.buscarLibrosPorTitulo(titulo);
    }

    private void buscarAutoresRegistradosPorNombre() {
        System.out.println("Escribe el nombre del autor que deseas buscar:");
        String nombre = scanner.nextLine();
        librosService.buscarAutoresPorNombre(nombre);
    }

    private void listarLibrosMasDescargados() {
        librosService.listarLibrosMasDescargados();
    }

    private void mostrarEstadisticasDeLibrosRegistrados() {
        librosService.mostrarEstadisticas();
    }


}