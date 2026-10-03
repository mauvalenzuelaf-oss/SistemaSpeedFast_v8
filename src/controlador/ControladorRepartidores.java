package controlador;

import dao.RepartidorDAO;
import dao.impl.RepartidorDAOImpl;
import modelo.Repartidor;

import java.util.List;

/**
 * Coordina las operaciones relacionadas
 * con los repartidores.
 */
public class ControladorRepartidores {

    // DAO
    private final RepartidorDAO repartidorDAO;

    /**
     * Construye el controlador de repartidores.
     */
    public ControladorRepartidores() {
        repartidorDAO = new RepartidorDAOImpl();
    }

    /**
     * Registra un nuevo repartidor.
     */
    public boolean registrarRepartidor(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        Repartidor repartidor =
                new Repartidor(nombre.trim());

        return repartidorDAO.create(repartidor);
    }

    /**
     * Obtiene todos los repartidores.
     */
    public List<Repartidor> listarRepartidores() {
        return repartidorDAO.readAll();
    }

    /**
     * Actualiza un repartidor.
     */
    public boolean actualizarRepartidor(
            int id,
            String nombre
    ) {

        if (id <= 0
                || nombre == null
                || nombre.trim().isEmpty()) {

            return false;
        }

        Repartidor repartidor =
                new Repartidor(
                        id,
                        nombre.trim()
                );

        return repartidorDAO.update(repartidor);
    }

    /**
     * Elimina un repartidor.
     */
    public boolean eliminarRepartidor(int id) {

        if (id <= 0) {
            return false;
        }

        return repartidorDAO.delete(id);
    }
}
