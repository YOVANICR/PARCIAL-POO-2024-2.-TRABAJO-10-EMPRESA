/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Controlador;

import co.edu.udec.poo.Empresa.Modelo.Entidades.Empleado;
import co.edu.udec.poo.Empresa.Modelo.Persistencia.EmpleadoRepository;

public class EmpleadoControlador {
    private EmpleadoRepository repositorio;

    // *************************************************************************************
    // Aquí inicializo el repositorio para poder gestionar los empleados más adelante
    // *************************************************************************************
    public EmpleadoControlador(EmpleadoRepository repositorio) {
        this.repositorio = repositorio;
    }

    // *************************************************************************************
    // En este método agrego un nuevo empleado al repositorio, solo paso el objeto empleado
    // *************************************************************************************
    public boolean agregarEmpleado(Empleado empleado) {
        return repositorio.agregar(empleado);
    }

    // *************************************************************************************
    // Si un empleado existe, actualizo su puesto y salario, luego lo guardo de nuevo en el repositorio
    // *************************************************************************************
    public boolean actualizarEmpleado(String nombre, String nuevoPuesto, double nuevoSalario) {
        Empleado empleado = repositorio.buscar(nombre);
        if (empleado != null) {
            empleado.setPuesto(nuevoPuesto);
            empleado.setSalario(nuevoSalario);
            return repositorio.actualizar(empleado);
        }
        return false;
    }

    // *************************************************************************************
    // Busco al empleado por su nombre y, si lo encuentro, lo elimino del repositorio
    // *************************************************************************************
    public boolean eliminarEmpleado(String nombre) {
        Empleado empleado = repositorio.buscar(nombre);
        if (empleado != null) {
            return repositorio.eliminar(empleado);
        }
        return false;
    }

    // *************************************************************************************
    // Retorno la lista de todos los empleados almacenados en el repositorio
    // *************************************************************************************
    public java.util.List<Empleado> listarEmpleados() {
        return repositorio.listar();
    }
}
