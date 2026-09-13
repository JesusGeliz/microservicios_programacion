package service;

import model.EstadoPedido;
import model.ItemPedido;
import model.Pedido;
import model.Plato;
import model.Rol;
import model.Usuario;
import repository.PedidoRepository;
import repository.PlatoRepository;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private PedidoRepository pedidoRepository;
    private PlatoRepository platoRepository;
    private Long contadorId = 1L;

    public PedidoService(PedidoRepository pedidoRepository, PlatoRepository platoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.platoRepository = platoRepository;
    }

    public void realizarPedido(Usuario usuarioAutenticado, List<ItemPedido> items) {

        if (usuarioAutenticado == null || usuarioAutenticado.getRol() != Rol.CLIENTE) {
            System.out.println("No tiene permisos para realizar un pedido");
            return;
        }

        if (items == null || items.isEmpty()) {
            System.out.println("El pedido debe tener al menos un plato");
            return;
        }

        for (Pedido pedido : pedidoRepository.getPedidos()) {
            if (pedido.getIdCliente().equals(usuarioAutenticado.getId())
                    && pedido.getEstado() != EstadoPedido.ENTREGADO
                    && pedido.getEstado() != EstadoPedido.CANCELADO) {
                System.out.println("El cliente ya tiene un pedido en curso");
                return;
            }
        }

        Long idRestaurante = null;
        for (ItemPedido item : items) {
            Plato plato = platoRepository.buscarPlatoPorId(item.getIdPlato());
            if (plato == null) {
                System.out.println("Uno de los platos del pedido no existe");
                return;
            }
            if (idRestaurante == null) {
                idRestaurante = plato.getIdRestaurante();
            } else if (!idRestaurante.equals(plato.getIdRestaurante())) {
                System.out.println("Todos los platos del pedido deben ser del mismo restaurante");
                return;
            }
        }

        Pedido pedido = new Pedido(contadorId, usuarioAutenticado.getId(), idRestaurante, items, EstadoPedido.PENDIENTE);
        pedidoRepository.guardarPedido(pedido);
        contadorId++;

        System.out.println("Pedido realizado correctamente, estado: " + pedido.getEstado());
    }

    public Pedido getPedido(Long id) {
        Pedido pedido = pedidoRepository.buscarPedidoPorId(id);
        if (pedido == null) {
            System.out.println("Pedido no encontrado");
            return null;
        }
        return pedido;
    }

    public List<Pedido> getPedidos() {
        return pedidoRepository.getPedidos();
    }

    public List<Pedido> listarPedidosPorEstado(Usuario usuarioAutenticado, EstadoPedido estado, int pagina, int tamanioPagina) {
        List<Pedido> resultado = new ArrayList<>();

        if (usuarioAutenticado == null || usuarioAutenticado.getRol() != Rol.EMPLEADO) {
            System.out.println("No tiene permisos para listar los pedidos");
            return resultado;
        }

        List<Pedido> pedidosFiltrados = new ArrayList<>();
        for (Pedido pedido : pedidoRepository.getPedidos()) {
            if (pedido.getIdRestaurante().equals(usuarioAutenticado.getIdRestaurante()) && pedido.getEstado() == estado) {
                pedidosFiltrados.add(pedido);
            }
        }

        int inicio = (pagina - 1) * tamanioPagina;
        if (inicio < 0 || inicio >= pedidosFiltrados.size()) {
            return resultado;
        }

        int fin = inicio + tamanioPagina;
        if (fin > pedidosFiltrados.size()) {
            fin = pedidosFiltrados.size();
        }

        for (int i = inicio; i < fin; i++) {
            resultado.add(pedidosFiltrados.get(i));
        }

        return resultado;
    }
}
