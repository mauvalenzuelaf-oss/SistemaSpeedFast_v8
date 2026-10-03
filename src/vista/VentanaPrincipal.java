package vista;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema SpeedFast.
 */
public class VentanaPrincipal extends JFrame {

    // Botones
    private JButton btnRepartidores;
    private JButton btnPedidos;
    private JButton btnEntregas;

    /**
     * Construye la ventana principal.
     */
    public VentanaPrincipal() {

        setTitle("SpeedFast - Sistema de Gestión");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    /**
     * Inicializa los componentes gráficos.
     */
    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        JLabel lblTitulo =
                new JLabel(
                        "Sistema SpeedFast",
                        SwingConstants.CENTER
                );

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        22
                )
        );

        lblTitulo.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 10, 20, 10
                )
        );

        add(
                lblTitulo,
                BorderLayout.NORTH
        );

        JPanel panelBotones =
                new JPanel(
                        new GridLayout(
                                3,
                                1,
                                10,
                                10
                        )
                );

        panelBotones.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 60, 30, 60
                )
        );

        btnRepartidores =
                new JButton(
                        "Gestión de Repartidores"
                );

        btnPedidos =
                new JButton(
                        "Gestión de Pedidos"
                );

        btnEntregas =
                new JButton(
                        "Gestión de Entregas"
                );

        panelBotones.add(btnRepartidores);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnEntregas);

        add(
                panelBotones,
                BorderLayout.CENTER
        );

        // Eventos
        btnRepartidores.addActionListener(
                e -> abrirRepartidores()
        );

        btnPedidos.addActionListener(
                e -> abrirPedidos()
        );

        btnEntregas.addActionListener(
                e -> abrirEntregas()
        );
    }

    /**
     * Abre la ventana de gestión de repartidores.
     */
    private void abrirRepartidores() {

        VentanaGestionRepartidores ventana =
                new VentanaGestionRepartidores();

        ventana.setVisible(true);
    }

    /**
     * Abre la ventana de gestión de pedidos.
     */
    private void abrirPedidos() {

        VentanaGestionPedidos ventana =
                new VentanaGestionPedidos();

        ventana.setVisible(true);
    }

    /**
     * Abre la ventana de gestión de entregas.
     */
    private void abrirEntregas() {

        VentanaGestionEntregas ventana =
                new VentanaGestionEntregas();

        ventana.setVisible(true);
    }
}