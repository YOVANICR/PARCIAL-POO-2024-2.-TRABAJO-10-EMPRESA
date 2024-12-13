/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Modelo.Entidades;

public class Empleado {

    private String nombre;
    private String puesto;
    private double salario;


    public Empleado(String nombre, String puesto, double salario) {
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
    }


    public String getNombre() {
        return nombre;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getPuesto() {
        return puesto;
    }


    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getSalario() {
        return salario;
    }

    // *************************************************************************************
    // Este método convierte toda la información del empleado en una cadena de texto.
    // *************************************************************************************
    @Override
    public String toString() {
        return "Empleado: " + nombre +
               ", Puesto: " + puesto +
               ", Salario: " + salario;
    }
}