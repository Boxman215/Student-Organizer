package vista;

import controlador.ControladorTareas;
import modelo.Tarea;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana para registrar tareas, verlas en una lista,
 * filtrarlas por pendientes y marcarlas como completadas.
 */
public class VistaTareas extends JFrame {

    private final ControladorTareas controlador;

    private JTextField campoNombre;
    private JTextField campoFecha;
    private JComboBox<String> comboPrioridad;
    private DefaultListModel<Tarea> modeloLista;
    private JList<Tarea> listaTareas;

    public VistaTareas(ControladorTareas controlador) {
        this.controlador = controlador;
        configurarVentana();
        cargarTareas();
    }

    private void configurarVentana() {
        setTitle("Student Organizer - Tareas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 460);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(construirPanelFormulario(), BorderLayout.NORTH);
        add(construirPanelLista(), BorderLayout.CENTER);
        add(construirPanelAcciones(), BorderLayout.SOUTH);
    }

    private JPanel construirPanelFormulario() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Registrar nueva tarea"));

        campoNombre = new JTextField();
        campoFecha = new JTextField();
        comboPrioridad = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});

        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Fecha entrega (AAAA-MM-DD):"));
        panel.add(campoFecha);
        panel.add(new JLabel("Prioridad:"));
        panel.add(comboPrioridad);

        return panel;
    }

    private JScrollPane construirPanelLista() {
        modeloLista = new DefaultListModel<>();
        listaTareas = new JList<>(modeloLista);
        return new JScrollPane(listaTareas);
    }

    private JPanel construirPanelAcciones() {
        JPanel panel = new JPanel();

        JButton botonRegistrar = new JButton("Registrar tarea");
        botonRegistrar.addActionListener(e -> registrarTarea());

        JButton botonCompletar = new JButton("Marcar como completada");
        botonCompletar.addActionListener(e -> marcarCompletada());

        JButton botonPendientes = new JButton("Ver solo pendientes");
        botonPendientes.addActionListener(e -> mostrarPendientes());

        JButton botonTodas = new JButton("Ver todas");
        botonTodas.addActionListener(e -> cargarTareas());

        panel.add(botonRegistrar);
        panel.add(botonCompletar);
        panel.add(botonPendientes);
        panel.add(botonTodas);

        return panel;
    }

    private void registrarTarea() {
        try {
            controlador.registrarTarea(
                campoNombre.getText(),
                campoFecha.getText(),
                (String) comboPrioridad.getSelectedItem()
            );
            campoNombre.setText("");
            campoFecha.setText("");
            cargarTareas();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Datos inválidos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void marcarCompletada() {
        Tarea seleccionada = listaTareas.getSelectedValue();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(this, "Selecciona una tarea de la lista.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            controlador.marcarCompletada(seleccionada);
            cargarTareas();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarPendientes() {
        actualizarLista(controlador.consultarPendientes());
    }

    private void cargarTareas() {
        actualizarLista(controlador.getListaTareas());
    }

    private void actualizarLista(List<Tarea> tareas) {
        modeloLista.clear();
        for (Tarea tarea : tareas) {
            modeloLista.addElement(tarea);
        }
    }
}
