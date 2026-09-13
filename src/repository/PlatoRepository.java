package repository;

import model.Plato;
import java.util.ArrayList;
import java.util.List;

public class PlatoRepository {
    private List<Plato> platos = new ArrayList<>();

    public void guardarPlato(Plato plato) {
        platos.add(plato);
    }

    public Plato buscarPlatoPorId(Long id) {
        for (Plato plato : platos) {
            if (plato.getId().equals(id)) {
                return plato;
            }
        }
        return null;
    }

    public List<Plato> getPlatos() {
        return platos;
    }
}
