package com.aida.babyplus.controlador.privado.proveedor;

import com.aida.babyplus.modelo.entidades.Cita;
import com.aida.babyplus.modelo.entidades.Solicitud;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.servicio.ServicioCitas;
import java.io.IOException;
import java.util.List;
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
@WebServlet(name = "GestionCitasProveedor", urlPatterns = {"/babyplus/jsp/privado/proveedor/gestionCitasProveedor"})
public class GestionCitasProveedor extends HttpServlet {

    private ServicioCitas servicioCitas;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioCitas = new ServicioCitas();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        boolean esProcesarSolicitudes = request.getParameter("procesar").equals("solicitudes");
        boolean esProcesarCitas = request.getParameter("procesar").equals("citas");
        
        if (esProcesarSolicitudes) {
            mostrarSolicitudes(request, response);
        } else if (esProcesarCitas) {
            mostrarCitas(request, response);
        } else {
            devolverNoDisponible(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        boolean esAceptarSolicitud = request.getParameter("aceptarSolicitud") != null;
        boolean esRechazarSolicitud = request.getParameter("rechazarSolicitud") != null;
        boolean esCerrarCita = request.getParameter("cerrarCita") != null;
        boolean esCancelarCita = request.getParameter("cancelarCita") != null;
        
        if (esAceptarSolicitud) {
            crearCita(request, response);
        } else if (esRechazarSolicitud) {
            rechazarSolicitud(request, response);
        } else if (esCerrarCita) {
            finalizarCita(request, response);
        } else if (esCancelarCita) {
            cancelarCita(request, response);
        } else {
            devolverNoDisponible(request, response);
        }
    }
    
    private void mostrarSolicitudes(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        try {
            Usuario usuario = ((Usuario)session.getAttribute("usuario"));
            List<Solicitud> solicitudes = servicioCitas.buscarSolicitudesPorIdProveedor(usuario.getId());
            session.setAttribute("solicitudes", solicitudes);
            response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/proveedor/solicitudes.jsp");
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void mostrarCitas(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        try {
            Usuario usuario = ((Usuario)session.getAttribute("usuario"));
            List<Cita> citas = servicioCitas.buscarCitasPorIdProveedor(usuario.getId());
            session.setAttribute("citas", citas);
            response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/proveedor/citas.jsp");
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void crearCita(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        try {
            servicioCitas.crearCita(request);
            session.setAttribute("mensaje", "cita.solicitud.creada");
            response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/proveedor/solicitudes.jsp");
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void rechazarSolicitud(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        try {
            servicioCitas.rechazarSolicitud(request);
            session.setAttribute("mensaje", "cita.solicitud.rechazada");
            response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/proveedor/solicitudes.jsp");
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void finalizarCita(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        try {
            servicioCitas.finalizarCita(request);
            session.setAttribute("mensaje", "cita.cita.cerrada");
            response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/proveedor/citas.jsp");
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void cancelarCita(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        try {
            servicioCitas.cerrarCita(request);
            session.setAttribute("mensaje", "cita.cita.cancelada");
            response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/proveedor/citas.jsp");
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("error", "proveedor.gestion.error.no.disponible");
        response.sendRedirect(request.getParameter("origen"));
    }
}
