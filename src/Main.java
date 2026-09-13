import model.EstadoPedido;
import model.ItemPedido;
import model.Pedido;
import model.Plato;
import model.Restaurante;
import model.RestauranteResumen;
import model.Rol;
import model.Usuario;
import repository.PedidoRepository;
import repository.PlatoRepository;
import repository.RestauranteRepository;
import repository.UsuarioRepository;
import service.PasswordService;
import service.PedidoService;
import service.PlatoService;
import service.RestauranteService;
import service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UsuarioRepository usuarioRepository = new UsuarioRepository();
        RestauranteRepository restauranteRepository = new RestauranteRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository, restauranteRepository);
        RestauranteService restauranteService = new RestauranteService(restauranteRepository, usuarioRepository);
        PlatoRepository platoRepository = new PlatoRepository();
        PlatoService platoService = new PlatoService(platoRepository, restauranteRepository);
        PedidoRepository pedidoRepository = new PedidoRepository();
        PedidoService pedidoService = new PedidoService(pedidoRepository, platoRepository);

        System.out.println("--- Administrador de fabrica (dato semilla del sistema) ---");
        Usuario administrador = new Usuario(0L, "Admin", "Sistema", "0", 30, "0", "admin@plazoleta.com", PasswordService.encriptarClave("admin123"), Rol.ADMINISTRADOR);
        usuarioRepository.guardarUsuario(administrador);

        System.out.println("--- Login del administrador ---");
        Usuario adminAutenticado = usuarioService.autenticar("admin@plazoleta.com", "admin123");

        System.out.println("--- Registro de propietario valido (por el administrador) ---");
        usuarioService.registrarPropietario(adminAutenticado, "Juan", "Perez", "123456789", 25, "3001234567", "juan@correo.com", "clave123");

        System.out.println("--- Registro con correo invalido ---");
        usuarioService.registrarPropietario(adminAutenticado, "Ana", "Gomez", "987654321", 30, "3007654321", "ana-correo", "clave456");

        System.out.println("--- Registro menor de edad ---");
        usuarioService.registrarPropietario(adminAutenticado, "Luis", "Ramirez", "111222333", 16, "3009876543", "luis@correo.com", "clave789");

        System.out.println("--- Registro con documento no numerico ---");
        usuarioService.registrarPropietario(adminAutenticado, "Carla", "Diaz", "abc123", 28, "3001112233", "carla@correo.com", "clave000");

        System.out.println("--- Registro con correo duplicado ---");
        usuarioService.registrarPropietario(adminAutenticado, "Otro", "Juan", "444555666", 22, "3004445566", "juan@correo.com", "clave111");

        System.out.println("--- Intento de registrar propietario sin ser administrador ---");
        Usuario propietarioIntentandoCrearOtro = usuarioService.autenticar("juan@correo.com", "clave123");
        usuarioService.registrarPropietario(propietarioIntentandoCrearOtro, "Pedro", "Lopez", "555666777", 40, "3005556677", "pedro@correo.com", "clave222");

        System.out.println("--- Usuarios registrados ---");
        for (Usuario usuario : usuarioService.getUsuarios()) {
            System.out.println(usuario);
        }

        System.out.println("--- Login del propietario ---");
        Usuario propietarioAutenticado = usuarioService.autenticar("juan@correo.com", "clave123");

        System.out.println("--- Login con clave incorrecta ---");
        usuarioService.autenticar("juan@correo.com", "claveIncorrecta");

        System.out.println("--- Registro de restaurante valido (propietario id 1) ---");
        restauranteService.registrarRestaurante(propietarioAutenticado, "Sabor Criollo", "900123456", "Calle 10 # 5-20", "3101234567", "http://logo.com/1.png", 1L);

        System.out.println("--- Registro de restaurante con id de usuario que no es propietario ---");
        restauranteService.registrarRestaurante(propietarioAutenticado, "La Esquina", "900654321", "Calle 20 # 8-30", "3107654321", "http://logo.com/2.png", 99L);

        System.out.println("--- Registro de restaurante con NIT no numerico ---");
        restauranteService.registrarRestaurante(propietarioAutenticado, "El Fogon", "abc123", "Calle 30 # 9-40", "3109998888", "http://logo.com/3.png", 1L);

        System.out.println("--- Registro de restaurante con telefono muy largo ---");
        restauranteService.registrarRestaurante(propietarioAutenticado, "Buen Sabor", "900111222", "Calle 40 # 1-10", "31099988887777666", "http://logo.com/4.png", 1L);

        System.out.println("--- Registro de restaurante con nombre solo numerico ---");
        restauranteService.registrarRestaurante(propietarioAutenticado, "12345", "900333444", "Calle 50 # 2-20", "3105554444", "http://logo.com/5.png", 1L);

        System.out.println("--- Intento de registrar restaurante sin autenticarse (login fallido) ---");
        restauranteService.registrarRestaurante(null, "Sin Dueño", "900999888", "Calle 60 # 3-30", "3101112233", "http://logo.com/6.png", 1L);

        System.out.println("--- Intento de registrar restaurante siendo administrador (no propietario) ---");
        restauranteService.registrarRestaurante(adminAutenticado, "Restaurante Admin", "900777888", "Calle 70 # 4-40", "3104445566", "http://logo.com/7.png", 1L);

        System.out.println("--- Restaurantes registrados ---");
        for (Restaurante restaurante : restauranteService.getRestaurantes()) {
            System.out.println(restaurante);
        }

        System.out.println("--- Registro de empleado valido (por el propietario, restaurante id 1) ---");
        usuarioService.registrarEmpleado(propietarioAutenticado, "Marta", "Suarez", "222333444", 26, "3002223344", "marta@correo.com", "claveEmpleado1", 1L);

        System.out.println("--- Registro de empleado con campo obligatorio vacio ---");
        usuarioService.registrarEmpleado(propietarioAutenticado, "", "Rojas", "333444555", 27, "3003334455", "sinnombre@correo.com", "claveEmpleado2", 1L);

        System.out.println("--- Registro de empleado en un restaurante que no es del propietario ---");
        usuarioService.registrarEmpleado(propietarioAutenticado, "Pedro", "Lopez", "444555666", 29, "3004445566", "pedro2@correo.com", "claveEmpleado3", 99L);

        System.out.println("--- Registro de empleado con correo duplicado ---");
        usuarioService.registrarEmpleado(propietarioAutenticado, "Otra", "Marta", "555666777", 31, "3005556677", "marta@correo.com", "claveEmpleado4", 1L);

        System.out.println("--- Intento de registrar empleado siendo administrador (no propietario) ---");
        usuarioService.registrarEmpleado(adminAutenticado, "Falso", "Empleado", "666777888", 33, "3006667788", "falso@correo.com", "claveEmpleado5", 1L);

        System.out.println("--- Usuarios registrados (incluye empleado) ---");
        for (Usuario usuario : usuarioService.getUsuarios()) {
            System.out.println(usuario);
        }

        System.out.println("--- Creacion de plato valido (restaurante id 1) ---");
        platoService.crearPlato(propietarioAutenticado, "Bandeja Paisa", 25000, "Plato tipico con frijoles, arroz y carne", "Comida tipica", "http://imagen.com/1.png", 1L);

        System.out.println("--- Creacion de plato con precio invalido ---");
        platoService.crearPlato(propietarioAutenticado, "Sancocho", 0, "Sopa tradicional", "Sopas", "http://imagen.com/2.png", 1L);

        System.out.println("--- Creacion de plato con campo obligatorio vacio ---");
        platoService.crearPlato(propietarioAutenticado, "", 15000, "Descripcion valida", "Comida rapida", "http://imagen.com/3.png", 1L);

        System.out.println("--- Creacion de plato con restaurante inexistente ---");
        platoService.crearPlato(propietarioAutenticado, "Hamburguesa", 18000, "Hamburguesa con carne y queso", "Comida rapida", "http://imagen.com/4.png", 99L);

        System.out.println("--- Intento de crear plato siendo administrador (no propietario) ---");
        platoService.crearPlato(adminAutenticado, "Postre Admin", 12000, "Postre de prueba", "Postres", "http://imagen.com/5.png", 1L);

        System.out.println("--- Platos registrados ---");
        for (Plato plato : platoService.getPlatos()) {
            System.out.println(plato);
        }

        System.out.println("--- Modificacion de plato (intenta cambiar tambien nombre y categoria) ---");
        Plato cambiosRecibidos = new Plato(1L, "Nombre que deberia ser ignorado", 30000, "Nueva descripcion con mas ingredientes", "Categoria que deberia ser ignorada", "http://imagen.com/otra.png", 1L);
        platoService.modificarPlato(cambiosRecibidos);

        System.out.println("--- Modificacion de plato con precio invalido ---");
        Plato cambiosPrecioInvalido = new Plato(1L, "Otro nombre ignorado", 0, "Otra descripcion", "Otra categoria", "http://imagen.com/otra2.png", 1L);
        platoService.modificarPlato(cambiosPrecioInvalido);

        System.out.println("--- Plato luego de la modificacion ---");
        Plato platoModificado = platoService.getPlato(1L);
        System.out.println(platoModificado);

        System.out.println("--- Segundo propietario, restaurante y plato (para probar pertenencia) ---");
        usuarioService.registrarPropietario(adminAutenticado, "Sofia", "Torres", "999888777", 35, "3009998877", "sofia@correo.com", "claveSofia1");
        Usuario segundoPropietarioAutenticado = usuarioService.autenticar("sofia@correo.com", "claveSofia1");
        restauranteService.registrarRestaurante(segundoPropietarioAutenticado, "Delicias de Sofia", "900555666", "Calle 80 # 5-50", "3108887766", "http://logo.com/8.png", 3L);
        platoService.crearPlato(segundoPropietarioAutenticado, "Ajiaco", 22000, "Ajiaco santafereño", "Sopas", "http://imagen.com/6.png", 2L);

        System.out.println("--- Deshabilitar plato propio (valido) ---");
        platoService.cambiarEstadoPlato(propietarioAutenticado, 1L);

        System.out.println("--- Habilitar de nuevo el mismo plato ---");
        platoService.cambiarEstadoPlato(propietarioAutenticado, 1L);

        System.out.println("--- Intentar cambiar estado de un plato de otro propietario ---");
        platoService.cambiarEstadoPlato(propietarioAutenticado, 2L);

        System.out.println("--- Intentar cambiar estado sin ser propietario ---");
        platoService.cambiarEstadoPlato(adminAutenticado, 1L);

        System.out.println("--- Intentar cambiar estado de un plato inexistente ---");
        platoService.cambiarEstadoPlato(propietarioAutenticado, 99L);

        System.out.println("--- Platos registrados (estado final) ---");
        for (Plato plato : platoService.getPlatos()) {
            System.out.println(plato);
        }

        System.out.println("--- Registro publico de cliente valido (sin autenticacion) ---");
        usuarioService.registrarCliente("Camila", "Vargas", "777888999", 20, "3007778899", "camila@correo.com", "claveCliente1");

        System.out.println("--- Registro de cliente con campo obligatorio vacio ---");
        usuarioService.registrarCliente("Andres", "", "888999000", 24, "3008889900", "andres@correo.com", "claveCliente2");

        System.out.println("--- Registro de cliente con correo duplicado ---");
        usuarioService.registrarCliente("Otra", "Camila", "999000111", 22, "3009990011", "camila@correo.com", "claveCliente3");

        System.out.println("--- Usuarios registrados (incluye cliente) ---");
        for (Usuario usuario : usuarioService.getUsuarios()) {
            System.out.println(usuario);
        }

        System.out.println("--- Listado publico de restaurantes, pagina 1 tamano 1 (orden alfabetico) ---");
        for (RestauranteResumen resumen : restauranteService.listarRestaurantes(1, 1)) {
            System.out.println(resumen.getNombre() + " - " + resumen.getUrlLogo());
        }

        System.out.println("--- Listado publico de restaurantes, pagina 2 tamano 1 ---");
        for (RestauranteResumen resumen : restauranteService.listarRestaurantes(2, 1)) {
            System.out.println(resumen.getNombre() + " - " + resumen.getUrlLogo());
        }

        System.out.println("--- Listado publico de restaurantes, pagina 3 tamano 1 (no hay mas datos) ---");
        List<RestauranteResumen> paginaVacia = restauranteService.listarRestaurantes(3, 1);
        System.out.println("Cantidad de resultados: " + paginaVacia.size());

        System.out.println("--- Listado publico de restaurantes, pagina 1 tamano 10 (todos, orden alfabetico) ---");
        for (RestauranteResumen resumen : restauranteService.listarRestaurantes(1, 10)) {
            System.out.println(resumen.getNombre() + " - " + resumen.getUrlLogo());
        }

        System.out.println("--- Platos adicionales para probar el menu del restaurante 1 ---");
        platoService.crearPlato(propietarioAutenticado, "Sancocho de Gallina", 20000, "Sopa con pollo y verduras", "Sopas", "http://imagen.com/7.png", 1L);
        platoService.crearPlato(propietarioAutenticado, "Torta de Chocolate", 12000, "Postre dulce de chocolate", "Postres", "http://imagen.com/8.png", 1L);
        platoService.cambiarEstadoPlato(propietarioAutenticado, 4L);

        System.out.println("--- Menu completo activo del restaurante 1 (sin filtro de categoria) ---");
        for (Plato plato : platoService.listarPlatosPorRestaurante(1L, null, 1, 10)) {
            System.out.println(plato);
        }

        System.out.println("--- Menu del restaurante 1 filtrado por categoria Sopas ---");
        for (Plato plato : platoService.listarPlatosPorRestaurante(1L, "Sopas", 1, 10)) {
            System.out.println(plato);
        }

        System.out.println("--- Menu del restaurante 1 paginado, pagina 1 tamano 1 ---");
        for (Plato plato : platoService.listarPlatosPorRestaurante(1L, null, 1, 1)) {
            System.out.println(plato);
        }

        System.out.println("--- Menu del restaurante 1 paginado, pagina 2 tamano 1 ---");
        for (Plato plato : platoService.listarPlatosPorRestaurante(1L, null, 2, 1)) {
            System.out.println(plato);
        }

        System.out.println("--- Login de la clienta ---");
        Usuario clienteAutenticado = usuarioService.autenticar("camila@correo.com", "claveCliente1");

        System.out.println("--- Realizar pedido valido (platos 1 y 3, mismo restaurante) ---");
        List<ItemPedido> itemsPedido1 = new ArrayList<>();
        itemsPedido1.add(new ItemPedido(1L, 2));
        itemsPedido1.add(new ItemPedido(3L, 1));
        pedidoService.realizarPedido(clienteAutenticado, itemsPedido1);

        System.out.println("--- Intentar realizar otro pedido mientras el anterior esta en curso ---");
        List<ItemPedido> itemsPedido2 = new ArrayList<>();
        itemsPedido2.add(new ItemPedido(3L, 1));
        pedidoService.realizarPedido(clienteAutenticado, itemsPedido2);

        System.out.println("--- Registro y login de un segundo cliente ---");
        usuarioService.registrarCliente("Roberto", "Diaz", "121212121", 27, "3001212121", "roberto@correo.com", "claveRoberto1");
        Usuario segundoClienteAutenticado = usuarioService.autenticar("roberto@correo.com", "claveRoberto1");

        System.out.println("--- Intentar pedido con platos de restaurantes distintos ---");
        List<ItemPedido> itemsRestaurantesDistintos = new ArrayList<>();
        itemsRestaurantesDistintos.add(new ItemPedido(1L, 1));
        itemsRestaurantesDistintos.add(new ItemPedido(2L, 1));
        pedidoService.realizarPedido(segundoClienteAutenticado, itemsRestaurantesDistintos);

        System.out.println("--- Intentar pedido sin items ---");
        List<ItemPedido> itemsVacios = new ArrayList<>();
        pedidoService.realizarPedido(segundoClienteAutenticado, itemsVacios);

        System.out.println("--- Intentar pedido sin ser cliente ---");
        List<ItemPedido> itemsPedidoSinRol = new ArrayList<>();
        itemsPedidoSinRol.add(new ItemPedido(1L, 1));
        pedidoService.realizarPedido(propietarioAutenticado, itemsPedidoSinRol);

        System.out.println("--- Mas pedidos para probar el listado del empleado ---");
        List<ItemPedido> itemsPedidoRoberto = new ArrayList<>();
        itemsPedidoRoberto.add(new ItemPedido(1L, 1));
        pedidoService.realizarPedido(segundoClienteAutenticado, itemsPedidoRoberto);
        Pedido pedidoDeRoberto = pedidoService.getPedido(2L);
        pedidoDeRoberto.setEstado(EstadoPedido.EN_PREPARACION);

        usuarioService.registrarCliente("Laura", "Mora", "232323232", 24, "3002323232", "laura@correo.com", "claveLaura1");
        Usuario terceraClienteAutenticada = usuarioService.autenticar("laura@correo.com", "claveLaura1");
        List<ItemPedido> itemsPedidoLaura = new ArrayList<>();
        itemsPedidoLaura.add(new ItemPedido(2L, 1));
        pedidoService.realizarPedido(terceraClienteAutenticada, itemsPedidoLaura);

        usuarioService.registrarCliente("Diana", "Ruiz", "343434343", 29, "3003434343", "diana@correo.com", "claveDiana1");
        Usuario cuartaClienteAutenticada = usuarioService.autenticar("diana@correo.com", "claveDiana1");
        List<ItemPedido> itemsPedidoDiana = new ArrayList<>();
        itemsPedidoDiana.add(new ItemPedido(3L, 1));
        pedidoService.realizarPedido(cuartaClienteAutenticada, itemsPedidoDiana);

        System.out.println("--- Pedidos registrados ---");
        for (Pedido pedido : pedidoService.getPedidos()) {
            System.out.println(pedido);
        }

        System.out.println("--- Login de la empleada del restaurante 1 ---");
        Usuario empleadaAutenticada = usuarioService.autenticar("marta@correo.com", "claveEmpleado1");

        System.out.println("--- Pedidos PENDIENTE del restaurante 1, pagina 1 tamano 1 ---");
        for (Pedido pedido : pedidoService.listarPedidosPorEstado(empleadaAutenticada, EstadoPedido.PENDIENTE, 1, 1)) {
            System.out.println(pedido);
        }

        System.out.println("--- Pedidos PENDIENTE del restaurante 1, pagina 2 tamano 1 ---");
        for (Pedido pedido : pedidoService.listarPedidosPorEstado(empleadaAutenticada, EstadoPedido.PENDIENTE, 2, 1)) {
            System.out.println(pedido);
        }

        System.out.println("--- Pedidos EN_PREPARACION del restaurante 1 ---");
        for (Pedido pedido : pedidoService.listarPedidosPorEstado(empleadaAutenticada, EstadoPedido.EN_PREPARACION, 1, 10)) {
            System.out.println(pedido);
        }

        System.out.println("--- Intentar listar pedidos sin ser empleado ---");
        pedidoService.listarPedidosPorEstado(propietarioAutenticado, EstadoPedido.PENDIENTE, 1, 10);
    }
}
