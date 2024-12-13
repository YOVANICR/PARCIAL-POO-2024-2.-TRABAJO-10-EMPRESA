/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Vista;

import co.edu.udec.poo.Empresa.Controlador.EmpresaControlador;
import co.edu.udec.poo.Empresa.Modelo.Entidades.Empresa;
import co.edu.udec.poo.Empresa.Modelo.Persistencia.EmpresaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FormularioEmpresa extends JFrame {
    private EmpresaControlador controlador;
    private JTextField txtNombre, txtSector, txtNumEmpleados;
    private JButton btnCrear, btnActualizar, btnEliminar, btnListar;
    private JTable tablaEmpresas;
    private DefaultTableModel modeloTabla;

    public FormularioEmpresa() {
        // *************************************************************************************
        // Configuro el título de la ventana, su tamaño inicial, 
        // y me aseguro de que esté centrada al abrirse.
        // *************************************************************************************
        setTitle("Gestión de Empresas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creo el controlador que conecta la lógica de negocio con la vista.
        controlador = new EmpresaControlador(new EmpresaRepository());
        inicializarComponentes();
    }

    private ImageIcon cargarIcono(String ruta) {
        // *************************************************************************************
        // Aquí trato de cargar los íconos desde la ruta que les asigné.
        // Si el archivo no se encuentra, simplemente imprimo un error en la consola.
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
        // Esta sección superior permite a los usuarios ingresar datos de las empresas 
        // usando campos de texto bien organizados.
        // *************************************************************************************
        JPanel panelEntrada = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        JLabel lblSector = new JLabel("Sector:");
        txtSector = new JTextField();
        JLabel lblNumEmpleados = new JLabel("Número de Empleados:");
        txtNumEmpleados = new JTextField();

        panelEntrada.add(lblNombre);
        panelEntrada.add(txtNombre);
        panelEntrada.add(lblSector);
        panelEntrada.add(txtSector);
        panelEntrada.add(lblNumEmpleados);
        panelEntrada.add(txtNumEmpleados);

        panel.add(panelEntrada, BorderLayout.NORTH);

        // *************************************************************************************
        // Diseño la tabla que muestra todas las empresas registradas. 
        // Esto ayuda a visualizar y seleccionar datos fácilmente.
        // *************************************************************************************
        modeloTabla = new DefaultTableModel(new String[]{"Nombre", "Sector", "N° Empleados"}, 0);
        tablaEmpresas = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaEmpresas);

        panel.add(scrollTabla, BorderLayout.CENTER);

        // *************************************************************************************
        // Aquí configuro los botones de acción: Crear, Actualizar, Eliminar y Listar.
        // Cada botón tiene un ícono para hacerlo más visual y atractivo.
        // *************************************************************************************
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnCrear = new JButton("Crear", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/crear.png"));
        btnActualizar = new JButton("Actualizar", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/actualizar.png"));
        btnEliminar = new JButton("Eliminar", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/eliminar.png"));
        btnListar = new JButton("Listar", cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/listar.png"));

        // Desactivo inicialmente estos botones para evitar errores sin datos seleccionados.
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);

        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);

        // *************************************************************************************
        // Asigno acciones a cada botón usando métodos específicos 
        // para crear, actualizar, eliminar y listar empresas.
        // *************************************************************************************
        btnCrear.addActionListener(this::crearEmpresa);
        btnActualizar.addActionListener(this::actualizarEmpresa);
        btnEliminar.addActionListener(this::eliminarEmpresa);
        btnListar.addActionListener(this::listarEmpresas);

        // Permito que, al seleccionar una fila de la tabla, los datos se carguen automáticamente
        // en los campos de texto para poder editarlos o eliminarlos.
        tablaEmpresas.getSelectionModel().addListSelectionListener(e -> cargarDatosSeleccionados());
    }

    private void crearEmpresa(ActionEvent e) {
        // *************************************************************************************
        // Este método valida los datos ingresados y, si son correctos, crea una nueva empresa
        // usando el controlador para guardar la información.
        // *************************************************************************************
        String nombre = txtNombre.getText();
        String sector = txtSector.getText();
        int numEmpleados;

        try {
            numEmpleados = Integer.parseInt(txtNumEmpleados.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El número de empleados debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empresa nuevaEmpresa = new Empresa(nombre, sector, numEmpleados);

        if (controlador.agregarEmpresa(nuevaEmpresa)) {
            JOptionPane.showMessageDialog(this, "Empresa creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            listarEmpresas(null);
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear la empresa. Verifique los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEmpresa(ActionEvent e) {
        // *************************************************************************************
        // Aquí se toman los datos modificados por el usuario para actualizar una empresa
        // seleccionada en la tabla. Los cambios se reflejan tras validar la entrada.
        // *************************************************************************************
        int filaSeleccionada = tablaEmpresas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una empresa para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText();
        String sector = txtSector.getText();
        int numEmpleados;

        try {
            numEmpleados = Integer.parseInt(txtNumEmpleados.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El número de empleados debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (controlador.actualizarEmpresa(nombre, sector, numEmpleados)) {
            JOptionPane.showMessageDialog(this, "Empresa actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            listarEmpresas(null);
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar la empresa. Verifique los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEmpresa(ActionEvent e) {
        // *************************************************************************************
        // Antes de eliminar, me aseguro de que el usuario haya seleccionado una empresa
        // de la tabla. La eliminación es permanente una vez confirmada.
        // *************************************************************************************
        int filaSeleccionada = tablaEmpresas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una empresa para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);

        if (controlador.eliminarEmpresa(nombre)) {
            JOptionPane.showMessageDialog(this, "Empresa eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            listarEmpresas(null);
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar la empresa.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarEmpresas(ActionEvent e) {
        // *************************************************************************************
        // Este método actualiza la tabla para mostrar todas las empresas registradas.
        // *************************************************************************************
        modeloTabla.setRowCount(0); // Limpio la tabla antes de listar
        controlador.listarEmpresas().forEach(empresa -> modeloTabla.addRow(
                new Object[]{empresa.getNombre(), empresa.getSector(), empresa.getNumeroEmpleados()}
        ));
    }

    private void cargarDatosSeleccionados() {
        // *************************************************************************************
        // Si el usuario selecciona una fila, los datos se cargan automáticamente
        // en los campos de texto para facilitar su edición o eliminación.
        // *************************************************************************************
        int filaSeleccionada = tablaEmpresas.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String sector = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            int numEmpleados = (int) modeloTabla.getValueAt(filaSeleccionada, 2);

            txtNombre.setText(nombre);
            txtSector.setText(sector);
            txtNumEmpleados.setText(String.valueOf(numEmpleados));

            btnActualizar.setEnabled(true);
            btnEliminar.setEnabled(true);
        } else {
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        // *************************************************************************************
        // Limpio los campos y desactivo botones para evitar que el usuario
        // realice acciones con datos inválidos.
        // *************************************************************************************
        txtNombre.setText("");
        txtSector.setText("");
        txtNumEmpleados.setText("");
        btnActualizar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }
}