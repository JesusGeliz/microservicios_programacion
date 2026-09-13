package model;

public class ItemPedido {
    private Long idPlato;
    private int cantidad;

    public ItemPedido(Long idPlato, int cantidad) {
        this.idPlato = idPlato;
        this.cantidad = cantidad;
    }

    public Long getIdPlato() { return idPlato; }
    public void setIdPlato(Long idPlato) { this.idPlato = idPlato; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idPlato=" + idPlato +
                ", cantidad=" + cantidad +
                '}';
    }
}
