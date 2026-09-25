import controlador.ControladorLogin;
import vista.Vista;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new Vista(new ControladorLogin()).mostrarLogin()
        );
    }
}
