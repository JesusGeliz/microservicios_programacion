package service;

import model.Plato;
import model.Restaurante;
import model.Rol;
import model.Usuario;
import repository.PlatoRepository;
import repository.RestauranteRepository;

import java.util.List;

public class PlatoService {

    private PlatoRepository platoRepository;
    private RestauranteRepository restauranteRepository;
    private Long contadorId = 1L;

    public PlatoService(PlatoRepository platoRepository, RestauranteRepository restauranteRepository) {
        this.platoRepository = platoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public void crearPlato(Usuario usuarioAutenticado, String nombre, double precio, String descripcion, String categoria, String urlImagen, Long idRestaurante) {

        if (usuarioAutenticado == null || usuarioAutenticado.getRol() != Rol.PROPIETARIO) {
            System.out.println("No tiene permisos para crear un plato");
            return;
        }

        if (!ValidarService.validarCampoObligatorio(nombre) || !ValidarService.validarCampoObligatorio(descripcion) || !ValidarService.validarCampoObligatorio(categoria)) {
            System.out.println("Todos los campos obligatorios deben estar presentes");
            return;
        }

        if (!ValidarService.validarPrecioMayorACero(precio)) {
            System.out.println("El precio debe ser mayor a cero");
            return;
        }

        Restaurante restaurante = restauranteRepository.buscarRestaurantePorId(idRestaurante);
        if (restaurante == null) {
            System.out.println("El restaurante indicado no existe");
            return;
        }

        Plato plato = new Plato(contadorId, nombre, precio, descripcion, categoria, urlImagen, idRestaurante);
        platoRepository.guardarPlato(plato);
        contadorId++;

        System.out.println("Plato creado correctamente");
    }

    public void modificarPlato(Plato platoConCambios) {
        Plato platoExistente = platoRepository.buscarPlatoPorId(platoConCambios.getId());
        if (platoExistente == null) {
            System.out.println("Plato no encontrado");
            return;
        }

        if (!ValidarService.validarPrecioMayorACero(platoConCambios.getPrecio())) {
            System.out.println("El precio debe ser mayor a cero");
            return;
        }

        platoExistente.setPrecio(platoConCambios.getPrecio());
        platoExistente.setDescripcion(platoConCambios.getDescripcion());

        System.out.println("Plato modificado correctamente");
    }

    public void cambiarEstadoPlato(Usuario usuarioAutenticado, Long idPlato) {
        if (usuarioAutenticado == null || usuarioAutenticado.getRol() != Rol.PROPIETARIO) {
            System.out.println("No tiene permisos para cambiar el estado del plato");
            return;
        }

        Plato plato = platoRepository.buscarPlatoPorId(idPlato);
        if (plato == null) {
            System.out.println("Plato no encontrado");
            return;
        }

        Restaurante restaurante = restauranteRepository.buscarRestaurantePorId(plato.getIdRestaurante());
        if (restaurante == null || !restaurante.getIdPropietario().equals(usuarioAutenticado.getId())) {
            System.out.println("El plato no pertenece a un restaurante del propietario autenticado");
            return;
        }

        plato.setActivo(!plato.isActivo());
        System.out.println("Estado del plato actualizado a: " + plato.isActivo());
    }

    public Plato getPlato(Long id) {
        Plato plato = platoRepository.buscarPlatoPorId(id);
        if (plato == null) {
            System.out.println("Plato no encontrado");
            return null;
        }
        return plato;
    }

    public List<Plato> getPlatos() {
        return platoRepository.getPlatos();
    }
}
