# Student-Organizer

Proyecto semestral de Programación Orientada a Objetos.

## RF01 - Inicio de sesión

Esta rama contiene la primera versión del inicio de sesión. Permite revisar un usuario y una contraseña, guardar usuarios en un archivo y mostrar una pantalla sencilla en español.

## Cuenta de demostración

- Usuario: `demo`
- Contraseña: `demo123`

## Cómo ejecutar

Desde la carpeta principal del proyecto:

```powershell
New-Item -ItemType Directory -Force salida
javac -d salida src\Main.java src\modelo\Usuario.java src\util\Seguridad.java src\persistencia\PersistenciaDatos.java src\controlador\ControladorLogin.java src\vista\Vista.java
java -cp salida Main
```

## Cómo ejecutar las pruebas

```powershell
javac -d salida src\modelo\Usuario.java src\util\Seguridad.java src\persistencia\PersistenciaDatos.java src\controlador\ControladorLogin.java src\vista\Vista.java pruebas\PruebasUsuarios.java pruebas\PruebasLogin.java pruebas\PruebasVista.java
java -ea -cp salida PruebasUsuarios
java -ea -cp salida PruebasLogin
java -ea -cp salida PruebasVista
```

La información de los usuarios se guarda en `data\usuarios.csv`.
