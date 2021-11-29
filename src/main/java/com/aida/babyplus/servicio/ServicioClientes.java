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
}
