package com.aida.babyplus.controlador.privado.cliente;

import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.modelo.entidades.Solicitud;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.servicio.ServicioCitas;
import com.aida.babyplus.servicio.ServicioClientes;
import com.aida.babyplus.servicio.ServicioProveedores;
import com.aida.babyplus.util.Parseador;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aida
 */
@WebServlet(name = "AccionesCliente", urlPatterns = {"/babyplus/jsp/privado/cliente/accionesCliente"})
public class AccionesCliente extends HttpServlet {
    
    private ServicioProveedores servicioProveedores;
    private ServicioClientes servicioClientes;
    private ServicioCitas servicioCitas;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioProveedores = new ServicioProveedores();
        servicioClientes = new ServicioClientes();
        servicioCitas = new ServicioCitas();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            Usuario usuario = ((Usuario)session.getAttribute("usuario"));
            Cliente cliente = servicioClientes.buscarPorid(usuario.getId());
            if(cliente != null) {
                session.setAttribute("cliente", cliente);
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/cliente/perfil.jsp");
            } else {
                throw new Exception("Forzando Salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        boolean esVerDetalle = request.getParameter("verDetalle") != null;
        boolean esPedirCita = request.getParameter("pedirCita") != null;
        boolean esConfirmarCita = request.getParameter("confirmarCita") != null;
        boolean esEnviarMensaje = request.getParameter("verConversacion") != null;
        boolean esActualizar = request.getParameter("actualizar") != null;
        boolean esCrearPaciente = request.getParameter("crearPaciente") != null;
        boolean esModificarPaciente = request.getParameter("modificarPaciente") != null;
        boolean esEliminarPaciente = request.getParameter("eliminarPaciente") != null;
        
        if (esVerDetalle) {
          verDetalleProveedor(request, response);
        } else if (esPedirCita) {
          pedirCitaAProveedor(request, response);
        } else if (esConfirmarCita) {
          confirmarCitaConProveedor(request, response);
        } else if (esEnviarMensaje) {
          enviarMensajeAProveedor(request, response);
        } else if (esActualizar) {
          actualizarDatos(request, response);  
        } else if(esCrearPaciente) {
            crearPaciente(request, response);
        } else if(esModificarPaciente) {
            actualizarPaciente(request, response);
        } else if(esEliminarPaciente) {
            eliminarPaciente(request, response);
        } else {
          devolverNoDisponible(request, response);
        }
    }
    
    private void verDetalleProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer idProveedor = Parseador.aNumero(request.getParameter("idDestino"));

        try {
            Proveedor proveedor = servicioProveedores.buscarPorid(idProveedor);
            if (proveedor != null) {
                session.setAttribute("proveedor", proveedor);
                session.setAttribute("logo", Base64.encode(proveedor.getLogo()));
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/cliente/detalleProveedor.jsp");
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "cliente.gestion.error.detalle.proveedor");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void pedirCitaAProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        try {
            Cliente cliente = servicioClientes.buscarPorid(Parseador.aNumero(request.getParameter("idOrigen")));
            Proveedor proveedor = servicioProveedores.buscarPorid(Parseador.aNumero(request.getParameter("idDestino")));
            if(cliente != null && proveedor != null) {
                session.setAttribute("cliente", cliente);
                session.setAttribute("proveedor", proveedor);
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/cliente/solicitud.jsp");
            } else {
                throw new Exception("Forzando Salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void confirmarCitaConProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        try {
            Solicitud solicitud = servicioCitas.crearSolicitud(request);
            if(solicitud != null) {
                session.setAttribute("mensaje", "cita.solicitud.creacion.ok");
            } else {
                throw new Exception("Forzando Salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void enviarMensajeAProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/babyplus/jsp/privado/gestorMensajes").forward(request, response);
    }
    
    private void actualizarDatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        try {
            Cliente cliente = servicioClientes.actualizarCliente(request);
            session.setAttribute("mensaje", "cliente.gestion.actualizar.ok");
            session.setAttribute("cliente", cliente);
        } catch (Exception e) {
            session.setAttribute("error", "cliente.gestion.actualizar.ko");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void crearPaciente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            Cliente cliente = servicioClientes.agregarPaciente(request);
            if (cliente != null) {
                session.setAttribute("mensaje", "cliente.gestion.hijo.crear.ok");
                session.setAttribute("cliente", cliente);
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "cliente.gestion.hijo.crear.ko");
        }
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void actualizarPaciente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            Cliente cliente = servicioClientes.actualizarPaciente(request);
            if (cliente != null) {
                session.setAttribute("mensaje", "cliente.gestion.hijo.actualizar.ok");
                session.setAttribute("cliente", cliente);
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "cliente.gestion.hijo.actualizar.ko");
        }
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void eliminarPaciente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            Cliente cliente = servicioClientes.eliminarPaciente(request);
            if (cliente != null) {
                session.setAttribute("mensaje", "cliente.gestion.hijo.eliminar.ok");
                session.setAttribute("cliente", cliente);
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "cliente.gestion.hijo.eliminar.ko");
        }
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("error", "cliente.gestion.error.no.disponible");
        response.sendRedirect(request.getParameter("origen"));
    }
}
