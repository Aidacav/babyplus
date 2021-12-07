package com.aida.babyplus.servicio;

import com.aida.babyplus.modelo.dao.CitaDAO;
import com.aida.babyplus.modelo.dao.ClienteDAO;
import com.aida.babyplus.modelo.dao.ProveedorDAO;
import com.aida.babyplus.modelo.dao.SolicitudDAO;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.EstadoSolicitud;
import com.aida.babyplus.modelo.entidades.Paciente;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.modelo.entidades.ProveedorServicio;
import com.aida.babyplus.modelo.entidades.Solicitud;
import com.aida.babyplus.util.Parseador;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aida
 */
public class ServicioCitas {
    
    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final CitaDAO citaDAO = new CitaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    public Solicitud crearSolicitud(HttpServletRequest request) {
        
        Solicitud nuevaSolicitud = aSolicitud(request);
        return nuevaSolicitud == null ? null : solicitudDAO.guardar(nuevaSolicitud);
    }
    
    private Solicitud aSolicitud(HttpServletRequest request) {
        
        Cliente cliente = clienteDAO.buscarPorId(Parseador.aNumero(request.getParameter("idCliente")));
        Proveedor proveedor = proveedorDAO.buscarPorId(Parseador.aNumero(request.getParameter("idProveedor")));
        
        if(cliente == null || proveedor == null) {
            return null;
        }
        
        Paciente paciente = new Paciente(Parseador.aNumero(request.getParameter("idPaciente")));
        ProveedorServicio servicio = new ProveedorServicio(Parseador.aNumero(request.getParameter("idServicio")));
        
        Solicitud solicitud = new Solicitud();
        solicitud.setCliente(cliente);
        solicitud.setPaciente(paciente);
        solicitud.setProveedor(proveedor);
        solicitud.setServicio(servicio);
        solicitud.setFecha(Parseador.aFecha(request.getParameter("fecha")));
        solicitud.setNotas(request.getParameter("notas"));
        solicitud.setEstado(new EstadoSolicitud("ENVIADA"));
        
        return solicitud;
    }
    
}
