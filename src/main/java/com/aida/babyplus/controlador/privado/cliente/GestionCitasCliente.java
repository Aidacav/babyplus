package com.aida.babyplus.controlador.privado.cliente;

import com.aida.babyplus.modelo.entidades.Cita;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.Solicitud;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.modelo.entidades.Valoracion;
import com.aida.babyplus.servicio.ServicioCitas;
import com.aida.babyplus.servicio.ServicioClientes;
import com.aida.babyplus.servicio.ServicioProveedores;
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
@WebServlet(name = "GestionCitasCliente", urlPatterns = {"/babyplus/jsp/privado/cliente/gestionCitasCliente"})
public class GestionCitasCliente extends HttpServlet {
    
    private ServicioCitas servicioCitas;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioCitas = new ServicioCitas();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            Usuario usuario = ((Usuario)session.getAttribute("usuario"));
            List<Solicitud> solicitudes = servicioCitas.buscarSolicitudesPorIdCliente(usuario.getId());
            List<Cita> citas = servicioCitas.buscarCitasParaSolicitudes(solicitudes);
            session.setAttribute("solicitudes", solicitudes);
            session.setAttribute("citas", citas);
            response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/cliente/citas.jsp");
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            Valoracion valoracion = servicioCitas.insertarValoracion(request);
            if(valoracion != null) {
                session.setAttribute("mensaje", "cita.valoracion.ok");
            } else {
                session.setAttribute("error", "cita.valoracion.ko");
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
        }
        response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/cliente/gestionCitasCliente");
    }
}
