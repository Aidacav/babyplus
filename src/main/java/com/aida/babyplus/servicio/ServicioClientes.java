/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.servicio;


import java.util.List;
import com.aida.babyplus.modelo.ActualizacionClientes;
import com.aida.babyplus.modelo.BusquedaClientes;
import com.aida.babyplus.modelo.dao.ClienteDAO;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.util.Parseador;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aida
 */
public class ServicioClientes {
    
    private final ClienteDAO clienteDAO = new ClienteDAO();
    
    public Cliente buscarPorid(Integer idCliente) {
        
        List<Cliente> clientes = clienteDAO.buscarPorId(idCliente);
        if(!clientes.isEmpty()) {
            return clientes.get(0);
        }
        return null;
    }

    public List<Cliente> buscarPorCriterios(BusquedaClientes criterios) {
        
        if(criterios.getId() != null) {
            return clienteDAO.buscarPorId(criterios.getId());
        }
        
        return clienteDAO.buscarPorCriterios(criterios);
    }

    public Cliente actualizarClienteAdmin(ActualizacionClientes nuevosValores) {
        return clienteDAO.actualizarValoresComoAdmin(nuevosValores);
    }

    public Cliente crearCliente(Usuario nuevoUsuario, HttpServletRequest request) {
        
        Cliente nuevoCliente = new Cliente(nuevoUsuario.getId());
        
        nuevoCliente.setUsuario1(nuevoUsuario);
        nuevoCliente.setNombre(request.getParameter("nombreCliente"));
        nuevoCliente.setApellidos(request.getParameter("apellidosCliente"));
        nuevoCliente.setDomicilio(request.getParameter("domicilioCliente"));
        nuevoCliente.setLocalidad(request.getParameter("localidadCliente"));
        nuevoCliente.setCp(Parseador.aNumero(request.getParameter("cpCliente")));
        nuevoCliente.setFechaNacimiento(Parseador.aFecha(request.getParameter("fechaCliente")));
        
        return clienteDAO.guardar(nuevoCliente);
    }

    
}
