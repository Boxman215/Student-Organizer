package modelo;


public class GestorPromedio extends GestorCalificaciones {

    public boolean estaVacio() {
        return getListaCalificaciones().isEmpty();
    }

    public int getCantidad() {
        return getListaCalificaciones().size();
    }


    public double calcularPromedio() {
        if (estaVacio()) {
            throw new IllegalStateException("No hay calificaciones registradas.");
        }

        double suma = 0;
        for (Calificacion calificacion : getListaCalificaciones()) {
            suma += calificacion.getNota();
        }

        return suma / getCantidad();
    }
}
