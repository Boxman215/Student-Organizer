import controlador.ControladorPromedio;
import modelo.Calificacion;
import modelo.GestorPromedio;
import persistencia.PersistenciaDatos;

import java.nio.file.Files;
import java.nio.file.Path;

public class PruebasPromedio {

    public static void main(String[] args) throws Exception {
       
        GestorPromedio vacio = new GestorPromedio();
        assert vacio.estaVacio() : "Un gestor nuevo debe estar vacío";
        try {
            vacio.calcularPromedio();
            throw new AssertionError("Debe fallar si no hay calificaciones");
        } catch (IllegalStateException e) {
            System.out.println("OK (sin calificaciones): " + e.getMessage());
        }

    
        GestorPromedio uno = new GestorPromedio();
        uno.agregar(new Calificacion("0000", "Fisica", 75.0, ""));
        assert uno.calcularPromedio() == 75.0 : "Con una nota el promedio es esa nota";

       
        GestorPromedio varios = new GestorPromedio();
        varios.agregar(new Calificacion("0000", "POO", 90.0, ""));
        varios.agregar(new Calificacion("0000", "Discreta", 78.5, ""));
        varios.agregar(new Calificacion("0000", "Algoritmos", 95.0, ""));
        assert Math.abs(varios.calcularPromedio() - 87.8333) < 0.001 : "Promedio incorrecto";

        
        Path archivo = Files.createTempFile("promedio-prueba", ".csv");
        PersistenciaDatos persistencia = PersistenciaDatos.paraPruebasCalificaciones(archivo);
        ControladorPromedio controlador = new ControladorPromedio(persistencia);

        assert !controlador.tieneCalificaciones("262140") : "Al inicio no hay calificaciones";
        try {
            controlador.calcularPromedio("262140");
            throw new AssertionError("Debe fallar si el usuario no tiene calificaciones");
        } catch (IllegalStateException e) {
            System.out.println("OK (usuario sin calificaciones): " + e.getMessage());
        }

        controlador.registrarCalificacion("262140", "POO", 80.0, "");
        controlador.registrarCalificacion("262140", "Calculo", 90.0, "");
        controlador.registrarCalificacion("999999", "Fisica", 10.0, "");

        assert controlador.calcularPromedio("262140") == 85.0
                : "El promedio debe ser 85 y no incluir notas de otro usuario";


        ControladorPromedio reiniciado = new ControladorPromedio(persistencia);
        assert reiniciado.calcularPromedio("262140") == 85.0 : "El promedio debe persistir";

        Files.deleteIfExists(archivo);
        System.out.println("PruebasPromedio: todas las pruebas pasaron");
    }
}
