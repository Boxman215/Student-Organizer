package persistencia;

import modelo.Tarea;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaDatos {

    private Path rutaTareas;

    public PersistenciaDatos(Path rutaTareas) {
        this.rutaTareas = rutaTareas;
    }

    public Path getRutaTareas() {
        return rutaTareas;
    }

    public void setRutaTareas(Path rutaTareas) {
        this.rutaTareas = rutaTareas;
    }

    public List<Tarea> leerTareas() {
        List<Tarea> tareas = new ArrayList<>();

        if (!Files.exists(rutaTareas)) {
            return tareas;
        }

        try (BufferedReader lector = Files.newBufferedReader(rutaTareas)) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(";", -1);
                if (partes.length < 4) {
                    continue;
                }
                try {
                    Tarea tarea = new Tarea(partes[0], partes[1], partes[2]);
                    if (Boolean.parseBoolean(partes[3])) {
                        tarea.marcarCompletada();
                    }
                    tareas.add(tarea);
                } catch (IllegalArgumentException e) {
                    System.out.println("Linea invalida en tareas.csv, se omite: " + linea);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el archivo de tareas.", e);
        }

        return tareas;
    }

    public void guardarTarea(Tarea tarea) {
        try {
            crearCarpetaSiNoExiste();
            try (FileWriter escritor = new FileWriter(rutaTareas.toFile(), true)) {
                escritor.write(formatearLinea(tarea));
                escritor.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar la tarea.", e);
        }
    }

    public void actualizarTarea(Tarea tarea) {
        List<Tarea> tareas = leerTareas();
        for (int i = 0; i < tareas.size(); i++) {
            Tarea actual = tareas.get(i);
            if (actual.getNombre().equals(tarea.getNombre())
                    && actual.getFechaEntrega().equals(tarea.getFechaEntrega())) {
                tareas.set(i, tarea);
            }
        }
        guardarTodas(tareas);
    }

    public void guardarTodas(List<Tarea> tareas) {
        try {
            crearCarpetaSiNoExiste();
            try (FileWriter escritor = new FileWriter(rutaTareas.toFile(), false)) {
                for (Tarea tarea : tareas) {
                    escritor.write(formatearLinea(tarea));
                    escritor.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudieron guardar las tareas.", e);
        }
    }

    private void crearCarpetaSiNoExiste() throws IOException {
        if (rutaTareas.getParent() != null) {
            Files.createDirectories(rutaTareas.getParent());
        }
    }

    private String formatearLinea(Tarea tarea) {
        return tarea.getNombre() + ";"
             + tarea.getFechaEntrega() + ";"
             + tarea.getPrioridad() + ";"
             + tarea.isCompletada();
    }

    @Override
    public String toString() {
        return "PersistenciaDatos [rutaTareas=" + rutaTareas + "]";
    }
}
