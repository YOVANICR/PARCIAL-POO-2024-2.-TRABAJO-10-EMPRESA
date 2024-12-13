/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Modelo.Entidades;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String nombre;
    private String sector;
    private int numeroEmpleados;
    private List<Departamento> departamentos; // Lista para manejar los departamentos de la empresa.


    public Empresa(String nombre, String sector, int numeroEmpleados) {
        this.nombre = nombre;
        this.sector = sector;
        this.numeroEmpleados = numeroEmpleados;
        this.departamentos = new ArrayList<>();
    }

   
    public String getNombre() {
        return nombre;
    }

  
    public void setSector(String sector) {
        this.sector = sector;
    }

    
    public String getSector() {
        return sector;
    }

    
    public void setNumeroEmpleados(int numeroEmpleados) {
        this.numeroEmpleados = numeroEmpleados;
    }

    
    public int getNumeroEmpleados() {
        return numeroEmpleados;
    }

   
    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    
    public boolean agregarDepartamento(Departamento departamento) {
        return departamentos.add(departamento); // Uso el método add de la lista.
    }

    
    public int calcularNumeroTotalEmpleados() {
        return departamentos.stream()
                .mapToInt(Departamento::getNumeroEmpleados) // Aquí recojo el número de empleados de cada departamento.
                .sum(); // Sumo todos esos números.
    }

    // Represento la información de la empresa en formato de texto
    @Override
    public String toString() {
        return "Empresa: " + nombre +
               ", Sector: " + sector +
               ", Número de Empleados: " + numeroEmpleados;
    }
}
