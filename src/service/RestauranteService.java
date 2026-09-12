package service;

import model.Restaurante;
import model.Rol;
import model.Usuario;
import repository.RestauranteRepository;
import repository.UsuarioRepository;

import java.util.List;

public class RestauranteService {

    private RestauranteRepository restauranteRepository;
    private UsuarioRepository usuarioRepository;
    private Long contadorId = 1L;

    public RestauranteService(RestauranteRepository restauranteRepository, UsuarioRepository usuarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void registrarRestaurante(Usuario usuarioAutenticado, String nombre, String nit, String direccion, String telefono, String urlLogo, Long idPropietario) {

        if (usuarioAutenticado == null || usuarioAutenticado.getRol() != Rol.PROPIETARIO) {
            System.out.println("No tiene permisos para registrar un restaurante");
            return;
        }

        Usuario propietario = usuarioRepository.buscarUsuarioPorId(idPropietario);
        if (propietario == null || propietario.getRol() != Rol.PROPIETARIO) {
            System.out.println("El id asociado no corresponde a un usuario con rol Propietario");
            return;
        }

        if (!ValidarService.validarSoloNumeros(nit)) {
            System.out.println("El NIT debe ser estrictamente numérico");
            return;
        }

        if (!ValidarService.validarLongitudMaxima(telefono, 13)) {
            System.out.println("El teléfono no debe superar los 13 caracteres");
            return;
        }

        if (!ValidarService.validarNoSoloNumeros(nombre)) {
            System.out.println("El nombre no puede ser solo números");
            return;
        }

        Restaurante restaurante = new Restaurante(contadorId, nombre, nit, direccion, telefono, urlLogo, idPropietario);
        restauranteRepository.guardarRestaurante(restaurante);
        contadorId++;

        System.out.println("Restaurante registrado correctamente");
    }

    public Restaurante getRestaurante(Long id) {
        Restaurante restaurante = restauranteRepository.buscarRestaurantePorId(id);
        if (restaurante == null) {
            System.out.println("Restaurante no encontrado");
            return null;
        }
        return restaurante;
    }

    public List<Restaurante> getRestaurantes() {
        return restauranteRepository.getRestaurantes();
    }
}
