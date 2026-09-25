import modelo.Usuario;
import persistencia.PersistenciaDatos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PruebasUsuarios {
    public static void main(String[] args) throws Exception {
        Path archivo = Files.createTempFile("usuarios", ".csv");

        PersistenciaDatos persistencia =
                PersistenciaDatos.paraPruebasUsuarios(archivo);

        Usuario usuario = new Usuario("demo", "demo123", "0000");

        persistencia.guardarUsuario(usuario);
        List<Usuario> usuarios = persistencia.leerUsuarios();

        assert usuarios.size() == 1 : "Debe recuperarse un usuario";

        assert usuarios.get(0).autenticar("demo", "demo123")
                : "La contraseña guardada debe permitir autenticarse";

        Files.deleteIfExists(archivo);

        System.out.println("PruebasUsuarios: todas las pruebas pasaron");
    }
}