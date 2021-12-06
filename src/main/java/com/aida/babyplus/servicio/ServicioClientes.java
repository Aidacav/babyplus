/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.servicio;


import java.util.List;
import com.aida.babyplus.modelo.dao.ClienteDAO;
import com.aida.babyplus.modelo.dao.SubscripcionDAO;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.ClienteSubscripcion;
import com.aida.babyplus.modelo.entidades.Subscripcion;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.modelo.entidades.Paciente;
import com.aida.babyplus.util.Parseador;
import java.time.Instant;
import static java.time.temporal.ChronoUnit.DAYS;
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
 
    public Cliente buscarPorid(Integer id) {
        if(id == null) {
            return null;
        }
        return clienteDAO.buscarPorId(id);
    }

    public List<Cliente> buscarPorCriteriosAdmin(HttpServletRequest request) {
        
        Cliente clienteABuscar = aCliente(request);
        
        if(clienteABuscar.getUsuario() != null) {
            Cliente cliente = buscarPorid(clienteABuscar.getUsuario());
            return cliente != null ? new LinkedList<Cliente>(){{add(cliente);}} : new LinkedList<>();
        }
        
        return clienteDAO.buscarPorCriteriosAdmin(clienteABuscar);
    }

    public Cliente actualizarCliente(HttpServletRequest request) {
        
        Cliente datosCliente = aCliente(request);
        String nuevoPassword = request.getParameter("password");
        return clienteDAO.actualizarCliente(datosCliente, nuevoPassword);
    }

    public Cliente crearCliente(Usuario nuevoUsuario, HttpServletRequest request) {
        
        Cliente nuevoCliente = aCliente(request);
        nuevoCliente.setUsuario(nuevoUsuario.getId());
        nuevoCliente.setUsuario1(nuevoUsuario);
        
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
    
    public Cliente agregarPaciente(HttpServletRequest request) {
        
        Paciente nuevoPaciente = aPaciente(request);
        return nuevoPaciente == null ? null : clienteDAO.agregarPaciente(nuevoPaciente);
    }
    
    public Cliente actualizarPaciente(HttpServletRequest request) {
        
        Paciente datosPaciente = aPaciente(request);
        return datosPaciente == null ? null : clienteDAO.actualizarPaciente(datosPaciente);
    }
    
    public Cliente eliminarPaciente(HttpServletRequest request) {
        
        Paciente datosPaciente = aPaciente(request);
        return datosPaciente == null ? null : clienteDAO.eliminarPaciente(datosPaciente);
    }
    
    private Cliente aCliente(HttpServletRequest request) {
        
        Usuario usuario = new Usuario();
        usuario.setUsuario(request.getParameter("usuario"));
        usuario.setActivo(Parseador.aBoolean(request.getParameter("activo")));
        usuario.setFechaAlta(Parseador.aFecha(request.getParameter("fechaAlta")));
        
        Cliente cliente = new Cliente();
        cliente.setUsuario(Parseador.aNumero(request.getParameter("id")));
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setApellidos(request.getParameter("apellidos"));
        cliente.setFechaNacimiento(Parseador.aFecha(request.getParameter("fechaNacimiento")));
        cliente.setDomicilio(request.getParameter("domicilio"));
        cliente.setLocalidad(request.getParameter("localidad"));
        cliente.setCp(Parseador.aNumero(request.getParameter("cp")));
        cliente.setUsuario1(usuario);

        return cliente;
    }
    
    private Paciente aPaciente(HttpServletRequest request) {
        
        Usuario usuario = ((Usuario)request.getSession().getAttribute("usuario"));
        
        if(usuario == null) {
            return null;
        }
        
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario.getId());
        cliente.setUsuario1(usuario);

        Paciente paciente = new Paciente();
        paciente.setCliente(cliente);
        paciente.setId(Parseador.aNumero(request.getParameter("idPaciente")));
        paciente.setNombre(request.getParameter("nombre"));
        paciente.setFechaNacimiento(Parseador.aFecha(request.getParameter("fechaNacimiento")));
        paciente.setObservaciones(request.getParameter("observaciones"));
        
        return paciente;
    }
}
