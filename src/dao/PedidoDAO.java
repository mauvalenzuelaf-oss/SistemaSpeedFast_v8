package dao;

import modelo.Pedido;

import java.util.List;

/**
 * Define las operaciones de acceso a datos
 * para los pedidos.
 */
public interface PedidoDAO {

    boolean create(Pedido pedido);

    List<Pedido> readAll();

    boolean update(Pedido pedido);

    boolean delete(int id);
}
