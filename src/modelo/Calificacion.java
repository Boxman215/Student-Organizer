package modelo;

public class Calificacion {
    private String carneUsuario;
    private String curso;
    private double nota;
    private String comentario;

    public Calificacion(String carneUsuario, String curso, double nota, String comentario) {
        setCarneUsuario(carneUsuario);
        setCurso(curso);
        setNota(nota);
        setComentario(comentario);
    }

    public String getCarneUsuario() {
        return carneUsuario;
    }

    public void setCarneUsuario(String carneUsuario) {
        if (carneUsuario == null || carneUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El carné del usuario no puede estar vacío.");
        }

        this.carneUsuario = carneUsuario.trim();
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        if (curso == null || curso.trim().isEmpty()) {
            throw new IllegalArgumentException("El curso no puede estar vacío.");
        }

        this.curso = curso.trim();
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        // Se valida que la nota sea un número válido.
        // El rango de la nota todavía debe ser definido por el equipo.
        if (Double.isNaN(nota) || Double.isInfinite(nota)) {
            throw new IllegalArgumentException("La nota debe ser un número válido.");
        }

        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        // El comentario puede estar vacío.
        if (comentario == null) {
            this.comentario = "";
        } else {
            this.comentario = comentario.trim();
        }
    }

    @Override
    public String toString() {
        return "Curso: " + curso + " | Nota: " + nota + " | Comentario: " + comentario;
    }
}