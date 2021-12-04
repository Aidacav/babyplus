/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.servicio;


import java.util.List;
import com.aida.babyplus.modelo.dao.ProveedorDAO;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.util.Parseador;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aida
 */
public class ServicioProveedores {
    
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    
    public Proveedor buscarPorid(Integer id) {
        if(id == null) {
            return null;
        }
        return proveedorDAO.buscarPorId(id);
    }

    public List<Proveedor> buscarPorCriteriosAdmin(HttpServletRequest request) {
        
        Proveedor proveedorABuscar = aProveedor(request);
        if(proveedorABuscar.getUsuario() != null) {
            Proveedor proveedor = buscarPorid(proveedorABuscar.getUsuario());
            return proveedor != null ? new LinkedList<Proveedor>(){{add(proveedor);}} : new LinkedList<>();
        }
        
        return proveedorDAO.buscarPorCriteriosAdmin(proveedorABuscar);
    }

    public Proveedor actualizarClienteAdmin(HttpServletRequest request) {
        
        Proveedor datosProveedor = aProveedor(request);
        String nuevoPassword = request.getParameter("password");
        return proveedorDAO.actualizarValoresAdmin(datosProveedor, nuevoPassword);
    }

    public Proveedor crearProveedor(Usuario nuevoUsuario, HttpServletRequest request) {
        
        Proveedor nuevoProveedor = aProveedor(request);
        nuevoProveedor.setUsuario(nuevoUsuario.getId());
        nuevoProveedor.setUsuario1(nuevoUsuario);
        
        return proveedorDAO.guardar(nuevoProveedor);
    }
    
    public List<Proveedor> buscarPorCriteriosCliente(HttpServletRequest request) {
        
        Proveedor proveedorABuscar = aProveedor(request);
        // TODO: Añadir request.getParameter("servicioProveedor") para filtrar por servicios

        return proveedorDAO.buscarPorCriteriosCliente(proveedorABuscar);
    }
    
    private Proveedor aProveedor(HttpServletRequest request) {
        
        Usuario usuario = new Usuario();
        
        usuario.setUsuario(request.getParameter("usuario"));
        usuario.setActivo(Parseador.aBoolean(request.getParameter("activo")));
        usuario.setFechaAlta(Parseador.aFecha(request.getParameter("fechaAlta")));
        
        Proveedor proveedor = new Proveedor();
        proveedor.setRazonSocial(request.getParameter("razon"));
        proveedor.setCif(request.getParameter("cif"));
        proveedor.setDireccion(request.getParameter("direccion"));
        proveedor.setLocalidad(request.getParameter("localidad"));
        proveedor.setCp(Parseador.aNumero(request.getParameter("cp")));
        proveedor.setLogo(Parseador.aBytes(request.getParameter("logo")));
        proveedor.setResponsable(request.getParameter("responsable"));
        proveedor.setUsuario(Parseador.aNumero(request.getParameter("id")));
        proveedor.setUsuario1(usuario);
        
        return proveedor;
    }
}
