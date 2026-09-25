package modelo;

import java.util.ArrayList;
import java.util.List;

public class GestorCalificaciones {
    private List<Calificacion> listaCalificaciones;

    public GestorCalificaciones() {
        listaCalificaciones = new ArrayList<>();
    }

    public void agregar(Calificacion calificacion) {
        if (calificacion != null) {
            listaCalificaciones.add(calificacion);
        }
    }

    public List<Calificacion> getListaCalificaciones() {
        return listaCalificaciones;
    }
}