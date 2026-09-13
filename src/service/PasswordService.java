package service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordService {

    public static String encriptarClave(String clave) {
        return BCrypt.hashpw(clave, BCrypt.gensalt());
    }

    public static boolean verificarClave(String clave, String claveEncriptada) {
        return BCrypt.checkpw(clave, claveEncriptada);
    }
}
