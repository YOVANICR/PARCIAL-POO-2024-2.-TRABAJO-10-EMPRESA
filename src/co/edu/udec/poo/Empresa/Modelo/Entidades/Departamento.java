/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Modelo.Entidades;

public class Departamento {
    private String nombre;
    private int numeroEmpleados;
    private Empleado jefe;

    public Departamento(String nombre, int numeroEmpleados, Empleado jefe) {
        this.nombre = nombre;
        this.numeroEmpleados = numeroEmpleados;
        this.jefe = jefe;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public int getNumeroEmpleados() {
        return numeroEmpleados;
    }

    public void setNumeroEmpleados(int numeroEmpleados) {
        this.numeroEmpleados = numeroEmpleados; // Actualiza el número de empleados
    }

    public Empleado getJefe() {
        return jefe;
    }

    public void setJefe(Empleado jefe) {
        this.jefe = jefe; // Actualiza el jefe
    }

    // Método toString
    @Override
    public String toString() {
        return "Departamento: " + nombre +
               ", Número de Empleados: " + numeroEmpleados +
               ", Jefe: " + (jefe != null ? jefe.getNombre() : "Sin jefe asignado");
    }
}
