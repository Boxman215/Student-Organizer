import controlador.ControladorLogin;
import vista.Vista;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Inicializa el flujo principal del programa arrancando con el Login
        SwingUtilities.invokeLater(() -> {
            ControladorLogin controladorLogin = new ControladorLogin();
            Vista vistaPrincipal = new Vista(controladorLogin);
            vistaPrincipal.mostrarLogin();
        });
    }
}