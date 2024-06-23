# LiteraturaChallenge

Este proyecto utiliza la API de Gutendex para buscar libros, almacenar los resultados en una base de datos PostgreSQL y permitir consultas sobre los libros y sus autores.

## Descripción

El propósito de este proyecto es crear una aplicación que permita buscar libros a través de la API de Gutendex, almacenar la información relevante en una base de datos y proporcionar servicios para consultar esta información. Esto incluye detalles sobre los libros, autores e idiomas.

## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes:

- **Main**: Contiene la clase principal de la aplicación.
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

## Clases Principales

### Principal.java

Es la clase principal que inicia la aplicación.

### Autor.java

Representa a un autor con sus atributos y métodos correspondientes.

### Idioma.java

Representa un idioma con sus atributos y métodos correspondientes.

### Libros.java

Representa un libro con sus atributos y métodos correspondientes.

### ConsumoAPI.java

Se encarga de consumir la API de Gutendex y obtener los datos necesarios.

### ConvierteDatos.java

Convierte los datos obtenidos de la API en un formato adecuado para almacenarlos en la base de datos.

### IdiomaService.java

Maneja la lógica relacionada con los idiomas.

### LibrosService.java

Maneja la lógica relacionada con los libros.

## Configuración

Para configurar y ejecutar el proyecto, sigue los siguientes pasos:

1. Clonar el repositorio.
2. Configurar la base de datos PostgreSQL y actualizar el archivo `application.properties` con las credenciales de la base de datos.
3. Ejecutar la aplicación principal `Principal.java`.

