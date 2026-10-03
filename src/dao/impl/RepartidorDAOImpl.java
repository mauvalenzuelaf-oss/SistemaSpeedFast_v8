package dao.impl;

import dao.RepartidorDAO;
import modelo.Repartidor;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa las operaciones CRUD
 * para los repartidores.
 */
public class RepartidorDAOImpl implements RepartidorDAO {

    /**
     * Registra un nuevo repartidor.
     */
    @Override
    public boolean create(Repartidor repartidor) {

        String sql =
                "INSERT INTO repartidores (nombre) VALUES (?)";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, repartidor.getNombre());
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los repartidores registrados.
     */
    @Override
    public List<Repartidor> readAll() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql =
                "SELECT * FROM repartidores ORDER BY id";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Repartidor repartidor = new Repartidor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );

                repartidores.add(repartidor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return repartidores;
    }

    /**
     * Actualiza los datos de un repartidor.
     */
    @Override
    public boolean update(Repartidor repartidor) {

        String sql =
                "UPDATE repartidores "
                        + "SET nombre = ? "
                        + "WHERE id = ?";

        try (
                Connection conn = ConexionBD.obtenerConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, repartidor.getNombre());
            stmt.setInt(2, repartidor.getId());

            int filasActualizadas = stmt.executeUpdate();

            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un repartidor según su ID.
     */
    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM repartidores WHERE id = ?";

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
}
