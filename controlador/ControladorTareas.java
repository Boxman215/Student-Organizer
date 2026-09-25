package controlador;

import modelo.Tarea;
import persistencia.PersistenciaDatos;

import java.util.ArrayList;
import java.util.List;

public class ControladorTareas {

    private List<Tarea> listaTareas;
    private final PersistenciaDatos persistencia;

    public ControladorTareas(PersistenciaDatos persistencia) {
        this.persistencia = persistencia;
        // Al iniciar, se cargan las tareas que ya estaban guardadas.
        this.listaTareas = new ArrayList<>(persistencia.leerTareas());
    }

    public void registrarTarea(String nombre, String fechaEntrega, String prioridad) {
        // El constructor de Tarea valida los datos.
        Tarea nuevaTarea = new Tarea(nombre, fechaEntrega, prioridad);
        listaTareas.add(nuevaTarea);
        persistencia.guardarTarea(nuevaTarea);
    }

    public List<Tarea> consultarPendientes() {
        List<Tarea> pendientes = new ArrayList<>();
        for (Tarea tarea : listaTareas) {
            if (!tarea.isCompletada()) {
                pendientes.add(tarea);
            }
        }
        return pendientes;
    }

    public void marcarCompletada(Tarea tarea) {
        if (tarea == null || !listaTareas.contains(tarea)) {
            throw new IllegalArgumentException(
                "La tarea no está registrada."
            );
        }
        tarea.marcarCompletada();
        persistencia.actualizarTarea(tarea);
    }

    public List<Tarea> getListaTareas() {
        // Devuelve una copia de la lista.
        return new ArrayList<>(listaTareas);
    }

    public void setListaTareas(List<Tarea> listaTareas) {
        if (listaTareas == null) {
            throw new IllegalArgumentException(
                "La lista no puede ser nula."
            );
        }
        for (Tarea tarea : listaTareas) {
            if (tarea == null) {
                throw new IllegalArgumentException(
                    "La lista no puede contener tareas nulas."
                );
            }
        }
        this.listaTareas = new ArrayList<>(listaTareas);
        persistencia.guardarTodas(this.listaTareas);
    }

    @Override
    public String toString() {
        return "Tareas registradas: " + listaTareas.size()
            + " | Pendientes: " + consultarPendientes().size();
    }
}
