import vista.Vista;

public class PruebasVista {
    public static void main(String[] args) {
        assert Vista.mensajeDeResultado(false, false)
                .equals("Complete todos los campos")
                : "Debe avisar cuando faltan datos";
        assert Vista.mensajeDeResultado(true, false)
                .equals("Usuario o contraseña incorrectos")
                : "Debe avisar cuando las credenciales no coinciden";
        assert Vista.mensajeDeResultado(true, true)
                .equals("Inicio de sesión correcto")
                : "Debe confirmar el acceso correcto";

        System.out.println("PruebasVista: todas las pruebas pasaron");
    }
}
