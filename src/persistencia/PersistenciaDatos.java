package persistencia;

import modelo.Calificacion;
import modelo.Usuario;

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
    private final Path rutaCalificaciones;

    public PersistenciaDatos() {
        this(Paths.get("data", "usuarios.csv"), Paths.get("data", "calificaciones.csv"));
    }

    public PersistenciaDatos(Path rutaUsuarios) {
        this(rutaUsuarios, Paths.get("data", "calificaciones.csv"));
    }

    public PersistenciaDatos(Path rutaUsuarios, Path rutaCalificaciones) {
        this.rutaUsuarios = rutaUsuarios;
        this.rutaCalificaciones = rutaCalificaciones;
    }

    // --- MÉTODOS DE USUARIOS 

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

    // --- MÉTODOS PARA RF03 (CALIFICACIONES) 

    public void guardarCalificacion(Calificacion calificacion) {
        if (calificacion == null) return;

        prepararArchivo(rutaCalificaciones);
        String linea = calificacion.getCarneUsuario() + ";"
                + calificacion.getCurso() + ";"
                + calificacion.getNota() + ";"
                + calificacion.getComentario() + System.lineSeparator();

        try {
            Files.writeString(rutaCalificaciones, linea, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
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
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(";", -1);
                if (partes.length >= 4) {
                    String carneEnArchivo = partes[0].trim();
                    if (carneEnArchivo.equalsIgnoreCase(carneUsuario.trim())) {
                        String curso = partes[1].trim();
                        double nota = Double.parseDouble(partes[2].trim());
                        String comentario = partes[3].trim();

                        calificaciones.add(new Calificacion(carneEnArchivo, curso, nota, comentario));
                    }
                }
            }
            return calificaciones;
        } catch (IOException error) {
            throw new IllegalStateException("No se pudieron leer las calificaciones", error);
        }
    }

    // GENERALIZADO ---

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