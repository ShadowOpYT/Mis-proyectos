package interfaz;

import gestion.GestorTurnos;
import modelo.Cliente;
import modelo.Turno;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private GestorTurnos gestor;
    private DefaultTableModel modeloTabla;
    private JComboBox<Cliente> cbClientes;

    public VentanaPrincipal() {
        gestor = new GestorTurnos();
        setTitle("Gestor de Turnos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane panelPestanas = new JTabbedPane();
        panelPestanas.addTab("Gestión de Clientes", crearPanelClientes());
        panelPestanas.addTab("Gestión de Turnos", crearPanelTurnos());

        add(panelPestanas);
    }

    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtTelefono = new JTextField();
        JButton btnGuardar = new JButton("Registrar Cliente");

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Apellido:"));
        panelFormulario.add(txtApellido);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre y Apellido son obligatorios.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Cliente nuevoCliente = new Cliente(txtId.getText(), txtNombre.getText(), txtApellido.getText(), txtTelefono.getText());
            gestor.registrarCliente(nuevoCliente);
            actualizarComboClientes();
            txtId.setText(""); txtNombre.setText(""); txtApellido.setText(""); txtTelefono.setText("");
            JOptionPane.showMessageDialog(this, "Cliente registrado en la base de datos.");
        });

        panel.add(panelFormulario, BorderLayout.NORTH);
        return panel;
    }

    private JPanel crearPanelTurnos() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cbClientes = new JComboBox<>();
        JTextField txtFecha = new JTextField();
        JTextField txtHora = new JTextField();
        JTextField txtServicio = new JTextField();
        JButton btnRegistrar = new JButton("Registrar Turno");

        panelFormulario.add(new JLabel("Cliente:"));
        panelFormulario.add(cbClientes);
        panelFormulario.add(new JLabel("Fecha (DD/MM/AAAA):"));
        panelFormulario.add(txtFecha);
        panelFormulario.add(new JLabel("Hora (HH:MM):"));
        panelFormulario.add(txtHora);
        panelFormulario.add(new JLabel("Servicio:"));
        panelFormulario.add(txtServicio);
        panelFormulario.add(new JLabel());
        panelFormulario.add(btnRegistrar);

        String[] columnas = {"ID", "Cliente", "Fecha", "Hora", "Servicio", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        JTable tablaTurnos = new JTable(modeloTabla);

        btnRegistrar.addActionListener(e -> {
            Cliente clienteSeleccionado = (Cliente) cbClientes.getSelectedItem();
            if (clienteSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (txtFecha.getText().trim().isEmpty() || txtHora.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fecha y Hora son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String idTurno = String.valueOf(gestor.obtenerTurnos().size() + 1);
            Turno nuevoTurno = new Turno(idTurno, clienteSeleccionado, txtFecha.getText(), txtHora.getText(), txtServicio.getText(), "Pendiente");
            gestor.registrarTurno(nuevoTurno);
            
            modeloTabla.addRow(new Object[]{nuevoTurno.getId(), clienteSeleccionado.getNombre(), nuevoTurno.getFecha(), nuevoTurno.getHora(), nuevoTurno.getServicio(), nuevoTurno.getEstado()});
            txtFecha.setText(""); txtHora.setText(""); txtServicio.setText("");
        });

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaTurnos), BorderLayout.CENTER);
        return panel;
    }

    private void actualizarComboClientes() {
        cbClientes.removeAllItems();
        for (Cliente c : gestor.obtenerClientes()) {
            cbClientes.addItem(c);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}