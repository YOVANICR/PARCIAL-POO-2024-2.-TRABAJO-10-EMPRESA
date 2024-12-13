/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Vista;

import co.edu.udec.poo.Empresa.Controlador.DepartamentoControlador;
import co.edu.udec.poo.Empresa.Modelo.Entidades.Departamento;
import co.edu.udec.poo.Empresa.Modelo.Entidades.Empleado;
import co.edu.udec.poo.Empresa.Modelo.Persistencia.DepartamentoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioDepartamento extends JFrame {
    private DepartamentoControlador controlador;
    private JTextField txtNombre, txtNumEmpleados, txtJefe;
    private JButton btnCrear, btnActualizar, btnEliminar, btnListar;
    private JTable tablaDepartamentos;
    private DefaultTableModel modeloTabla;

    public FormularioDepartamento() {
        // *************************************************************************************
        // Configuración básica de la ventana: título, tamaño, y centrado en la pantalla.
        // *************************************************************************************
        setTitle("Gestión de Departamentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Conecto la lógica del controlador con este formulario.
        controlador = new DepartamentoControlador(new DepartamentoRepository());
        inicializarComponentes();
    }

    private ImageIcon cargarIcono(String ruta) {
        // *************************************************************************************
        // Este método sirve para cargar los íconos que uso en los botones. Si no encuentra el archivo, imprime un error pero sigue funcionando.
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
        // Parte superior: campos para ingresar datos del departamento. Uso un diseño en cuadrícula para organizar las etiquetas y los campos de texto.
        // *************************************************************************************
        JPanel panelEntrada = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        JLabel lblNumEmpleados = new JLabel("Número de Empleados:");
        txtNumEmpleados = new JTextField();
        JLabel lblJefe = new JLabel("Jefe del Departamento:");
        txtJefe = new JTextField();

        panelEntrada.add(lblNombre);
        panelEntrada.add(txtNombre);
        panelEntrada.add(lblNumEmpleados);
        panelEntrada.add(txtNumEmpleados);
        panelEntrada.add(lblJefe);
        panelEntrada.add(txtJefe);

        panel.add(panelEntrada, BorderLayout.NORTH);

        // *************************************************************************************
        // Parte central: tabla donde se muestran los departamentos registrados.
        // *************************************************************************************
        modeloTabla = new DefaultTableModel(new String[]{"Nombre", "N° Empleados", "Jefe"}, 0);
        tablaDepartamentos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaDepartamentos);

        panel.add(scrollTabla, BorderLayout.CENTER);

        // *************************************************************************************
        // Parte inferior: botones para ejecutar las operaciones CRUD.
        // *************************************************************************************
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnCrear = new JButton("Crear", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/crear.png"));
        btnActualizar = new JButton("Actualizar", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/actualizar.png"));
        btnEliminar = new JButton("Eliminar", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/eliminar.png"));
        btnListar = new JButton("Listar", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/listar.png"));

        // Desactivo los botones de actualizar y eliminar hasta que se seleccione un departamento.
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        // *************************************************************************************
        // Asigno las acciones a cada botón para que realicen las operaciones correspondientes.
        // *************************************************************************************
        btnCrear.addActionListener(this::crearDepartamento);
        btnActualizar.addActionListener(this::actualizarDepartamento);
        btnEliminar.addActionListener(this::eliminarDepartamento);
        btnListar.addActionListener(this::listarDepartamentos);

        // Si selecciono una fila en la tabla, los datos se cargan automáticamente.
        tablaDepartamentos.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());
    }

    private void crearDepartamento(ActionEvent e) {
        // *************************************************************************************
        // Recojo los datos ingresados, los valido y creo un nuevo departamento si todo está bien.
        // *************************************************************************************
        String nombre = txtNombre.getText();
        int numEmpleados;
        String jefe = txtJefe.getText();

        try {
            numEmpleados = Integer.parseInt(txtNumEmpleados.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El número de empleados debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empleado jefeDepartamento = new Empleado(jefe, "Jefe", 0);

        if (controlador.agregarDepartamento(new Departamento(nombre, numEmpleados, jefeDepartamento))) {
            JOptionPane.showMessageDialog(this, "Departamento creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            listarDepartamentos(null);
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear el departamento. Verifique los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarDepartamento(ActionEvent e) {
        // *************************************************************************************
        //  permitO actualizar un departamento seleccionado. Primero se valida que haya algo seleccionado y luego se actualizan los datos.
        // *************************************************************************************
        int filaSeleccionada = tablaDepartamentos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un departamento para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText();
        int numEmpleados;
        String jefe = txtJefe.getText();

        try {
            numEmpleados = Integer.parseInt(txtNumEmpleados.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El número de empleados debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empleado jefeDepartamento = new Empleado(jefe, "Jefe", 0);

        if (controlador.actualizarDepartamento(nombre, numEmpleados, jefeDepartamento)) {
            JOptionPane.showMessageDialog(this, "Departamento actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            listarDepartamentos(null);
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el departamento. Verifique los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDepartamento(ActionEvent e) {
        // *************************************************************************************
        // elimino un departamento seleccionado. Si no hay nada seleccionado,muestra un mensaje de error.
        // *************************************************************************************
        int filaSeleccionada = tablaDepartamentos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un departamento para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);

        if (controlador.eliminarDepartamento(nombre)) {
            JOptionPane.showMessageDialog(this, "Departamento eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            listarDepartamentos(null);
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar el departamento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarDepartamentos(ActionEvent e) {
        // *************************************************************************************
        // Limpio la tabla y la vuelvo a llenar con los departamentos registrados.
        // *************************************************************************************
        modeloTabla.setRowCount(0);
        controlador.listarDepartamentos().forEach(departamento -> modeloTabla.addRow(
                new Object[]{departamento.getNombre(), departamento.getNumeroEmpleados(), departamento.getJefe().getNombre()}
        ));
    }

    private void cargarDatosSeleccionados() {
        // *************************************************************************************
        // Carga los datos del departamento seleccionado en los campos de texto para que puedan ser editados o eliminados.
        // *************************************************************************************
        int filaSeleccionada = tablaDepartamentos.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            int numEmpleados = (int) modeloTabla.getValueAt(filaSeleccionada, 1);
            String jefe = (String) modeloTabla.getValueAt(filaSeleccionada, 2);

            txtNombre.setText(nombre);
            txtNumEmpleados.setText(String.valueOf(numEmpleados));
            txtJefe.setText(jefe);

            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
        } else {
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        // *************************************************************************************
        // Limpia los campos de texto y desactiva los botones para evitar errores.
        // *************************************************************************************
        txtNombre.setText("");
        txtNumEmpleados.setText("");
        txtJefe.setText("");
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
}