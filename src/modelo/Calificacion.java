package modelo;

public class Calificacion {
    private String carneUsuario;
    private String curso;
    private double nota;
    private String comentario;

    public Calificacion(String carneUsuario, String curso, double nota, String comentario) {
        this.carneUsuario = carneUsuario;
        this.curso = curso;
        this.nota = nota;
        this.comentario = comentario;
    }

    public String getCarneUsuario() {
        return carneUsuario;
    }

    public void setCarneUsuario(String carneUsuario) {
        this.carneUsuario = carneUsuario;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Curso: " + curso + " | Nota: " + nota + " | Comentario: " + comentario;
    }
}