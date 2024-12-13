/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author yovani c
 */

package co.edu.udec.poo.Empresa.Controlador;

import co.edu.udec.poo.Empresa.Modelo.Entidades.Empresa;
import co.edu.udec.poo.Empresa.Modelo.Persistencia.EmpresaRepository;

public class EmpresaControlador {
    // *************************************************************************************
    // Creo una referencia al repositorio para manejar todas las operaciones con empresas.
    // *************************************************************************************
    private EmpresaRepository repositorio;

    // *************************************************************************************
    // Aquí inicializo el controlador con su repositorio. Es el puente entre la vista y los datos.
    // *************************************************************************************
    public EmpresaControlador(EmpresaRepository repositorio) {
        this.repositorio = repositorio;
    }

    // *************************************************************************************
    // Este método me permite agregar una empresa nueva al repositorio.
    // *************************************************************************************
    public boolean agregarEmpresa(Empresa empresa) {
        return repositorio.agregar(empresa);
    }

    // *************************************************************************************
    // Aquí busco una empresa por su nombre, y si existe, actualizo su sector y número de empleados.
    // *************************************************************************************
    public boolean actualizarEmpresa(String nombre, String sector, int numeroEmpleados) {
        Empresa empresa = repositorio.buscar(nombre);
        if (empresa != null) {
            empresa.setSector(sector);
            empresa.setNumeroEmpleados(numeroEmpleados);
            return repositorio.actualizar(empresa);
        }
        return false;
    }

    // *************************************************************************************
    // Este método elimina una empresa buscándola por su nombre. Si no la encuentro, no hace nada.
    // *************************************************************************************
    public boolean eliminarEmpresa(String nombre) {
        Empresa empresa = repositorio.buscar(nombre);
        if (empresa != null) {
            return repositorio.eliminar(empresa);
        }
        return false;
    }

    // *************************************************************************************
    // Aquí obtengo todas las empresas del repositorio como una lista.
    // *************************************************************************************
    public java.util.List<Empresa> listarEmpresas() {
        return repositorio.listar();
    }
}
