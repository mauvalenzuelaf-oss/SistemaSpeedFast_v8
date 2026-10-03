package controlador;

import dao.PedidoDAO;
import dao.impl.PedidoDAOImpl;
import modelo.EstadoPedido;
import modelo.Pedido;

import java.util.List;

/**
 * Coordina las operaciones relacionadas
 * con los pedidos.
 */
public class ControladorPedidos {

    // DAO
    private final PedidoDAO pedidoDAO;

    /**
     * Construye el controlador de pedidos.
     */
    public ControladorPedidos() {
        pedidoDAO = new PedidoDAOImpl();
    }

    /**
     * Registra un nuevo pedido.
     */
    public boolean registrarPedido(
            String direccion,
            String tipo,
            EstadoPedido estado
    ) {

        if (direccion == null
                || direccion.trim().isEmpty()
                || tipo == null
                || tipo.trim().isEmpty()
                || estado == null) {

            return false;
        }

        Pedido pedido = new Pedido(
                direccion.trim(),
                tipo,
                estado
        );

        return pedidoDAO.create(pedido);
    }

    /**
     * Obtiene todos los pedidos.
     */
    public List<Pedido> listarPedidos() {
        return pedidoDAO.readAll();
    }

    /**
     * Actualiza un pedido.
     */
    public boolean actualizarPedido(
            int id,
            String direccion,
            String tipo,
            EstadoPedido estado
    ) {

        if (id <= 0
                || direccion == null
                || direccion.trim().isEmpty()
                || tipo == null
                || tipo.trim().isEmpty()
                || estado == null) {

            return false;
        }

        Pedido pedido = new Pedido(
                id,
                direccion.trim(),
                tipo,
                estado
        );

        return pedidoDAO.update(pedido);
    }

    /**
     * Elimina un pedido.
     */
    public boolean eliminarPedido(int id) {

        if (id <= 0) {
            return false;
        }

        return pedidoDAO.delete(id);
    }
}
