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
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.util.Parseador;
import javax.servlet.http.HttpServletRequest;

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

    public Proveedor crearProveedor(Usuario nuevoUsuario, HttpServletRequest request) {
        
        System.out.println("ssss");
        Proveedor nuevoProveedor = new Proveedor(nuevoUsuario.getId());
        
        nuevoProveedor.setUsuario1(nuevoUsuario);
        nuevoProveedor.setRazonSocial(request.getParameter("razonProveedor"));
        nuevoProveedor.setCif(request.getParameter("cifProveedor"));
        nuevoProveedor.setDireccion(request.getParameter("direccionProveedor"));
        nuevoProveedor.setLocalidad(request.getParameter("localidadProveedor"));
        nuevoProveedor.setCp(Parseador.aNumero(request.getParameter("cpProveedor")));
        nuevoProveedor.setResponsable(request.getParameter("responsableProveedor"));
        nuevoProveedor.setLogo(request.getParameter("logo").getBytes());
        
        return proveedorDAO.guardar(nuevoProveedor);
    }
}
