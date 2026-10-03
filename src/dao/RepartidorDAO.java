package dao;

import modelo.Repartidor;

import java.util.List;

/**
 * Define las operaciones de acceso a datos
 * para los repartidores.
 */
public interface RepartidorDAO {

    boolean create(Repartidor repartidor);

    List<Repartidor> readAll();

    boolean update(Repartidor repartidor);

    boolean delete(int id);
}
