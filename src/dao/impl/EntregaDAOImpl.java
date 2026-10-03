package dao.impl;

import dao.EntregaDAO;
import modelo.Entrega;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD
 * para las entregas.
 */
public class EntregaDAOImpl implements EntregaDAO {

    /**
     * Registra una nueva entrega.
     */
    @Override
    public boolean create(Entrega entrega) {

        try (Connection conn = ConexionBD.obtenerConexion()) {

            return create(conn, entrega);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Registra una entrega utilizando una conexión existente.
     */
    public boolean create(
            Connection conn,
            Entrega entrega
    ) throws SQLException {

        String sql =
                "INSERT INTO entregas "
                        + "(id_pedido, id_repartidor, fecha, hora) "
                        + "VALUES (?, ?, ?, ?)";

        try (
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdRepartidor());
            stmt.setDate(3, entrega.getFecha());
            stmt.setTime(4, entrega.getHora());

            int filasInsertadas = stmt.executeUpdate();

            return filasInsertadas > 0;
        }
    }

    /**
     * Obtiene todas las entregas registradas.
     */
    @Override
    public List<Entrega> readAll() {

        List<Entrega> entregas = new ArrayList<>();

        String sql =
                "SELECT * FROM entregas ORDER BY id";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Entrega entrega = new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getDate("fecha"),
                        rs.getTime("hora")
                );

                entregas.add(entrega);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entregas;
    }

    /**
     * Actualiza los datos de una entrega.
     */
    @Override
    public boolean update(Entrega entrega) {

        String sql =
                "UPDATE entregas "
                        + "SET id_pedido = ?, "
                        + "id_repartidor = ?, "
                        + "fecha = ?, "
                        + "hora = ? "
                        + "WHERE id = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, entrega.getIdPedido());
            stmt.setInt(2, entrega.getIdRepartidor());
            stmt.setDate(3, entrega.getFecha());
            stmt.setTime(4, entrega.getHora());
            stmt.setInt(5, entrega.getId());

            int filasActualizadas =
                    stmt.executeUpdate();

            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una entrega según su ID.
     */
    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM entregas WHERE id = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            int filasEliminadas =
                    stmt.executeUpdate();

            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
