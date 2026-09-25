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
        if (persistencia == null) {
            throw new IllegalArgumentException("La persistencia no puede ser nula.");
        }

        this.gestor = new GestorCalificaciones();
        this.persistencia = persistencia;
    }

    public boolean registrarCalificacion(String carneUsuario, String curso, double nota, String comentario) {
        try {
            // La clase Calificacion se encarga de validar los datos básicos.
            Calificacion nuevaCalificacion = new Calificacion(
                    carneUsuario,
                    curso,
                    nota,
                    comentario
            );

            // Primero se guarda la calificación en la persistencia.
            persistencia.guardarCalificacion(nuevaCalificacion);

            // Si se pudo guardar correctamente, también se agrega al gestor.
            gestor.agregar(nuevaCalificacion);

            return true;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: Datos inválidos. " + e.getMessage());
            return false;

        } catch (IllegalStateException e) {
            System.out.println("Error: No se pudo guardar en la persistencia. " + e.getMessage());
            return false;
        }
    }

    public List<Calificacion> obtenerCalificaciones(String carneUsuario) {
        // Se llama a través del atributo persistencia.
        return persistencia.leerCalificacionesPorUsuario(carneUsuario);
    }
}