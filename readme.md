# LiteraturaChallenge
Este proyecto utiliza la API de Gutendex para buscar libros, almacenar los resultados en una base de datos PostgreSQL y permitir consultas sobre los libros y sus autores.

## Descripción

El propósito de este proyecto es crear una aplicación que permita buscar libros a través de la API de [Gutendex](https://gutendex.com/), almacenar la información relevante en una base de datos y proporcionar servicios para consultar esta información. Esto incluye detalles sobre los libros, autores e idiomas.
La interaccion con el usuario es a través de la consola.

## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes:

- **Main**: Contiene la clase principal de la aplicación, se encarga de manejar la interaccion en la consola y
llamar a los metodos correspondientes a la opcion seleccionada.
- **models**: Contiene las clases de modelo que representan las entidades del dominio.
    - `Autor`
    - `Idioma`
    - `Libros`
- **repository**: Contiene las interfaces de repositorio para las entidades.
    - `AutorRepository`
    - `IdiomaRepository`
    - `LibroRepository`
- **service**: Contiene las clases de servicio que implementan la lógica de negocio.
    - `ConsumoAPI`
    - `ConvierteDatos`
    - `IdiomaService`
    - `LibrosService`
## Funcionalidades
   1. Consultar libros en la API
   2. Listar libros registrados
   3. Listar autores registrados
   4. Listar autores vivos en un determinado año
   5. Listar libros por idioma
   6. Buscar libros registrados por titulo
   7. Buscar autores registrados por nombre
   8. Listar 5 libros más descargados
   9. Mostrar estadísticas de libros registrados

## Tecnologías
  * Java 20
  * Spring Boot
  * PostgreSQL
  * Maven
  * JPA
  * Hibernate
  * Lombok
  * API de [Gutendex](https://gutendex.com/)
  * Postman

## Ejecución de la Aplicación

Para ejecutar la aplicación, sigue los siguientes pasos:
1. Clonar el repositorio.
2. Importar el proyecto en tu IDE de preferencia.
2. Configurar la base de datos PostgreSQL y actualizar el archivo `application.properties` con las credenciales de la base de datos.
3. Ejecutar el proyecto.
4. Seguir las instrucciones en la consola para interactuar con la aplicación.

## Codigo del menu de opciones
Las opciones se muestran asi:
![Menu de opciones](/images/menu.png)
1. Consultar libros en la API
```java
private void consultarLibrosAPI() throws IOException, InterruptedException {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = scanner.nextLine();

        var formateoNombre = nombreLibro.replace(" ", "+");
        String Busqueda = urlBase + "?search=" + formateoNombre;

       var respuesta = consultaApi.consultarApi(Busqueda);
        //System.out.println(respuesta);

        librosService.guardarLibros(respuesta,limite);


    }
```
Este metodo permite buscar libros en la API de Gutendex, se le solicita al usuario el nombre del libro que desea buscar,
se formatea el nombre para que pueda ser utilizado en la URL de la API, se realiza la consulta y se guarda la informacion
en la base de datos.
![Consulta de libros](/images/buscaLibrosApi.png)

2. Listar libros registrados
```java
private void listarLibrosRegistrados() {
        librosService.listarLibrosRegistrados();
    }
```
Este metodo permite listar los libros que se encuentran registrados en la base de datos.
![Listar libros registrados](/images/listarLibrosRegistrados.png)
3. Listar autores registrados
```java
 private void ListarAutoresRegistrados() {
        librosService.listarAutoresRegistrados();
    }
```
Este metodo permite listar los autores que se encuentran registrados en la base de datos.
![Listar autores registrados](/images/listarAutoresRegistrados.png)
4. Listar autores vivos en un determinado año
```java
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
```
Este metodo permite listar los autores que se encuentran vivos en un determinado año, se le solicita al usuario el año que desea buscar,
se valida que el año tenga exactamente 4 digitos y se realiza la consulta en la base de datos.
![Listar autores en determinado año](/images/listarAutoresEnDeterminadoAnio.png)

5. Listar libros por idioma
```java
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
```
Este metodo permite listar los libros por idioma, se listan los idiomas disponibles, se le solicita al usuario el numero del idioma que desea buscar,
se valida que la seleccion sea valida y se realiza la consulta en la base de datos.
![Listar libros por idioma](/images/listarLibrosPorIdioma.png)
6. Buscar libros registrados por titulo
```java
private void buscarLibrosRegistradosPorTitulo() {
        System.out.println("Escribe el título del libro que deseas buscar:");
        String titulo = scanner.nextLine();
        librosService.buscarLibrosPorTitulo(titulo);
    }
```
Este metodo permite buscar libros registrados por titulo, se le solicita al usuario el titulo del libro que desea buscar y se realiza la consulta en la base de datos.
![Buscar libros por titulo](/images/buscarLibrosPorTitulo.png)
7. Buscar autores registrados por nombre
```java
private void buscarAutoresRegistradosPorNombre() {
        System.out.println("Escribe el nombre del autor que deseas buscar:");
        String nombre = scanner.nextLine();
        librosService.buscarAutoresPorNombre(nombre);
    }
```
Este metodo permite buscar autores registrados por nombre, se le solicita al usuario el nombre del autor que desea buscar y se realiza la consulta en la base de datos.
![Buscar autores por nombre](/images/buscarAutoresPorNombre.png)
8. Listar 5 libros más descargados
```java
private void listarLibrosMasDescargados() {
        librosService.listarLibrosMasDescargados();
    }
```
Este metodo permite listar los 5 libros mas descargados, se realiza la consulta en la base de datos.
![Listar libros mas descargados](/images/listarLibrosMasDescargados.png)
9. Mostrar estadísticas de libros registrados
```java
private void mostrarEstadisticas() {
        librosService.mostrarEstadisticas();
    }
```
Este metodo permite mostrar las estadisticas de los libros registrados, se realiza la consulta en la base de datos.
![Mostrar estadisticas](/images/mostrarEstadisticas.png)
## Contribucion
- Si deseas contribuir a este proyecto, por favor crea un fork y envia un pull request.
## Agradecimientos
- Agradezco a [Gutendex](https://gutendex.com/) por proporcionar la API que permite buscar libros.
- Agradezco al Programa One de [Alura Latam](https://www.linkedin.com/company/alura-latam/) y [Oracle](https://www.linkedin.com/company/oracle/) por brindarme 
la oportunidad de aprender y mejorar mis habilidades en programación.
## Autor
- El proyecto fue desarrollado por [jorgeSanchez](https://github.com/jascmen)
- Sientete libre de contactarme en [LinkedIn](https://www.linkedin.com/in/jorge-anthony-sanchez-chavez/)
