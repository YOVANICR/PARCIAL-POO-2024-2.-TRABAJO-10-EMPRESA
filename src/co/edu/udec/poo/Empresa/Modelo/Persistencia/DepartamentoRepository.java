/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Modelo.Persistencia;

import co.edu.udec.poo.Empresa.Modelo.Entidades.Departamento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoRepository {
    private static final String ARCHIVO_DEPARTAMENTOS = "departamentos.txt";
    private List<Departamento> departamentos;

    // *************************************************************************************
    // Aquí inicializo la lista de departamentos cargando los datos desde el archivo.
    // *************************************************************************************
    public DepartamentoRepository() {
        departamentos = cargarDepartamentos();
    }

    // *************************************************************************************
    // Agrego el nuevo departamento a la lista y luego guardo todos los departamentos.
    // *************************************************************************************
    public boolean agregar(Departamento departamento) {
        departamentos.add(departamento);
        return guardarDepartamentos();
    }

    // *************************************************************************************
    // Retorno una nueva lista con todos los departamentos que tengo en memoria.
    // *************************************************************************************
    public List<Departamento> listar() {
        return new ArrayList<>(departamentos);
    }

    // *************************************************************************************
    // Busco el departamento en la lista y lo actualizo con los nuevos datos si lo encuentro.
    // Si lo actualizo, guardo la lista de nuevo.
    // *************************************************************************************
    public boolean actualizar(Departamento departamento) {
        for (int i = 0; i < departamentos.size(); i++) {
            if (departamentos.get(i).getNombre().equalsIgnoreCase(departamento.getNombre())) {
                departamentos.set(i, departamento);
                return guardarDepartamentos();
            }
        }
        return false;
    }

    // *************************************************************************************
    // Si encuentro el departamento en la lista, lo elimino y guardo los cambios.
    // *************************************************************************************
    public boolean eliminar(Departamento departamento) {
        if (departamentos.remove(departamento)) {
            return guardarDepartamentos();
        }
        return false;
    }

    // *************************************************************************************
    // Busco un departamento por nombre. Si lo encuentro, lo retorno; si no, retorno null.
    // *************************************************************************************
    public Departamento buscar(String nombre) {
        for (Departamento departamento : departamentos) {
            if (departamento.getNombre().equalsIgnoreCase(nombre)) {
                return departamento;
            }
        }
        return null; // Si no se encuentra, retorno null
    }

    // *************************************************************************************
    // Este método guarda todos los departamentos en el archivo "departamentos.txt".
    // Si todo sale bien, retorna true, sino false.
    // *************************************************************************************
    private boolean guardarDepartamentos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_DEPARTAMENTOS))) {
            for (Departamento departamento : departamentos) {
                String jefeNombre = (departamento.getJefe() != null) ? departamento.getJefe().getNombre() : "null";
                writer.write(departamento.getNombre() + "," + departamento.getNumeroEmpleados() + "," + jefeNombre);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // *************************************************************************************
    // Este método carga los departamentos desde el archivo y los agrega a la lista.
    // Si el archivo no existe, retorna una lista vacía.
    // *************************************************************************************
    private List<Departamento> cargarDepartamentos() {
        List<Departamento> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO_DEPARTAMENTOS);

        if (!archivo.exists()) {
            return lista; // Si no existe el archivo, devuelvo una lista vacía
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3) {
                    String nombre = datos[0];
                    int numeroEmpleados = Integer.parseInt(datos[1]);
                    String jefeNombre = datos[2].equals("null") ? null : datos[2];

                    // Si hay un nombre de jefe, creo un objeto de jefe ficticio
                    co.edu.udec.poo.Empresa.Modelo.Entidades.Empleado jefe = 
                        (jefeNombre != null) ? new co.edu.udec.poo.Empresa.Modelo.Entidades.Empleado(jefeNombre, "Sin puesto", 0.0) : null;
                    lista.add(new Departamento(nombre, numeroEmpleados, jefe));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
