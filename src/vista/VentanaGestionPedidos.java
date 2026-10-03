package vista;

import controlador.ControladorPedidos;
import modelo.EstadoPedido;
import modelo.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar los pedidos
 * del sistema SpeedFast.
 */
public class VentanaGestionPedidos extends JFrame {

    // Componentes
    private JTextField txtDireccion;
    private JComboBox<String> cmbTipo;
    private JComboBox<EstadoPedido> cmbEstado;

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    // Controlador
    private final ControladorPedidos controlador;

    /**
     * Construye la ventana de gestión de pedidos.
     */
    public VentanaGestionPedidos() {

        controlador =
                new ControladorPedidos();

        setTitle("SpeedFast - Gestión de Pedidos");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        cargarPedidos();
    }

    /**
     * Inicializa los componentes gráficos.
     */
    private void inicializarComponentes() {

        setLayout(new BorderLayout(10, 10));

        // Panel formulario
        JPanel panelFormulario =
                new JPanel(
                        new GridLayout(3, 2, 10, 10)
                );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 0, 10
                )
        );

        panelFormulario.add(
                new JLabel("Dirección:")
        );

        txtDireccion =
                new JTextField();

        panelFormulario.add(txtDireccion);

        panelFormulario.add(
                new JLabel("Tipo:")
        );

        cmbTipo =
                new JComboBox<>(
                        new String[]{
                                "COMIDA",
                                "ENCOMIENDA",
                                "EXPRESS"
                        }
                );

        panelFormulario.add(cmbTipo);

        panelFormulario.add(
                new JLabel("Estado:")
        );

        cmbEstado =
                new JComboBox<>(
                        EstadoPedido.values()
                );

        panelFormulario.add(cmbEstado);

        add(
                panelFormulario,
                BorderLayout.NORTH
        );

        // Tabla
        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Dirección",
                                "Tipo",
                                "Estado"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        tablaPedidos =
                new JTable(modeloTabla);

        tablaPedidos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        add(
                new JScrollPane(tablaPedidos),
                BorderLayout.CENTER
        );

        // Panel botones
        JPanel panelBotones =
                new JPanel();

        btnRegistrar =
                new JButton("Registrar");

        btnEditar =
                new JButton("Editar");

        btnEliminar =
                new JButton("Eliminar");

        btnLimpiar =
                new JButton("Limpiar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        add(
                panelBotones,
                BorderLayout.SOUTH
        );

        // Eventos
        btnRegistrar.addActionListener(
                e -> registrarPedido()
        );

        btnEditar.addActionListener(
                e -> editarPedido()
        );

        btnEliminar.addActionListener(
                e -> eliminarPedido()
        );

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        tablaPedidos
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        cargarPedidoSeleccionado();
                    }
                });
    }

    /**
     * Registra un nuevo pedido.
     */
    private void registrarPedido() {

        String direccion =
                txtDireccion.getText().trim();

        String tipo =
                (String) cmbTipo.getSelectedItem();

        EstadoPedido estado =
                (EstadoPedido)
                        cmbEstado.getSelectedItem();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar la dirección del pedido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean resultado =
                controlador.registrarPedido(
                        direccion,
                        tipo,
                        estado
                );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido registrado correctamente."
            );

            cargarPedidos();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el pedido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Edita el pedido seleccionado.
     */
    private void editarPedido() {

        int fila =
                tablaPedidos.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String direccion =
                txtDireccion.getText().trim();

        if (direccion.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "La dirección no puede estar vacía.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String tipo =
                (String) cmbTipo.getSelectedItem();

        EstadoPedido estado =
                (EstadoPedido)
                        cmbEstado.getSelectedItem();

        int id =
                (int) modeloTabla.getValueAt(
                        fila,
                        0
                );

        boolean resultado =
                controlador.actualizarPedido(
                        id,
                        direccion,
                        tipo,
                        estado
                );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido actualizado correctamente."
            );

            cargarPedidos();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el pedido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina el pedido seleccionado.
     */
    private void eliminarPedido() {

        int fila =
                tablaPedidos.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int id =
                (int) modeloTabla.getValueAt(
                        fila,
                        0
                );

        int confirmacion =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea eliminar el pedido seleccionado?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean resultado =
                controlador.eliminarPedido(id);

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado correctamente."
            );

            cargarPedidos();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el pedido. "
                            + "Puede tener entregas asociadas.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Carga los pedidos desde la base de datos.
     */
    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        List<Pedido> pedidos =
                controlador.listarPedidos();

        for (Pedido pedido : pedidos) {

            modeloTabla.addRow(
                    new Object[]{
                            pedido.getId(),
                            pedido.getDireccion(),
                            pedido.getTipo(),
                            pedido.getEstado()
                    }
            );
        }
    }

    /**
     * Carga en el formulario el pedido
     * seleccionado en la tabla.
     */
    private void cargarPedidoSeleccionado() {

        int fila =
                tablaPedidos.getSelectedRow();

        if (fila != -1) {

            txtDireccion.setText(
                    modeloTabla
                            .getValueAt(fila, 1)
                            .toString()
            );

            cmbTipo.setSelectedItem(
                    modeloTabla
                            .getValueAt(fila, 2)
                            .toString()
            );

            cmbEstado.setSelectedItem(
                    EstadoPedido.valueOf(
                            modeloTabla
                                    .getValueAt(fila, 3)
                                    .toString()
                    )
            );
        }
    }

    /**
     * Limpia el formulario.
     */
    private void limpiarFormulario() {

        txtDireccion.setText("");

        cmbTipo.setSelectedIndex(0);

        cmbEstado.setSelectedItem(
                EstadoPedido.PENDIENTE
        );

        tablaPedidos.clearSelection();

        txtDireccion.requestFocus();
    }
}
