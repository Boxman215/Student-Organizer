package controlador;

import persistencia.PersistenciaDatos;
import vista.VistaTareas;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        Path rutaTareas = Paths.get("data", "tareas.csv");

        PersistenciaDatos persistencia = new PersistenciaDatos(rutaTareas);
        ControladorTareas controlador = new ControladorTareas(persistencia);

        SwingUtilities.invokeLater(() -> {
            VistaTareas vista = new VistaTareas(controlador);
            vista.setVisible(true);
        });
    }
}
