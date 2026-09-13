package service;

public class ValidarService {

    public static boolean validarEmail(String email) {
        if (email == null) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean validarSoloNumeros(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        return texto.matches("\\d+");
    }

    public static boolean validarMayorDeEdad(int edad) {
        return edad >= 18;
    }

    public static boolean validarNoSoloNumeros(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }
        return !texto.matches("\\d+");
    }

    public static boolean validarLongitudMaxima(String texto, int longitudMaxima) {
        if (texto == null) {
            return false;
        }
        return texto.length() <= longitudMaxima;
    }

    public static boolean validarCampoObligatorio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean validarPrecioMayorACero(double precio) {
        return precio > 0;
    }
}
