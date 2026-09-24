import controlador.ControladorLogin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PruebasLogin {
    public static void main(String[] args) throws Exception {
        Path archivoUsuarios = Paths.get("data", "usuarios.csv");
        Files.deleteIfExists(archivoUsuarios);

        ControladorLogin controlador = new ControladorLogin();

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
        System.out.println("PruebasLogin: todas las pruebas pasaron");
    }
}
