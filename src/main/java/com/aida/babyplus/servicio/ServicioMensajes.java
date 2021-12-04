/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.servicio;

import com.aida.babyplus.modelo.dao.ClienteDAO;
import com.aida.babyplus.modelo.dao.MensajeDAO;
import com.aida.babyplus.modelo.dao.ProveedorDAO;
import com.aida.babyplus.modelo.dao.UsuarioDAO;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.Mensaje;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.util.Parseador;
import java.time.Instant;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aida
 */
public class ServicioMensajes {
    
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final MensajeDAO mensajeDAO = new MensajeDAO();

    public boolean enviarMensaje(HttpServletRequest request, TipoUsuario tipoUsuario) {
        
        Mensaje mensajeAEnviar = aMensaje(request, tipoUsuario);
        return mensajeAEnviar != null && mensajeDAO.guardar(mensajeAEnviar) != null;
    }
    
    private Mensaje aMensaje(HttpServletRequest request, TipoUsuario tipoUsuario) {
        
        Integer idCliente;
        Integer idProveedor;
        
        switch (tipoUsuario) {
            case CLIENTE:
                idCliente = Parseador.aNumero(request.getParameter("idOrigen"));
                idProveedor = Parseador.aNumero(request.getParameter("idDestino"));
                break;
            case PROVEEDOR:
                idCliente = Parseador.aNumero(request.getParameter("idDestino"));
                idProveedor = Parseador.aNumero(request.getParameter("idOrigen"));
                break;
            default:
                return null;
        }
        
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        Proveedor proveedor = proveedorDAO.buscarPorId(idProveedor);
        
        if(cliente == null || proveedor == null) {
            return null;
        }
        
        Mensaje mensaje = new Mensaje();
        mensaje.setFecha(Date.from(Instant.now()));
        mensaje.setMensaje(request.getParameter("mensaje"));
        mensaje.setOrigen(Parseador.aNumero(request.getParameter("idOrigen")));
        mensaje.setCliente(cliente);
        mensaje.setProveedor(proveedor);
        
        return mensaje;
    }
}
