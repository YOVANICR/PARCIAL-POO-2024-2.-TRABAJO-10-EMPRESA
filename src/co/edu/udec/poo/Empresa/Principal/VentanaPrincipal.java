/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Principal;

import co.edu.udec.poo.Empresa.Vista.FormularioDepartamento;
import co.edu.udec.poo.Empresa.Vista.FormularioEmpleado;
import co.edu.udec.poo.Empresa.Vista.FormularioEmpresa;

import javax.swing.*;
import java.awt.*;

// *************************************************************************************
// Esta es la ventana principal de la aplicación, desde aquí manejo toda la navegación
// *************************************************************************************
public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        // *************************************************************************************
        // Aquí configuro la ventana principal, le doy título, tamaño y la centro en la pantalla
        // *************************************************************************************
        setTitle("Gestión de Empresa - Ventana Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes(); // Llamo a mi método que organiza los elementos
    }

    private void inicializarComponentes() {
        // *************************************************************************************
        // Este es el panel principal donde añado todo lo visual. Incluye una imagen de fondo.
        // *************************************************************************************
        JPanel panelPrincipal = new JPanel() {
            private Image imagenFondo = cargarImagen("/co/edu/udec/poo/Empresa/Vistas/Iconos/fondo.jpg");

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    // Dibujo la imagen de fondo para que se ajuste al tamaño de la ventana
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelPrincipal.setLayout(new BorderLayout());

        // *************************************************************************************
        // Aquí está el panel superior con los botones para navegar entre las opciones principales.
        // *************************************************************************************
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setOpaque(false); // Este panel es transparente, así no tapa el fondo
        JButton btnEmpresa = new JButton("Empresa");
        JButton btnDepartamento = new JButton("Departamento");
        JButton btnEmpleado = new JButton("Empleado");

        // Le pongo íconos a cada botón para que sean más visuales y llamativos
        btnEmpresa.setIcon(cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/empresa.png"));
        btnDepartamento.setIcon(cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/departamento.png"));
        btnEmpleado.setIcon(cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/empleado.png"));

        // Agrego los botones al panel
        panelBotones.add(btnEmpresa);
        panelBotones.add(btnDepartamento);
        panelBotones.add(btnEmpleado);

        // *************************************************************************************
        // Este es el panel central donde pongo mi información personal y la hago destacar.
        // *************************************************************************************
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setOpaque(false); // También transparente, para que no bloquee el fondo
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelTexto = new JPanel();
        panelTexto.setBackground(new Color(0, 0, 0, 150)); // Fondo negro pero semitransparente
        panelTexto.setLayout(new BoxLayout(panelTexto, BoxLayout.Y_AXIS));
        panelTexto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // *************************************************************************************
        // Aquí cargo mi foto y la agrego al centro de la ventana para que sea lo primero que se vea.
        // *************************************************************************************
        JLabel lblImagen = new JLabel();
        lblImagen.setIcon(cargarIcono("/co/edu/udec/poo/Empresa/Vistas/Iconos/mi_imagen.png"));
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        // *************************************************************************************
        // Estas son las etiquetas con mi información personal, las configuro para que se vean bien.
        // *************************************************************************************
        JLabel lblNombre = new JLabel("Yovani Enrique Castro Rios");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setForeground(Color.WHITE); // El texto es blanco para que destaque
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUniversidad = new JLabel("Universidad de Cartagena");
        lblUniversidad.setFont(new Font("Arial", Font.PLAIN, 16));
        lblUniversidad.setForeground(Color.WHITE);
        lblUniversidad.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCarrera = new JLabel("Ingeniería de Software");
        lblCarrera.setFont(new Font("Arial", Font.PLAIN, 16));
        lblCarrera.setForeground(Color.WHITE);
        lblCarrera.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblAsignatura = new JLabel("Programación Orientada a Objetos");
        lblAsignatura.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAsignatura.setForeground(Color.WHITE);
        lblAsignatura.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agrego mis datos al panel de texto
        panelTexto.add(lblNombre);
        panelTexto.add(Box.createRigidArea(new Dimension(0, 5))); // Espaciado
        panelTexto.add(lblUniversidad);
        panelTexto.add(lblCarrera);
        panelTexto.add(lblAsignatura);

        // Ahora agrego todo al panel central
        panelCentro.add(lblImagen);
        panelCentro.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado adicional
        panelCentro.add(panelTexto);

        // *************************************************************************************
        // Agrego los paneles superior y central al panel principal
        // *************************************************************************************
        panelPrincipal.add(panelBotones, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // Configuro todo el contenido de la ventana
        add(panelPrincipal);

        // *************************************************************************************
        // Aquí configuro lo que pasa cuando presiono los botones del panel superior
        // *************************************************************************************
        btnEmpresa.addActionListener(e -> new FormularioEmpresa().setVisible(true));
        btnDepartamento.addActionListener(e -> new FormularioDepartamento().setVisible(true));
        btnEmpleado.addActionListener(e -> new FormularioEmpleado().setVisible(true));
    }

    // *************************************************************************************
    // Método para cargar íconos desde la carpeta de recursos
    // *************************************************************************************
    private ImageIcon cargarIcono(String ruta) {
        java.net.URL url = getClass().getResource(ruta);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.err.println("No se encontró el ícono en la ruta: " + ruta);
            return null;
        }
    }

    // *************************************************************************************
    // Método para cargar la imagen de fondo
    // *************************************************************************************
    private Image cargarImagen(String ruta) {
        java.net.URL url = getClass().getResource(ruta);
        if (url != null) {
            return new ImageIcon(url).getImage();
        } else {
            System.err.println("No se encontró la imagen en la ruta: " + ruta);
            return null;
        }
    }

    // *************************************************************************************
    // Este es el método principal que arranca la aplicación
    // *************************************************************************************
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}
