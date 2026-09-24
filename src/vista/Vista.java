package vista;

import controlador.ControladorLogin;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class Vista {
    private final ControladorLogin controladorLogin;
    private JFrame ventana;

    public Vista(ControladorLogin controladorLogin) {
        this.controladorLogin = controladorLogin;
    }

    public void mostrarLogin() {
        SwingUtilities.invokeLater(() -> {
            ventana = new JFrame("Student Organizer - Inicio de sesión");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(360, 220);
            ventana.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            GridBagConstraints posicion = new GridBagConstraints();
            posicion.insets = new Insets(6, 6, 6, 6);
            posicion.fill = GridBagConstraints.HORIZONTAL;

            JTextField campoUsuario = new JTextField(16);
            JPasswordField campoContrasena = new JPasswordField(16);
            JButton botonIngresar = new JButton("Iniciar sesión");

            agregar(panel, new JLabel("Usuario:"), posicion, 0, 0);
            agregar(panel, campoUsuario, posicion, 1, 0);
            agregar(panel, new JLabel("Contraseña:"), posicion, 0, 1);
            agregar(panel, campoContrasena, posicion, 1, 1);
            agregar(panel, botonIngresar, posicion, 1, 2);

            botonIngresar.addActionListener(evento -> {
                String usuario = campoUsuario.getText();
                String contrasena = new String(campoContrasena.getPassword());
                boolean camposCompletos = !usuario.trim().isEmpty() && !contrasena.isEmpty();
                boolean accesoCorrecto = camposCompletos
                        && controladorLogin.iniciarSesion(usuario, contrasena);
                mostrarMensaje(mensajeDeResultado(camposCompletos, accesoCorrecto));
            });

            ventana.add(panel);
            ventana.setVisible(true);
        });
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(ventana, mensaje);
    }

    public static String mensajeDeResultado(boolean camposCompletos, boolean accesoCorrecto) {
        if (!camposCompletos) {
            return "Complete todos los campos";
        }
        if (accesoCorrecto) {
            return "Inicio de sesión correcto";
        }
        return "Usuario o contraseña incorrectos";
    }

    private void agregar(JPanel panel, java.awt.Component componente,
                         GridBagConstraints posicion, int columna, int fila) {
        posicion.gridx = columna;
        posicion.gridy = fila;
        panel.add(componente, posicion);
    }

    @Override
    public String toString() {
        return "Vista{" +
                "controladorLogin=" + controladorLogin +
                '}';
    }
}
