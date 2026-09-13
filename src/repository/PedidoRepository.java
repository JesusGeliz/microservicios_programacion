package repository;

import model.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {
    private List<Pedido> pedidos = new ArrayList<>();

    public void guardarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public Pedido buscarPedidoPorId(Long id) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId().equals(id)) {
                return pedido;
            }
        }
        return null;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}
