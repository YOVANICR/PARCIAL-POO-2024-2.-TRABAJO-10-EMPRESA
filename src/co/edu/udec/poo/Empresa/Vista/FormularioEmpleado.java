/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Vista;

import co.edu.udec.poo.Empresa.Controlador.EmpleadoControlador;
import co.edu.udec.poo.Empresa.Modelo.Entidades.Empleado;
import co.edu.udec.poo.Empresa.Modelo.Persistencia.EmpleadoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioEmpleado extends JFrame {
    private EmpleadoControlador controlador;
    private JTextField txtNombre, txtPuesto, txtSalario;
    private JButton btnCrear, btnActualizar, btnEliminar, btnListar;
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;

    public FormularioEmpleado() {
        // *************************************************************************************
        // Configuro la ventana con su título, tamaño, y la centro para que sea más amigable.
        // *************************************************************************************
        setTitle("Gestión de Empleados");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Aquí conecto la lógica del negocio con la interfaz gráfica.
        controlador = new EmpleadoControlador(new EmpleadoRepository());
        inicializarComponentes();
    }

    private ImageIcon cargarIcono(String ruta) {
        // *************************************************************************************
        // Este método carga íconos para los botones desde la ruta que asigné. Si no encuentra el archivo, imprime un error pero sigue funcionando.
        // *************************************************************************************
        java.net.URL url = getClass().getResource(ruta);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.err.println("No se encontró el ícono en la ruta: " + ruta);
            return null;
        }
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout());

        // *************************************************************************************
        // Parte superior de la ventana donde los usuarios ingresan los datos del empleado. Uso un diseño de cuadrícula para organizar los campos de entrada.
        // *************************************************************************************
        JPanel panelEntrada = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        JLabel lblPuesto = new JLabel("Puesto:");
        txtPuesto = new JTextField();
        JLabel lblSalario = new JLabel("Salario:");
        txtSalario = new JTextField();

        panelEntrada.add(lblNombre);
        panelEntrada.add(txtNombre);
        panelEntrada.add(lblPuesto);
        panelEntrada.add(txtPuesto);
        panelEntrada.add(lblSalario);
        panelEntrada.add(txtSalario);

        panel.add(panelEntrada, BorderLayout.NORTH);

        // *************************************************************************************
        // Centro de la ventana: una tabla para listar empleados con sus datos organizados.
        // *************************************************************************************
        modeloTabla = new DefaultTableModel(new String[]{"Nombre", "Puesto", "Salario"}, 0);
        tablaEmpleados = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaEmpleados);

        panel.add(scrollTabla, BorderLayout.CENTER);

        // *************************************************************************************
        // Parte inferior: botones de acción para CRUD. Cada botón tiene su ícono.
        // *************************************************************************************
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnCrear = new JButton("Crear", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/crear.png"));
        btnActualizar = new JButton("Actualizar", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/actualizar.png"));
        btnEliminar = new JButton("Eliminar", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/eliminar.png"));
        btnListar = new JButton("Listar", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/listar.png"));

        // Estos botones empiezan desactivados para evitar errores si no hay datos seleccionados.
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        // *************************************************************************************
        // Asigno las acciones a los botones usando métodos específicos.
        // *************************************************************************************
        btnCrear.addActionListener(this::crearEmpleado);
        btnActualizar.addActionListener(this::actualizarEmpleado);
        btnEliminar.addActionListener(this::eliminarEmpleado);
        btnListar.addActionListener(this::listarEmpleados);

        // Cuando selecciono un empleado de la tabla, cargo los datos automáticamente.
        tablaEmpleados.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());
    }

    private void crearEmpleado(ActionEvent e) {
        // *************************************************************************************
        // Aquí recojo los datos ingresados por el usuario, los valido y los guardo como un nuevo empleado si todo está correcto.
        // *************************************************************************************
        String nombre = txtNombre.getText();
        String puesto = txtPuesto.getText();
        double salario;

        try {
            salario = Double.parseDouble(txtSalario.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El salario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empleado nuevoEmpleado = new Empleado(nombre, puesto, salario);

        if (controlador.agregarEmpleado(nuevoEmpleado)) {
            JOptionPane.showMessageDialog(this, "Empleado creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            listarEmpleados(null);
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear el empleado. Verifique los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEmpleado(ActionEvent e) {
        // *************************************************************************************
        // Este método permite modificar un empleado seleccionado en la tabla. Valida los nuevos datos antes de actualizarlos.
        // *************************************************************************************
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText();
        String puesto = txtPuesto.getText();
        double salario;

        try {
            salario = Double.parseDouble(txtSalario.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El salario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (controlador.actualizarEmpleado(nombre, puesto, salario)) {
            JOptionPane.showMessageDialog(this, "Empleado actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            listarEmpleados(null);
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el empleado. Verifique los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEmpleado(ActionEvent e) {
        // *************************************************************************************
        // Antes de eliminar, confirmo que haya un empleado seleccionado.Si todo está bien, procedo con la eliminación.
        // *************************************************************************************
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);

        if (controlador.eliminarEmpleado(nombre)) {
            JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            listarEmpleados(null);
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarEmpleados(ActionEvent e) {
        // *************************************************************************************
        // Aquí actualizo la tabla con todos los empleados registrados.
        // *************************************************************************************
        modeloTabla.setRowCount(0); // Limpio la tabla antes de llenarla
        controlador.listarEmpleados().forEach(empleado -> modeloTabla.addRow(
                new Object[]{empleado.getNombre(), empleado.getPuesto(), empleado.getSalario()}
        ));
    }

    private void cargarDatosSeleccionados() {
        // *************************************************************************************
        // Este método carga automáticamente los datos de la fila seleccionadaen los campos de texto para editarlos o eliminarlos.
        // *************************************************************************************
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String puesto = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            double salario = (double) modeloTabla.getValueAt(filaSeleccionada, 2);

            txtNombre.setText(nombre);
            txtPuesto.setText(puesto);
            txtSalario.setText(String.valueOf(salario));

            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
        } else {
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        // *************************************************************************************
        // Limpio los campos y desactivo los botones para evitar erroressi no hay datos seleccionados o válidos.
        // *************************************************************************************
        txtNombre.setText("");
        txtPuesto.setText("");
        txtSalario.setText("");
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
}