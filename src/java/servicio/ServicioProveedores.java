/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;


import java.util.List;
import modelo.ActualizacionProveedores;
import modelo.BusquedaProveedores;
import modelo.dao.ProveedorDAO;
import modelo.entidades.Proveedor;

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