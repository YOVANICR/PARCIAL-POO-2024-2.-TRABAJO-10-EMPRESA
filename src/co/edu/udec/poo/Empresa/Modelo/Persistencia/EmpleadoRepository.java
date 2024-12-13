/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Modelo.Persistencia;

import co.edu.udec.poo.Empresa.Modelo.Entidades.Empleado;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepository {
    private static final String ARCHIVO_EMPLEADOS = "empleados.txt";
    private List<Empleado> empleados;

    public EmpleadoRepository() {
        empleados = cargarEmpleados(); // Cargamos los empleados desde el archivo al iniciar
    }

    public boolean agregar(Empleado empleado) {
        empleados.add(empleado); // Añado el nuevo empleado a la lista
        return guardarEmpleados(); // Guardo la lista actualizada en el archivo
    }

    public List<Empleado> listar() {
        return new ArrayList<>(empleados); // Retorno una copia de la lista para evitar modificaciones externas
    }

    public Empleado buscar(String nombre) {
        for (Empleado empleado : empleados) {
            if (empleado.getNombre().equalsIgnoreCase(nombre)) {
                return empleado; // Si el nombre coincide, devuelvo el empleado
            }
        }
        return null; // Si no lo encuentro, devuelvo null
    }

    public boolean actualizar(Empleado empleado) {
        Empleado existente = buscar(empleado.getNombre());
        if (existente != null) {
            empleados.remove(existente); // Si lo encontré, lo elimino de la lista
            empleados.add(empleado); // Agrego el empleado actualizado
            return guardarEmpleados(); // Guardo la lista actualizada en el archivo
        }
        return false; // Si no encontré el empleado, retorno false
    }

    public boolean eliminar(Empleado empleado) {
        boolean eliminado = empleados.remove(empleado); // Intento eliminar el empleado de la lista
        if (eliminado) {
            return guardarEmpleados(); // Si lo eliminé, guardo la lista actualizada
        }
        return false; // Si no lo encontré para eliminar, retorno false
    }

    // *************************************************************************************
    //  guardO la lista de empleados en el archivo de texto
    // *************************************************************************************
    private boolean guardarEmpleados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_EMPLEADOS))) {
            for (Empleado empleado : empleados) {
                writer.write(empleado.getNombre() + "," + empleado.getPuesto() + "," + empleado.getSalario());
                writer.newLine(); // Escribo cada empleado en una nueva línea
            }
            return true; // Si no hay errores, devuelvo true
        } catch (IOException e) {
            e.printStackTrace(); // Si ocurre un error al guardar, lo imprimo
            return false; // Retorno false si hubo error
        }
    }

    // *************************************************************************************
    //  cargO los empleados desde el archivo de texto
    // *************************************************************************************
    private List<Empleado> cargarEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_EMPLEADOS);

        if (!archivo.exists()) {
            return lista; // Si no existe el archivo, retorno una lista vacía
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(","); // Separo los datos por comas
                if (datos.length == 3) {
                    String nombre = datos[0];
                    String puesto = datos[1];
                    double salario = Double.parseDouble(datos[2]); // Convierto el salario a double
                    lista.add(new Empleado(nombre, puesto, salario)); // Añado el empleado a la lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Si hay algún error al leer el archivo, lo imprimo
        }
        return lista; // Retorno la lista de empleados cargada
    }
}
