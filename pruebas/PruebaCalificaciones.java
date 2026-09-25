import controlador.ControladorCalificaciones;
import modelo.Calificacion;
import persistencia.PersistenciaDatos;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PruebaCalificaciones {

    public static void main(String[] args) throws Exception {
        System.out.println("--- PRUEBAS DE LOGICA Y PERSISTENCIA (RF03) ---");

        // Se crea un archivo temporal para no modificar el archivo real de calificaciones.
        Path archivoCalificaciones = Files.createTempFile(
                "calificaciones-prueba",
                ".csv"
        );

        PersistenciaDatos persistencia =
                PersistenciaDatos.paraPruebasCalificaciones(archivoCalificaciones);

        ControladorCalificaciones controlador =
                new ControladorCalificaciones(persistencia);

        // 1. Prueba curso vacío
        System.out.println("\nPrueba 1: Validar curso vacío");

        boolean exito1 = controlador.registrarCalificacion(
                "262140",
                "",
                85.0,
                "Prueba"
        );

        assert !exito1 : "No se debe registrar una calificación con curso vacío.";

        System.out.println("Resultado esperado: false");
        System.out.println("Resultado obtenido: " + exito1);


        // 2. Prueba carné vacío
        System.out.println("\nPrueba 2: Validar carné vacío");

        boolean exito2 = controlador.registrarCalificacion(
                "",
                "Programacion POO",
                85.0,
                "Prueba"
        );

        assert !exito2 : "No se debe registrar una calificación con carné vacío.";

        System.out.println("Resultado esperado: false");
        System.out.println("Resultado obtenido: " + exito2);


        // 3. Prueba guardar primera calificación válida
        System.out.println("\nPrueba 3: Guardar calificación válida 1");

        boolean exito3 = controlador.registrarCalificacion(
                "262140",
                "Programacion POO",
                90.0,
                "Proyecto avance"
        );

        assert exito3 : "La primera calificación válida debe registrarse.";

        System.out.println("Resultado esperado: true");
        System.out.println("Resultado obtenido: " + exito3);


        // 4. Prueba guardar segunda calificación
        System.out.println("\nPrueba 4: Guardar calificación válida 2");

        boolean exito4 = controlador.registrarCalificacion(
                "262140",
                "Matematica Discreta",
                78.5,
                "Parcial 1"
        );

        assert exito4 : "La segunda calificación válida debe registrarse.";

        System.out.println("Resultado esperado: true");
        System.out.println("Resultado obtenido: " + exito4);


        // 5. Prueba registrar una calificación para otro usuario
        System.out.println("\nPrueba 5: Guardar calificación de otro usuario");

        boolean exito5 = controlador.registrarCalificacion(
                "999999",
                "Fisica",
                88.0,
                "Laboratorio"
        );

        assert exito5 : "La calificación del segundo usuario debe registrarse.";

        System.out.println("Resultado esperado: true");
        System.out.println("Resultado obtenido: " + exito5);


        // 6. Leer de la persistencia para comprobar aislamiento por usuario
        System.out.println("\nPrueba 6: Comprobar aislamiento por usuario");

        List<Calificacion> misCalificaciones =
                controlador.obtenerCalificaciones("262140");

        List<Calificacion> otrasCalificaciones =
                controlador.obtenerCalificaciones("999999");

        assert misCalificaciones.size() == 2
                : "El usuario 262140 debe tener exactamente 2 calificaciones.";

        assert otrasCalificaciones.size() == 1
                : "El usuario 999999 debe tener exactamente 1 calificación.";

        for (Calificacion calificacion : misCalificaciones) {
            assert calificacion.getCarneUsuario().equals("262140")
                    : "No se deben recuperar calificaciones de otro usuario.";
        }

        System.out.println("Calificaciones del usuario 262140: " + misCalificaciones.size());
        System.out.println("Calificaciones del usuario 999999: " + otrasCalificaciones.size());


        // 7. Prueba persistencia después de reiniciar el controlador
        System.out.println("\nPrueba 7: Comprobar persistencia después de reiniciar");

        // Se crea otro controlador usando el mismo archivo.
        // Esto simula cerrar y volver a abrir la aplicación.
        ControladorCalificaciones controladorReiniciado =
                new ControladorCalificaciones(persistencia);

        List<Calificacion> calificacionesRecuperadas =
                controladorReiniciado.obtenerCalificaciones("262140");

        assert calificacionesRecuperadas.size() == 2
                : "Las calificaciones deben seguir guardadas después de reiniciar.";

        System.out.println(
                "Calificaciones recuperadas después de reiniciar: "
                        + calificacionesRecuperadas.size()
        );


        // 8. Prueba guardar una nueva calificación sin borrar las anteriores
        System.out.println("\nPrueba 8: Comprobar que una nueva calificación no borre las anteriores");

        boolean exito6 = controladorReiniciado.registrarCalificacion(
                "262140",
                "Algoritmos",
                95.0,
                "Proyecto final"
        );

        assert exito6 : "La tercera calificación debe registrarse.";

        List<Calificacion> calificacionesFinales =
                controladorReiniciado.obtenerCalificaciones("262140");

        assert calificacionesFinales.size() == 3
                : "Las calificaciones anteriores no deben eliminarse.";

        System.out.println(
                "Calificaciones finales del usuario 262140: "
                        + calificacionesFinales.size()
        );


        // 9. Mostrar las calificaciones finales
        System.out.println("\nPrueba 9: Mostrar calificaciones finales");

        for (Calificacion calificacion : calificacionesFinales) {
            System.out.println(" -> " + calificacion.toString());
        }


        // Se elimina el archivo temporal utilizado durante las pruebas.
        Files.deleteIfExists(archivoCalificaciones);

        System.out.println("\nPruebaCalificaciones: todas las pruebas pasaron.");
    }
}