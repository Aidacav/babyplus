/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.servicio;


import java.util.List;
import com.aida.babyplus.modelo.ActualizacionProveedores;
import com.aida.babyplus.modelo.BusquedaProveedores;
import com.aida.babyplus.modelo.dao.ProveedorDAO;
import com.aida.babyplus.modelo.entidades.Proveedor;

/**
 *
 * @author Aida
 */
public class ServicioProveedores {
    
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    
    public Proveedor buscarPorid(Integer idCliente) {
        
        List<Proveedor> proveedores = proveedorDAO.buscarPorId(idCliente);
        if(!proveedores.isEmpty()) {
            return proveedores.get(0);
        }
        return null;
    }

    public List<Proveedor> buscarPorCriterios(BusquedaProveedores criterios) {
        
        if(criterios.getId() != null) {
            return proveedorDAO.buscarPorId(criterios.getId());
        }
        
        return proveedorDAO.buscarPorCriterios(criterios);
    }

    public Proveedor actualizarClienteAdmin(ActualizacionProveedores nuevosValores) {
        return proveedorDAO.actualizarValoresComoAdmin(nuevosValores);
    }
}
