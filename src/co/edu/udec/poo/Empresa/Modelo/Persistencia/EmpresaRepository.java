/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Modelo.Persistencia;

import co.edu.udec.poo.Empresa.Modelo.Entidades.Empresa;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepository {
    private final String archivo = "empresa.txt";
    private List<Empresa> empresas;

    // *************************************************************************************
    // En el constructor, inicializo la lista de empresas y cargo las que ya están guardadas
    // *************************************************************************************
    public EmpresaRepository() {
        this.empresas = new ArrayList<>();
        cargarDesdeArchivo(); // Cargar las empresas desde el archivo al inicio
    }

    // *************************************************************************************
    // Aquí agrego una nueva empresa a la lista y luego guardo los cambios en el archivo
    // *************************************************************************************
    public boolean agregar(Empresa empresa) {
        empresas.add(empresa);
        return guardarEnArchivo(); // Guardo los cambios después de agregar
    }

    // *************************************************************************************
    // Este método busca una empresa por nombre. Si la encuentra, la devuelve, si no, retorna null
    // *************************************************************************************
    public Empresa buscar(String nombre) {
        return empresas.stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre)) // Busco por nombre sin distinguir mayúsculas
                .findFirst() // Devuelvo la primera coincidencia
                .orElse(null); // Si no la encuentro, devuelvo null
    }

    // *************************************************************************************
    // Actualizo los datos de una empresa. Si existe, la modifico y guardo los cambios en el archivo
    // *************************************************************************************
    public boolean actualizar(Empresa empresa) {
        Empresa existente = buscar(empresa.getNombre()); // Busco la empresa por nombre
        if (existente != null) {
            // Si la empresa existe, actualizo sus datos
            existente.setSector(empresa.getSector());
            existente.setNumeroEmpleados(empresa.getNumeroEmpleados());
            return guardarEnArchivo(); // Guardo los cambios en el archivo
        }
        return false; // Si no la encontré, retorno false
    }

    // *************************************************************************************
    // Elimino una empresa de la lista. Si se elimina correctamente, guardo los cambios
    // *************************************************************************************
    public boolean eliminar(Empresa empresa) {
        if (empresas.remove(empresa)) { // Intento eliminar la empresa de la lista
            return guardarEnArchivo(); // Si se eliminó, guardo los cambios
        }
        return false; // Si no se eliminó, retorno false
    }

    // *************************************************************************************
    // Este método devuelve la lista completa de empresas
    // *************************************************************************************
    public List<Empresa> listar() {
        return empresas; // Retorno la lista completa de empresas
    }

    // *************************************************************************************
    // Guardo todas las empresas en el archivo. Escribo cada empresa con sus datos separados por ";"
    // *************************************************************************************
    private boolean guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Empresa empresa : empresas) {
                // Escribo cada empresa en una nueva línea en el archivo
                writer.write(empresa.getNombre() + ";" + empresa.getSector() + ";" + empresa.getNumeroEmpleados());
                writer.newLine();
            }
            return true; // Si todo salió bien, retorno true
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Si ocurrió un error, retorno false
        }
    }

    // *************************************************************************************
    // Este método carga las empresas desde el archivo si ya existen. Si el archivo no está, se queda vacío
    // *************************************************************************************
    private void cargarDesdeArchivo() {
        File archivoEmpresas = new File(archivo);
        if (archivoEmpresas.exists()) { // Verifico si el archivo ya existe
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) { // Leo cada línea del archivo
                    String[] partes = linea.split(";"); // Separo los datos de cada empresa por ";"
                    if (partes.length == 3) { // Si la línea tiene el formato correcto
                        String nombre = partes[0];
                        String sector = partes[1];
                        int numEmpleados = Integer.parseInt(partes[2]);
                        empresas.add(new Empresa(nombre, sector, numEmpleados)); // Creo la empresa y la agrego a la lista
                    }
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace(); // Si hay algún error al leer o convertir, lo imprimo
            }
        }
    }
}
