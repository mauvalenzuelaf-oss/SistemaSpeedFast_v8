package vista;

import controlador.ControladorRepartidores;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para gestionar los repartidores
 * del sistema SpeedFast.
 */
public class VentanaGestionRepartidores extends JFrame {

    // Componentes
    private JTextField txtNombre;
    private JTable tablaRepartidores;
    private DefaultTableModel modeloTabla;

    private JButton btnRegistrar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    // Controlador
    private final ControladorRepartidores controlador;

    /**
     * Construye la ventana de gestión de repartidores.
     */
    public VentanaGestionRepartidores() {

        controlador =
                new ControladorRepartidores();

        setTitle("SpeedFast - Gestión de Repartidores");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        cargarRepartidores();
    }

    /**
     * Inicializa los componentes gráficos.
     */
    private void inicializarComponentes() {

        setLayout(new BorderLayout(10, 10));

        // Panel formulario
        JPanel panelFormulario =
                new JPanel(new GridLayout(2, 2, 10, 10));

        panelFormulario.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 0, 10
                )
        );

        panelFormulario.add(
                new JLabel("Nombre:")
        );

        txtNombre = new JTextField();

        panelFormulario.add(txtNombre);

        add(
                panelFormulario,
                BorderLayout.NORTH
        );

        // Tabla
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Nombre"},
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

        tablaRepartidores =
                new JTable(modeloTabla);

        tablaRepartidores.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        add(
                new JScrollPane(tablaRepartidores),
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
                e -> registrarRepartidor()
        );

        btnEditar.addActionListener(
                e -> editarRepartidor()
        );

        btnEliminar.addActionListener(
                e -> eliminarRepartidor()
        );

        btnLimpiar.addActionListener(
                e -> limpiarFormulario()
        );

        tablaRepartidores
                .getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        cargarRepartidorSeleccionado();
                    }
                });
    }

    /**
     * Registra un nuevo repartidor.
     */
    private void registrarRepartidor() {

        String nombre =
                txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el nombre del repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean resultado =
                controlador.registrarRepartidor(nombre);

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor registrado correctamente."
            );

            cargarRepartidores();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el repartidor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Edita el repartidor seleccionado.
     */
    private void editarRepartidor() {

        int fila =
                tablaRepartidores.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un repartidor.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String nombre =
                txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre no puede estar vacío.",
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
                controlador.actualizarRepartidor(
                        id,
                        nombre
                );

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado correctamente."
            );

            cargarRepartidores();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el repartidor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Elimina el repartidor seleccionado.
     */
    private void eliminarRepartidor() {

        int fila =
                tablaRepartidores.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un repartidor.",
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
                        "¿Desea eliminar el repartidor seleccionado?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean resultado =
                controlador.eliminarRepartidor(id);

        if (resultado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor eliminado correctamente."
            );

            cargarRepartidores();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el repartidor. "
                            + "Puede tener entregas asociadas.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Carga los repartidores desde la base de datos.
     */
    private void cargarRepartidores() {

        modeloTabla.setRowCount(0);

        List<Repartidor> repartidores =
                controlador.listarRepartidores();

        for (Repartidor repartidor : repartidores) {

            modeloTabla.addRow(
                    new Object[]{
                            repartidor.getId(),
                            repartidor.getNombre()
                    }
            );
        }
    }

    /**
     * Carga en el formulario el repartidor
     * seleccionado en la tabla.
     */
    private void cargarRepartidorSeleccionado() {

        int fila =
                tablaRepartidores.getSelectedRow();

        if (fila != -1) {

            txtNombre.setText(
                    modeloTabla
                            .getValueAt(fila, 1)
                            .toString()
            );
        }
    }

    /**
     * Limpia el formulario.
     */
    private void limpiarFormulario() {

        txtNombre.setText("");

        tablaRepartidores.clearSelection();

        txtNombre.requestFocus();
    }
}
