/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Controlador;

import co.edu.udec.poo.Empresa.Modelo.Entidades.Departamento;
import co.edu.udec.poo.Empresa.Modelo.Entidades.Empleado;
import co.edu.udec.poo.Empresa.Modelo.Persistencia.DepartamentoRepository;

public class DepartamentoControlador {
    private DepartamentoRepository repositorio;

    // *************************************************************************************
    // Aquí inicializo el repositorio que me permitirá gestionar los departamentos
    // *************************************************************************************
    public DepartamentoControlador(DepartamentoRepository repositorio) {
        this.repositorio = repositorio;
    }

    // *************************************************************************************
    // Este método agrega un nuevo departamento al repositorio, simplemente paso el objeto
    // *************************************************************************************
    public boolean agregarDepartamento(Departamento departamento) {
        return repositorio.agregar(departamento);
    }

    // *************************************************************************************
    // Si encuentro el departamento, actualizo el número de empleados y el jefe, luego
    // lo guardo de nuevo en el repositorio
    // *************************************************************************************
    public boolean actualizarDepartamento(String nombre, int nuevoNumeroEmpleados, Empleado nuevoJefe) {
        Departamento departamento = repositorio.buscar(nombre);
        if (departamento != null) {
            departamento.setNumeroEmpleados(nuevoNumeroEmpleados);
            departamento.setJefe(nuevoJefe);
            return repositorio.actualizar(departamento);
        }
        return false;
    }

    // *************************************************************************************
    // Busco el departamento por nombre y, si lo encuentro, lo elimino del repositorio
    // *************************************************************************************
    public boolean eliminarDepartamento(String nombre) {
        Departamento departamento = repositorio.buscar(nombre);
        if (departamento != null) {
            return repositorio.eliminar(departamento);
        }
        return false;
    }

    // *************************************************************************************
    // Aquí obtengo y retorno la lista de todos los departamentos del repositorio
    // *************************************************************************************
    public java.util.List<Departamento> listarDepartamentos() {
        return repositorio.listar();
    }
}
