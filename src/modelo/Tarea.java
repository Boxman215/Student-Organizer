package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Tarea {

    private String nombre;
    private String fechaEntrega;
    private String prioridad;
    private boolean completada;

    public Tarea(String nombre, String fechaEntrega, String prioridad) {
        setNombre(nombre);
        setFechaEntrega(fechaEntrega);
        setPrioridad(prioridad);
        completada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre es obligatorio."
            );
        }
        this.nombre = nombre.trim();
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        if (fechaEntrega == null || fechaEntrega.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "La fecha de entrega es obligatoria."
            );
        }
        try {
            LocalDate fecha = LocalDate.parse(fechaEntrega.trim());
            this.fechaEntrega = fecha.toString();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Ingresa una fecha válida con formato AAAA-MM-DD."
            );
        }
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        if (prioridad == null) {
            throw new IllegalArgumentException(
                "La prioridad es obligatoria."
            );
        }
        String valor = prioridad.trim();
        if (valor.equalsIgnoreCase("Alta")) {
            this.prioridad = "Alta";
        } else if (valor.equalsIgnoreCase("Media")) {
            this.prioridad = "Media";
        } else if (valor.equalsIgnoreCase("Baja")) {
            this.prioridad = "Baja";
        } else {
            throw new IllegalArgumentException(
                "La prioridad debe ser Alta, Media o Baja."
            );
        }
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public void marcarCompletada() {
        completada = true;
    }

    @Override
    public String toString() {
        return nombre
            + " | Fecha: " + fechaEntrega
            + " | Prioridad: " + prioridad
            + " | Estado: "
            + (completada ? "Completada" : "Pendiente");
    }
}
