package modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorCalificaciones {
    private List<Calificacion> listaCalificaciones;

    public GestorCalificaciones() {
        listaCalificaciones = new ArrayList<>();
    }

    public void agregar(Calificacion calificacion) {
        if (calificacion == null) {
            throw new IllegalArgumentException("La calificación no puede ser nula.");
        }

        listaCalificaciones.add(calificacion);
    }

    public List<Calificacion> getListaCalificaciones() {
        // Se devuelve una copia para proteger la lista original.
        return new ArrayList<>(listaCalificaciones);
    }
}