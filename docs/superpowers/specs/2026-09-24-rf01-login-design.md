# RF01 - Diseño del inicio de sesión

## Objetivo

Crear un inicio de sesión sencillo y funcional con usuario y contraseña para Student Organizer, siguiendo el diagrama UML existente y separando correctamente la información, las acciones y la pantalla.

## Qué se va a incluir

Este trabajo incluirá únicamente RF01:

- La clase `Usuario`, con `nombreUsuario`, `contrasena` y `carne`.
- Guardado de usuarios en un archivo mediante `PersistenciaDatos`.
- `ControladorLogin` para revisar las credenciales y mantener la sesión iniciada.
- Una pantalla sencilla de inicio de sesión en `Vista`.
- Revisión de datos y pruebas del funcionamiento.
- Documento de requisitos y prioridades, con espacios para agregar mis horas y contribuciones reales.

No se harán todavía las funciones de calificaciones, tareas ni promedio general. Esas funciones podrán usar el inicio de sesión después.

## Cómo funcionará

La clase `Usuario` representará a cada persona que puede entrar al sistema. `PersistenciaDatos` leerá y guardará los usuarios en el archivo `data/usuarios.csv`. Si la carpeta o el archivo no existen, se crearán automáticamente.

Las contraseñas no se guardarán directamente, sino como un código seguro. La pantalla solo recibirá los datos y mostrará mensajes; la revisión de las credenciales estará en `ControladorLogin`.

Para poder demostrar el funcionamiento, si el archivo está vacío se agregará una cuenta de prueba que no pertenece a ninguna persona: `demo` / `demo123`. El archivo README explicará cómo cambiarla por datos de prueba propios.

## Revisiones y errores

- Si el usuario está vacío o solo tiene espacios, se rechazará.
- Si la contraseña está vacía, se rechazará.
- Si el usuario no existe o la contraseña es incorrecta, no se iniciará sesión.
- Si los datos son correctos, se guardará el usuario en `usuarioActual`.
- Si el archivo de usuarios no existe, se creará cuando sea posible y se mostrará un mensaje si ocurre algún problema.

## Pruebas

Se probarán los campos vacíos, los usuarios inexistentes, las contraseñas incorrectas, el inicio correcto, el cierre de sesión y la recuperación de datos después de volver a abrir la aplicación. También se abrirá la pantalla para comprobar manualmente los casos correctos e incorrectos.

## Espacios para completar

El documento tendrá espacios para agregar mis fechas reales, horas trabajadas, interrupciones, enlaces de mis contribuciones y comentarios de los usuarios. Estos datos los completaré con la información real del trabajo realizado.
