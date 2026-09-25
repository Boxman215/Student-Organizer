package controlador;

import modelo.Calificacion;
import modelo.GestorCalificaciones;
import persistencia.PersistenciaDatos;
import java.util.List;

public class ControladorCalificaciones {
    private GestorCalificaciones gestor;
    private PersistenciaDatos persistencia;

    // Constructor base #1 
    public ControladorCalificaciones() {
        this(new PersistenciaDatos());
    }

    // Constructor que nos permite tener una instancia de PersistenciaDatos
    public ControladorCalificaciones(PersistenciaDatos persistencia) {
        this.gestor = new GestorCalificaciones();
        this.persistencia = persistencia;
    }

    public boolean registrarCalificacion(String carneUsuario, String curso, double nota, String comentario) {
        if (carneUsuario == null || carneUsuario.trim().isEmpty()) {
            System.out.println("Error: El usuario/carné no puede estar vacío.");
            return false;
        }

        if (curso == null || curso.trim().isEmpty()) {
            System.out.println("Error: El curso no puede estar vacío.");
            return false;
        }

        Calificacion nuevaCalificacion = new Calificacion(carneUsuario.trim(), curso.trim(), nota, comentario);

        try {
            // Se usa el atributo persistencia (de instancia) en vez de la llamada estática
            persistencia.guardarCalificacion(nuevaCalificacion);
            gestor.agregar(nuevaCalificacion);
            return true;
        } catch (Exception e) {
            System.out.println("Error: No se pudo guardar en la persistencia. " + e.getMessage());
            return false;
        }
    }

    public List<Calificacion> obtenerCalificaciones(String carneUsuario) {
        // Se llama a través del atributo persistencia
        return persistencia.leerCalificacionesPorUsuario(carneUsuario);
    }
}