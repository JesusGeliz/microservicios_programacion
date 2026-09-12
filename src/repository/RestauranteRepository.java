package repository;

import model.Restaurante;
import java.util.ArrayList;
import java.util.List;

public class RestauranteRepository {
    private List<Restaurante> restaurantes = new ArrayList<>();

    public void guardarRestaurante(Restaurante restaurante) {
        restaurantes.add(restaurante);
    }

    public Restaurante buscarRestaurantePorId(Long id) {
        for (Restaurante restaurante : restaurantes) {
            if (restaurante.getId().equals(id)) {
                return restaurante;
            }
        }
        return null;
    }

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
    }
}
