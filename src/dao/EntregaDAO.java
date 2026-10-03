package dao;

import modelo.Entrega;

import java.util.List;

/**
 * Define las operaciones de acceso a datos
 * para las entregas.
 */
public interface EntregaDAO {

    boolean create(Entrega entrega);

    List<Entrega> readAll();

    boolean update(Entrega entrega);

    boolean delete(int id);
}