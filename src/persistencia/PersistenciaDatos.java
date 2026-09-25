package persistencia;

import modelo.Calificacion;
import modelo.Tarea;
import modelo.Usuario;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaDatos {
    private final Path rutaUsuarios;
    private Path rutaTareas;
    private final Path rutaCalificaciones;





// --- CONSTRUCTORES ---

public PersistenciaDatos() {
    this(
        Paths.get("data", "usuarios.csv"),
        Paths.get("data", "tareas.csv"),
        Paths.get("data", "calificaciones.csv")
    );
}

//Constructor completo. Permite especificar independientemente las rutas de usuarios, tareas y calificaciones.
 
public PersistenciaDatos(
        Path rutaUsuarios,
        Path rutaTareas,
        Path rutaCalificaciones) {

    if (rutaUsuarios == null) {
        throw new IllegalArgumentException(
            "La ruta de usuarios no puede ser nula."
        );
    }

    if (rutaTareas == null) {
        throw new IllegalArgumentException(
            "La ruta de tareas no puede ser nula."
        );
    }

    if (rutaCalificaciones == null) {
        throw new IllegalArgumentException(
            "La ruta de calificaciones no puede ser nula."
        );
    }

    this.rutaUsuarios = rutaUsuarios;
    this.rutaTareas = rutaTareas;
    this.rutaCalificaciones = rutaCalificaciones;
}

//Crea una persistencia utilizando un archivo específico para pruebas de usuarios.
 
public static PersistenciaDatos paraPruebasUsuarios(Path rutaUsuarios) {
    return new PersistenciaDatos(
        rutaUsuarios,
        Paths.get("data", "tareas.csv"),
        Paths.get("data", "calificaciones.csv")
    );
}

// Crea una persistencia utilizando un archivo específico para pruebas de tareas.
 
public static PersistenciaDatos paraPruebasTareas(Path rutaTareas) {
    return new PersistenciaDatos(
        Paths.get("data", "usuarios.csv"),
        rutaTareas,
        Paths.get("data", "calificaciones.csv")
    );
}


 // Crea una persistencia utilizando un archivo específico para pruebas de calificaciones.
 
public static PersistenciaDatos paraPruebasCalificaciones(
        Path rutaCalificaciones) {

    return new PersistenciaDatos(
        Paths.get("data", "usuarios.csv"),
        Paths.get("data", "tareas.csv"),
        rutaCalificaciones
    );
}

    // --- MÉTODOS DE USUARIOS (ANDREH / PROYECTO) ---

    public List<Usuario> leerUsuarios() {
        prepararArchivo(rutaUsuarios);
        List<Usuario> usuarios = new ArrayList<>();

        try {
            for (String linea : Files.readAllLines(rutaUsuarios, StandardCharsets.UTF_8)) {
                String[] partes = linea.split(";", -1);
                if (partes.length == 3 && !partes[0].trim().isEmpty()) {
                    usuarios.add(Usuario.desdeArchivo(partes[0], partes[1], partes[2]));
                }
            }
            return usuarios;
        } catch (IOException error) {
            throw new IllegalStateException("No se pudieron leer los usuarios", error);
        }
    }

    public void guardarUsuario(Usuario usuario) {
        prepararArchivo(rutaUsuarios);
        String linea = usuario.getNombreUsuario() + ";"
                + usuario.getContrasena() + ";"
                + usuario.getCarne() + System.lineSeparator();

        try {
            Files.writeString(rutaUsuarios, linea, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException error) {
            throw new IllegalStateException("No se pudo guardar el usuario", error);
        }
    }

    // --- MÉTODOS DE TAREAS (LUIS) ---

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
            prepararArchivo(rutaTareas);
            try (FileWriter escritor = new FileWriter(rutaTareas.toFile(), true)) {
                escritor.write(formatearLineaTarea(tarea));
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
        guardarTodasLasTareas(tareas);
    }

    public void guardarTodasLasTareas(List<Tarea> tareas) {
        try {
            prepararArchivo(rutaTareas);
            try (FileWriter escritor = new FileWriter(rutaTareas.toFile(), false)) {
                for (Tarea tarea : tareas) {
                    escritor.write(formatearLineaTarea(tarea));
                    escritor.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudieron guardar las tareas.", e);
        }
    }

    private String formatearLineaTarea(Tarea tarea) {
        return tarea.getNombre() + ";"
             + tarea.getFechaEntrega() + ";"
             + tarea.getPrioridad() + ";"
             + tarea.isCompletada();

    }
    public void guardarTodas(List<Tarea> tareas) {
        guardarTodasLasTareas(tareas);
    }


    // --- MÉTODOS DE CALIFICACIONES (RF03 - FABRICIO) ---

    public void guardarCalificacion(Calificacion calificacion) {
        if (calificacion == null) {
            throw new IllegalArgumentException("La calificación no puede ser nula.");
        }

        prepararArchivo(rutaCalificaciones);

        String linea = calificacion.getCarneUsuario() + ";"
                + calificacion.getCurso() + ";"
                + calificacion.getNota() + ";"
                + calificacion.getComentario() + System.lineSeparator();

        try {
            Files.writeString(
                    rutaCalificaciones,
                    linea,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException error) {
            throw new IllegalStateException("No se pudo guardar la calificación", error);
        }
    }

    public List<Calificacion> leerCalificacionesPorUsuario(String carneUsuario) {
        prepararArchivo(rutaCalificaciones);
        List<Calificacion> calificaciones = new ArrayList<>();

        if (carneUsuario == null || carneUsuario.trim().isEmpty()) {
            return calificaciones;
        }

        try {
            for (String linea : Files.readAllLines(rutaCalificaciones, StandardCharsets.UTF_8)) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split(";", -1);

                if (partes.length < 4) {
                    System.out.println("Linea invalida en calificaciones.csv, se omite: " + linea);
                    continue;
                }

                try {
                    String carneEnArchivo = partes[0].trim();

                    // Solo se recuperan las calificaciones del usuario solicitado.
                    if (carneEnArchivo.equalsIgnoreCase(carneUsuario.trim())) {
                        String curso = partes[1].trim();
                        double nota = Double.parseDouble(partes[2].trim());
                        String comentario = partes[3].trim();

                        Calificacion calificacion = new Calificacion(
                                carneEnArchivo,
                                curso,
                                nota,
                                comentario
                        );

                        calificaciones.add(calificacion);
                    }

                } catch (IllegalArgumentException error) {
                    // Si una línea tiene información inválida se omite,
                    // pero las demás calificaciones se siguen leyendo.
                    System.out.println("Linea invalida en calificaciones.csv, se omite: " + linea);
                }
            }

            return calificaciones;

        } catch (IOException error) {
            throw new IllegalStateException("No se pudieron leer las calificaciones", error);
        }
    }
    // --- MÉTODO AUXILIAR GENERALIZADO ---

    private void prepararArchivo(Path ruta) {
        try {
            Path carpeta = ruta.getParent();
            if (carpeta != null) {
                Files.createDirectories(carpeta);
            }
            if (Files.notExists(ruta)) {
                Files.createFile(ruta);
            }
        } catch (IOException error) {
            throw new IllegalStateException("No se pudo preparar el archivo: " + ruta, error);
        }
    }
}