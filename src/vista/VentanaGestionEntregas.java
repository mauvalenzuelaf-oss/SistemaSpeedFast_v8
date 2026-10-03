package vista;

import controlador.ControladorEntregas;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * Ventana para gestionar las entregas
 * del sistema SpeedFast.
 */
public class VentanaGestionEntregas extends JFrame {

    // Componentes
    private JComboBox<Pedido> cmbPedido;
    private JComboBox<Repartidor> cmbRepartidor;

    private JTextField txtFecha;
    private JTextField txtHora;

    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnActualizar;

    // Controlador
    private final ControladorEntregas controlador;

    /**
     * Construye la ventana de gestión de entregas.
     */
    public VentanaGestionEntregas() {

        controlador =
                new ControladorEntregas();

        setTitle("SpeedFast - Gestión de Entregas");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();

        cargarCombos();
        cargarEntregas();
    }

    /**
     * Inicializa los componentes gráficos.
     */
    private void inicializarComponentes() {

        setLayout(new BorderLayout(10, 10));

        // Panel formulario
        JPanel panelFormulario =
                new JPanel(
                        new GridLayout(4, 2, 10, 10)
                );

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 0, 10
                )
        );

        panelFormulario.add(
                new JLabel("Pedido:")
        );

        cmbPedido =
                new JComboBox<>();

        panelFormulario.add(cmbPedido);

        panelFormulario.add(
                new JLabel("Repartidor:")
        );

        cmbRepartidor =
                new JComboBox<>();

        panelFormulario.add(cmbRepartidor);

        panelFormulario.add(
                new JLabel("Fecha (AAAA-MM-DD):")
        );

        txtFecha =
                new JTextField();

        panelFormulario.add(txtFecha);

        panelFormulario.add(
                new JLabel("Hora (HH:MM:SS):")
        );

        txtHora =
                new JTextField();

        panelFormulario.add(txtHora);

        add(
                panelFormulario,
                BorderLayout.NORTH
        );

        // Tabla
        modeloTabla =
                new DefaultTableModel(
                        new Object[]{
                                "ID",
                                "Pedido",
                                "Repartidor",
                                "Fecha",
                                "Hora"
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

        tablaEntregas =
                new JTable(modeloTabla);

        tablaEntregas.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        add(
                new JScrollPane(tablaEntregas),
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

        btnActualizar =
                new JButton("Actualizar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnActualizar);

        add(
                panelBotones,
                BorderLayout.SOUTH
        );

        // Eventos
        btnRegistrar.addActionListener(
                e -> registrarEntrega()
        );

        btnEditar.addActionListener(
                e -> editarEntrega()
        );

        btnEliminar.addActionListener(
                e -> eliminarEntrega()
        );

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        btnActualizar.addActionListener(
                e -> {
                    cargarCombos();
                    cargarEntregas();
                }
        );

        tablaEntregas
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        cargarEntregaSeleccionada();
                    }
                });
    }

    /**
     * Carga pedidos y repartidores desde
     * la base de datos.
     */
    private void cargarCombos() {

        cmbPedido.removeAllItems();
        cmbRepartidor.removeAllItems();

        for (Pedido pedido
                : controlador.listarPedidos()) {

            cmbPedido.addItem(pedido);
        }

        for (Repartidor repartidor
                : controlador.listarRepartidores()) {

            cmbRepartidor.addItem(repartidor);
        }
    }

    /**
     * Registra una nueva entrega.
     */
    private void registrarEntrega() {

        Pedido pedido =
                (Pedido) cmbPedido.getSelectedItem();

        Repartidor repartidor =
                (Repartidor)
                        cmbRepartidor.getSelectedItem();

        if (pedido == null
                || repartidor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe existir al menos un pedido "
                            + "y un repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Date fecha;
        Time hora;

        try {

            fecha =
                    Date.valueOf(
                            txtFecha.getText().trim()
                    );

            hora =
                    Time.valueOf(
                            txtHora.getText().trim()
                    );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese fecha y hora válidas.\n"
                            + "Fecha: AAAA-MM-DD\n"
                            + "Hora: HH:MM:SS",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean resultado =
                controlador.registrarEntrega(
                        pedido,
                        repartidor,
                        fecha,
                        hora
                );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega registrada correctamente."
            );

            cargarCombos();
            cargarEntregas();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la entrega.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Edita la entrega seleccionada.
     */
    private void editarEntrega() {

        int fila =
                tablaEntregas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una entrega.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Pedido pedido =
                (Pedido) cmbPedido.getSelectedItem();

        Repartidor repartidor =
                (Repartidor)
                        cmbRepartidor.getSelectedItem();

        if (pedido == null
                || repartidor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido "
                            + "y un repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Date fecha;
        Time hora;

        try {

            fecha =
                    Date.valueOf(
                            txtFecha.getText().trim()
                    );

            hora =
                    Time.valueOf(
                            txtHora.getText().trim()
                    );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese fecha y hora válidas.",
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

        boolean resultado =
                controlador.actualizarEntrega(
                        id,
                        pedido,
                        repartidor,
                        fecha,
                        hora
                );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega actualizada correctamente."
            );

            cargarEntregas();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar la entrega.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina la entrega seleccionada.
     */
    private void eliminarEntrega() {

        int fila =
                tablaEntregas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar una entrega.",
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
                        "¿Desea eliminar la entrega seleccionada?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean resultado =
                controlador.eliminarEntrega(id);

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega eliminada correctamente."
            );

            cargarEntregas();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar la entrega.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Carga las entregas registradas.
     */
    private void cargarEntregas() {

        modeloTabla.setRowCount(0);

        List<Entrega> entregas =
                controlador.listarEntregas();

        for (Entrega entrega : entregas) {

            Pedido pedido =
                    buscarPedidoPorId(
                            entrega.getIdPedido()
                    );

            Repartidor repartidor =
                    buscarRepartidorPorId(
                            entrega.getIdRepartidor()
                    );

            modeloTabla.addRow(
                    new Object[]{
                            entrega.getId(),
                            pedido != null
                                    ? pedido
                                    : entrega.getIdPedido(),
                            repartidor != null
                                    ? repartidor
                                    : entrega.getIdRepartidor(),
                            entrega.getFecha(),
                            entrega.getHora()
                    }
            );
        }
    }

    /**
     * Busca un pedido según su ID.
     */
    private Pedido buscarPedidoPorId(int id) {

        for (Pedido pedido
                : controlador.listarPedidos()) {

            if (pedido.getId() == id) {
                return pedido;
            }
        }

        return null;
    }

    /**
     * Busca un repartidor según su ID.
     */
    private Repartidor buscarRepartidorPorId(int id) {

        for (Repartidor repartidor
                : controlador.listarRepartidores()) {

            if (repartidor.getId() == id) {
                return repartidor;
            }
        }

        return null;
    }

    /**
     * Carga los datos de la entrega
     * seleccionada en el formulario.
     */
    private void cargarEntregaSeleccionada() {

        int fila =
                tablaEntregas.getSelectedRow();

        if (fila == -1) {
            return;
        }

        int idEntrega =
                (int) modeloTabla.getValueAt(
                        fila,
                        0
                );

        for (Entrega entrega
                : controlador.listarEntregas()) {

            if (entrega.getId() == idEntrega) {

                seleccionarPedido(
                        entrega.getIdPedido()
                );

                seleccionarRepartidor(
                        entrega.getIdRepartidor()
                );

                txtFecha.setText(
                        entrega.getFecha().toString()
                );

                txtHora.setText(
                        entrega.getHora().toString()
                );

                break;
            }
        }
    }

    /**
     * Selecciona un pedido en el JComboBox
     * según su ID.
     */
    private void seleccionarPedido(int id) {

        for (int i = 0;
             i < cmbPedido.getItemCount();
             i++) {

            Pedido pedido =
                    cmbPedido.getItemAt(i);

            if (pedido.getId() == id) {

                cmbPedido.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Selecciona un repartidor en el JComboBox
     * según su ID.
     */
    private void seleccionarRepartidor(int id) {

        for (int i = 0;
             i < cmbRepartidor.getItemCount();
             i++) {

            Repartidor repartidor =
                    cmbRepartidor.getItemAt(i);

            if (repartidor.getId() == id) {

                cmbRepartidor.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Limpia el formulario.
     */
    private void limpiarFormulario() {

        if (cmbPedido.getItemCount() > 0) {
            cmbPedido.setSelectedIndex(0);
        }

        if (cmbRepartidor.getItemCount() > 0) {
            cmbRepartidor.setSelectedIndex(0);
        }

        txtFecha.setText("");
        txtHora.setText("");

        tablaEntregas.clearSelection();
    }
}