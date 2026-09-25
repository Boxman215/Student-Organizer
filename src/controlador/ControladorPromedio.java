package controlador;

import modelo.Calificacion;
import modelo.GestorPromedio;
import persistencia.PersistenciaDatos;

public class ControladorPromedio extends ControladorCalificaciones {

    public ControladorPromedio() {
        super();
    }

    public ControladorPromedio(PersistenciaDatos persistencia) {
        super(persistencia);
    }

    public double calcularPromedio(String carneUsuario) {
        GestorPromedio gestor = new GestorPromedio();

        for (Calificacion calificacion : obtenerCalificaciones(carneUsuario)) {
            gestor.agregar(calificacion);
        }

        return gestor.calcularPromedio();
    }

    public boolean tieneCalificaciones(String carneUsuario) {
        return !obtenerCalificaciones(carneUsuario).isEmpty();
    }
}
