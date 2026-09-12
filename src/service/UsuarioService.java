package service;

import model.Rol;
import model.Usuario;
import repository.UsuarioRepository;

import java.util.List;

public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private Long contadorId = 1L;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void registrarPropietario(Usuario usuarioAutenticado, String nombre, String apellido, String documento, int edad, String celular, String correo, String clave) {

        if (usuarioAutenticado == null || usuarioAutenticado.getRol() != Rol.ADMINISTRADOR) {
            System.out.println("No tiene permisos para registrar un propietario");
            return;
        }

        if (!ValidarService.validarMayorDeEdad(edad)) {
            System.out.println("El usuario debe ser mayor de edad");
            return;
        }

        if (!ValidarService.validarEmail(correo)) {
            System.out.println("Correo inválido");
            return;
        }

        if (!ValidarService.validarSoloNumeros(documento)) {
            System.out.println("El documento debe ser numérico");
            return;
        }

        if (usuarioRepository.buscarUsuarioPorCorreo(correo) != null) {
            System.out.println("El correo ya está registrado");
            return;
        }

        String claveEncriptada = PasswordService.encriptarClave(clave);

        Usuario propietario = new Usuario(contadorId, nombre, apellido, documento, edad, celular, correo, claveEncriptada, Rol.PROPIETARIO);
        usuarioRepository.guardarUsuario(propietario);
        contadorId++;

        System.out.println("Propietario registrado correctamente");
    }

    public void registrarCliente(String nombre, String apellido, String documento, int edad, String celular, String correo, String clave) {

        if (!ValidarService.validarCampoObligatorio(nombre) || !ValidarService.validarCampoObligatorio(apellido)
                || !ValidarService.validarCampoObligatorio(documento) || !ValidarService.validarCampoObligatorio(celular)
                || !ValidarService.validarCampoObligatorio(correo) || !ValidarService.validarCampoObligatorio(clave)) {
            System.out.println("Todos los campos obligatorios deben estar presentes");
            return;
        }

        if (usuarioRepository.buscarUsuarioPorCorreo(correo) != null) {
            System.out.println("El correo ya está registrado");
            return;
        }

        String claveEncriptada = PasswordService.encriptarClave(clave);

        Usuario cliente = new Usuario(contadorId, nombre, apellido, documento, edad, celular, correo, claveEncriptada, Rol.CLIENTE);
        usuarioRepository.guardarUsuario(cliente);
        contadorId++;

        System.out.println("Cliente registrado correctamente");
    }

    public Usuario autenticar(String correo, String clave) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorCorreo(correo);
        if (usuario == null) {
            System.out.println("Credenciales inválidas");
            return null;
        }

        if (!PasswordService.verificarClave(clave, usuario.getClave())) {
            System.out.println("Credenciales inválidas");
            return null;
        }

        System.out.println("Autenticación exitosa");
        return usuario;
    }

    public Usuario getUsuario(Long id) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorId(id);
        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            return null;
        }
        return usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarioRepository.getUsuarios();
    }
}
