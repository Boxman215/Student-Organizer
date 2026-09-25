import controlador.ControladorTareas;
import modelo.Tarea;
import persistencia.PersistenciaDatos;

import java.nio.file.Files;
import java.nio.file.Path;

public class PruebasTareas {

    public static void main(String[] args) throws Exception {
        Path carpetaTemporal = Files.createTempDirectory("prueba-tareas");
        Path archivoTareas = carpetaTemporal.resolve("tareas.csv");

        // --- Pruebas de la clase Tarea 
        Tarea tarea = new Tarea("Diagrama de clases", "2026-10-19", "alta");
        assert tarea.getPrioridad().equals("Alta")
            : "La prioridad debe normalizarse a 'Alta'";
        assert !tarea.isCompletada()
            : "Una tarea nueva no debe estar completada";

        tarea.marcarCompletada();
        assert tarea.isCompletada()
            : "marcarCompletada debe cambiar el estado a completada";

        probarError(() -> new Tarea("", "2026-10-19", "Alta"), "nombre vacio");
        probarError(() -> new Tarea("Tarea", "19/10/2026", "Alta"), "fecha con formato invalido");
        probarError(() -> new Tarea("Tarea", "2026-10-19", "Urgente"), "prioridad invalida");

        // --- Pruebas de PersistenciaDatos + ControladorTareas
        PersistenciaDatos persistencia = PersistenciaDatos.paraPruebasTareas(archivoTareas);
        ControladorTareas controlador = new ControladorTareas(persistencia);

        controlador.registrarTarea("Leer capitulo 5", "2026-10-25", "Media");
        controlador.registrarTarea("Entregar avance", "2026-11-01", "Baja");

        assert controlador.getListaTareas().size() == 2
            : "Deben quedar 2 tareas registradas";
        assert Files.exists(archivoTareas)
            : "El archivo de tareas debe crearse al registrar una tarea";

        // "cerrar y volver a abrir" el programa
        ControladorTareas controladorReiniciado = new ControladorTareas(persistencia);
        assert controladorReiniciado.getListaTareas().size() == 2
            : "Las tareas deben persistir después de reiniciar el programa";

        Tarea primera = controladorReiniciado.getListaTareas().get(0);
        controladorReiniciado.marcarCompletada(primera);

        ControladorTareas controladorFinal = new ControladorTareas(persistencia);
        assert controladorFinal.consultarPendientes().size() == 1
            : "Debe quedar 1 tarea pendiente tras marcar una como completada y persistir";

        probarError(() -> controlador.marcarCompletada(null), "marcar completada una tarea nula");
        probarError(() -> controlador.setListaTareas(null), "lista nula en setListaTareas");

        Files.deleteIfExists(archivoTareas);
        Files.deleteIfExists(carpetaTemporal);

        System.out.println("PruebasTareas: todas las pruebas pasaron");
    }

    private static void probarError(Ejecutable accion, String caso) {
        try {
            accion.ejecutar();
            throw new AssertionError("Se esperaba un error en el caso: " + caso);
        } catch (IllegalArgumentException e) {
            System.out.println("OK (" + caso + "): " + e.getMessage());
        }
    }

    private interface Ejecutable {
        void ejecutar();
    }
}