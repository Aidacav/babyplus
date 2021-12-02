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
import com.aida.babyplus.modelo.dao.SubscripcionDAO;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.ClienteSubscripcion;
import com.aida.babyplus.modelo.entidades.Subscripcion;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.util.Parseador;
import java.time.Instant;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aida
 */
public class ServicioClientes {
    
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final SubscripcionDAO subscripcionDAO = new SubscripcionDAO();
    
    public boolean esCliente(Usuario usuario) {
        return usuario != null && TipoUsuario.CLIENTE.toString().equals(usuario.getRol().getDescripcion());
    }
 
    public Cliente buscarPorid(Integer idCliente) {
        return clienteDAO.buscarPorId(idCliente);
    }

    public List<Cliente> buscarPorCriterios(BusquedaClientes criterios) {
        
        if(criterios.getId() != null) {
            Cliente cliente = clienteDAO.buscarPorId(criterios.getId());
            return cliente != null ? new LinkedList<Cliente>(){{add(cliente);}} : new LinkedList<Cliente>();
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

    public boolean tieneSubscripcionActiva(Integer idCliente) {
        Date ahora = Date.from(Instant.now());
        Collection<ClienteSubscripcion> subscripciones = buscarPorid(idCliente).getSubscripciones();
        for(ClienteSubscripcion subscripcion : subscripciones) {
            if(subscripcion.getFechaInicio().before(ahora) && subscripcion.getFechaFin().after(ahora)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean renovarSubscripcion(HttpServletRequest request) {
        
        Cliente cliente = buscarPorid(Parseador.aNumero(request.getParameter("idCliente")));
        Subscripcion subscripcion = subscripcionDAO.buscarPorNombre(request.getParameter("tipo"));
        
        if(cliente != null && subscripcion != null) {
            Instant ahora = Instant.now();
            
            ClienteSubscripcion nuevaSubscripcion = new ClienteSubscripcion();
            nuevaSubscripcion.setCliente(cliente);
            nuevaSubscripcion.setSubscripcion(subscripcion);
            nuevaSubscripcion.setFechaInicio(Date.from(ahora));
            nuevaSubscripcion.setFechaFin(Date.from(ahora.plus(subscripcion.getDuracionDias(), DAYS)));
            
            return clienteDAO.agregarSubscripcion(nuevaSubscripcion) != null;
        }
        
        return false;
    }
}
