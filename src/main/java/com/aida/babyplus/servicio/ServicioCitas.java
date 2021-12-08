package com.aida.babyplus.servicio;

import com.aida.babyplus.modelo.dao.CitaDAO;
import com.aida.babyplus.modelo.dao.ClienteDAO;
import com.aida.babyplus.modelo.dao.ProveedorDAO;
import com.aida.babyplus.modelo.dao.SolicitudDAO;
import com.aida.babyplus.modelo.entidades.Cita;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.EstadoSolicitud;
import com.aida.babyplus.modelo.entidades.Paciente;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.modelo.entidades.ProveedorServicio;
import com.aida.babyplus.modelo.entidades.Solicitud;
import com.aida.babyplus.modelo.entidades.Valoracion;
import com.aida.babyplus.util.Parseador;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    
    public List<Solicitud> buscarSolicitudesPorIdCliente(Integer id) {
        return citaDAO.buscarSolicitudesDeCliente(id);
    }
    
    public List<Cita> buscarCitasParaSolicitudes(List<Solicitud> solicitudes) {
        
        Set<Integer> idsSolicitudes = new HashSet<>();
        for(Solicitud solicitud : solicitudes) {
            idsSolicitudes.add(solicitud.getId());
        }
        return citaDAO.buscarCitasPorIdSolicitudes(idsSolicitudes);
    }
    
    public Valoracion insertarValoracion(HttpServletRequest request) {
        
        Valoracion nuevaValoracion = aValoracion(request);
        return nuevaValoracion == null ? null : citaDAO.guardarValoracion(nuevaValoracion);
        
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
    
    private Valoracion aValoracion(HttpServletRequest request) {
        
        Cita cita = citaDAO.buscarPorId(Parseador.aNumero(request.getParameter("idCita")));
        
        if(cita == null) {
            return null;
        }
        
        Valoracion valoracion = new Valoracion();
        valoracion.setCita1(cita);
        valoracion.setCita(cita.getId());
        valoracion.setChupetes(Parseador.aNumero(request.getParameter("chupetes")));
        valoracion.setMensaje(request.getParameter("mensaje"));
        valoracion.setFecha(Date.from(Instant.now()));
        
        return valoracion;
    }
}
