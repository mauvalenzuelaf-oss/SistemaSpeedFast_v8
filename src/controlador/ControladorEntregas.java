package controlador;

import dao.RepartidorDAO;
import dao.impl.EntregaDAOImpl;
import dao.impl.PedidoDAOImpl;
import dao.impl.RepartidorDAOImpl;
import modelo.Entrega;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.Repartidor;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

/**
 * Coordina las operaciones relacionadas
 * con las entregas.
 */
public class ControladorEntregas {

    // DAO
    private final EntregaDAOImpl entregaDAO;
    private final PedidoDAOImpl pedidoDAO;
    private final RepartidorDAO repartidorDAO;

    /**
     * Construye el controlador de entregas.
     */
    public ControladorEntregas() {
        entregaDAO = new EntregaDAOImpl();
        pedidoDAO = new PedidoDAOImpl();
        repartidorDAO = new RepartidorDAOImpl();
    }

    /**
     * Registra una entrega y cambia el estado
     * del pedido a EN_REPARTO mediante una transacción.
     */
    public boolean registrarEntrega(
            Pedido pedido,
            Repartidor repartidor,
            Date fecha,
            Time hora
    ) {

        if (pedido == null
                || repartidor == null
                || fecha == null
                || hora == null) {

            return false;
        }

        Entrega entrega = new Entrega(
                pedido.getId(),
                repartidor.getId(),
                fecha,
                hora
        );

        try (
                Connection conn =
                        ConexionBD.obtenerConexion()
        ) {

            conn.setAutoCommit(false);

            try {

                boolean entregaGuardada =
                        entregaDAO.create(
                                conn,
                                entrega
                        );

                if (!entregaGuardada) {
                    conn.rollback();
                    return false;
                }

                boolean estadoActualizado =
                        pedidoDAO.updateEstado(
                                conn,
                                pedido.getId(),
                                EstadoPedido.EN_REPARTO
                        );

                if (!estadoActualizado) {
                    conn.rollback();
                    return false;
                }

                conn.commit();

                return true;

            } catch (SQLException e) {

                conn.rollback();
                e.printStackTrace();

                return false;

            } finally {

                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todas las entregas registradas.
     */
    public List<Entrega> listarEntregas() {
        return entregaDAO.readAll();
    }

    /**
     * Obtiene todos los pedidos para los JComboBox.
     */
    public List<Pedido> listarPedidos() {
        return pedidoDAO.readAll();
    }

    /**
     * Obtiene todos los repartidores para los JComboBox.
     */
    public List<Repartidor> listarRepartidores() {
        return repartidorDAO.readAll();
    }

    /**
     * Actualiza una entrega existente.
     */
    public boolean actualizarEntrega(
            int id,
            Pedido pedido,
            Repartidor repartidor,
            Date fecha,
            Time hora
    ) {

        if (id <= 0
                || pedido == null
                || repartidor == null
                || fecha == null
                || hora == null) {

            return false;
        }

        Entrega entrega = new Entrega(
                id,
                pedido.getId(),
                repartidor.getId(),
                fecha,
                hora
        );

        return entregaDAO.update(entrega);
    }

    /**
     * Elimina una entrega.
     */
    public boolean eliminarEntrega(int id) {

        if (id <= 0) {
            return false;
        }

        return entregaDAO.delete(id);
    }
}
