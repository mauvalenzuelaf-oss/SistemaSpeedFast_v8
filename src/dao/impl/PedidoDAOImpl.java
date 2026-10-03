package dao.impl;

import dao.PedidoDAO;
import modelo.EstadoPedido;
import modelo.Pedido;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD
 * para los pedidos.
 */
public class PedidoDAOImpl implements PedidoDAO {

    /**
     * Registra un nuevo pedido.
     */
    @Override
    public boolean create(Pedido pedido) {

        String sql =
                "INSERT INTO pedidos "
                        + "(direccion, tipo, estado) "
                        + "VALUES (?, ?, ?)";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, pedido.getDireccion());
            stmt.setString(2, pedido.getTipo());
            stmt.setString(3, pedido.getEstado().name());

            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los pedidos registrados.
     */
    @Override
    public List<Pedido> readAll() {

        List<Pedido> pedidos = new ArrayList<>();

        String sql =
                "SELECT * FROM pedidos ORDER BY id";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        EstadoPedido.valueOf(
                                rs.getString("estado")
                        )
                );

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    /**
     * Actualiza los datos de un pedido.
     */
    @Override
    public boolean update(Pedido pedido) {

        String sql =
                "UPDATE pedidos "
                        + "SET direccion = ?, tipo = ?, estado = ? "
                        + "WHERE id = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, pedido.getDireccion());
            stmt.setString(2, pedido.getTipo());
            stmt.setString(3, pedido.getEstado().name());
            stmt.setInt(4, pedido.getId());

            int filasActualizadas = stmt.executeUpdate();

            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un pedido según su ID.
     */
    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM pedidos WHERE id = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            int filasEliminadas = stmt.executeUpdate();

            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza el estado de un pedido utilizando
     * una conexión existente.
     */
    public boolean updateEstado(
            Connection conn,
            int idPedido,
            EstadoPedido estado
    ) throws SQLException {

        String sql =
                "UPDATE pedidos "
                        + "SET estado = ? "
                        + "WHERE id = ?";

        try (
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, estado.name());
            stmt.setInt(2, idPedido);

            int filasActualizadas =
                    stmt.executeUpdate();

            return filasActualizadas > 0;
        }
    }
}