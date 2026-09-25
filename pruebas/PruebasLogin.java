import controlador.ControladorLogin;
import persistencia.PersistenciaDatos;

import java.nio.file.Files;
import java.nio.file.Path;

public class PruebasLogin {
    public static void main(String[] args) throws Exception {
        Path carpetaTemporal = Files.createTempDirectory("prueba-login");
        Path archivoUsuarios = carpetaTemporal.resolve("usuarios.csv");

        ControladorLogin controlador = new ControladorLogin(
                new PersistenciaDatos(archivoUsuarios));

        assert !controlador.iniciarSesion("", "")
                : "No se debe iniciar sesión con campos vacíos";
        assert !controlador.iniciarSesion("noExiste", "123")
                : "No se debe iniciar sesión con un usuario inexistente";
        assert !controlador.iniciarSesion("demo", "incorrecta")
                : "No se debe iniciar sesión con una contraseña incorrecta";
        assert controlador.iniciarSesion("demo", "demo123")
                : "Debe iniciar sesión con la cuenta de demostración";
        assert controlador.getUsuarioActual() != null
                : "Debe guardarse el usuario actual";

        controlador.cerrarSesion();
        assert controlador.getUsuarioActual() == null
                : "Cerrar sesión debe borrar el usuario actual";

        Files.deleteIfExists(archivoUsuarios);
        Files.deleteIfExists(carpetaTemporal);
        System.out.println("PruebasLogin: todas las pruebas pasaron");
    }
}
