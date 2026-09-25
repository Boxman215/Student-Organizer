# RF01 - Inicio de sesión

## Descripción

Mi requisito consiste en permitir que una persona entre al sistema usando un nombre de usuario y una contraseña. Si los datos son correctos, el programa guarda quién inició sesión. Si los datos son incorrectos, muestra un mensaje y no permite el acceso.

## Prioridad

**Prioridad 1:** el inicio de sesión es importante porque controla el acceso al sistema y será la entrada para las demás funciones del proyecto.

## Datos que se utilizan

- Nombre de usuario.
- Contraseña.
- Carné del estudiante.

La contraseña se guarda como un código y no se muestra directamente en el archivo de usuarios.

## Clases utilizadas

| Clase | Para qué sirve |
|---|---|
| `Usuario` | Guarda los datos de cada usuario y revisa sus credenciales. |
| `PersistenciaDatos` | Guarda y lee los usuarios desde `data/usuarios.csv`. |
| `Seguridad` | Convierte la contraseña a un código antes de guardarla. |
| `ControladorLogin` | Revisa el usuario y la contraseña y guarda la sesión actual. |
| `Vista` | Muestra la pantalla, recibe los datos y enseña los mensajes. |
| `Main` | Inicia el programa. |

## Funcionamiento

1. La persona escribe su usuario y contraseña.
2. La pantalla revisa que no estén vacíos.
3. `ControladorLogin` busca el usuario guardado.
4. Se compara la contraseña ingresada.
5. Si coincide, se inicia la sesión.
6. Si no coincide, se muestra un mensaje de error.

## Tareas de mi requisito

| Tarea | Descripción | Horas estimadas | Responsable | Fecha probable |
|---|---|---:|---|---|
| Crear la clase `Usuario` | Agregar atributos, constructor, métodos y validación de credenciales. | 2 | Yo | 24/09/2026 |
| Guardar usuarios | Crear el archivo y las funciones para guardar y leer usuarios. | 2 | Yo | 24/09/2026 |
| Crear el controlador | Conectar la búsqueda de usuarios con el inicio y cierre de sesión. | 2 | Yo | 24/09/2026 |
| Crear la pantalla | Agregar campos, botón y mensajes en español. | 2 | Yo | 24/09/2026 |
| Probar el Login | Probar accesos correctos, incorrectos y campos vacíos. | 1 | Yo | 24/09/2026 |
| Integrar con el proyecto | Revisar que la estructura pueda conectarse con las otras funciones. | 1 | Yo | 24/09/2026 |

## Pruebas realizadas

- Inicio de sesión con la cuenta de demostración.
- Usuario inexistente.
- Contraseña incorrecta.
- Usuario vacío.
- Contraseña vacía.
- Guardado y lectura de usuarios.
- Cierre de sesión.
- Mensajes mostrados por la pantalla.

## Cuenta de demostración

- Usuario: `demo`
- Contraseña: `demo123`

## Registro de mis horas

| Fecha | Inicio | Fin | Interrupción | Tiempo trabajado | Tarea | Comentarios |
|---|---|---|---:|---:|---|---|
|  |  |  |  |  |  |  |
|  |  |  |  |  |  |  |
|  |  |  |  |  |  |  |

## Mis contribuciones

| Fecha | Descripción del aporte | Enlace o commit |
|---|---|---|
|  |  |  |
|  |  |  |
|  |  |  |
|  |  |  |

## Muestra a usuarios

| Caso mostrado | Comentario recibido | Cambio realizado |
|---|---|---|
| Inicio de sesión correcto |  |  |
| Contraseña incorrecta |  |  |
| Campos vacíos |  |  |

## Pendientes de integración

- Conectar el usuario actual con la pantalla principal del sistema.
- Agregar la opción visible de cerrar sesión cuando se integren las demás pantallas.
- Revisar la pantalla final junto con las funciones de tareas y calificaciones.
