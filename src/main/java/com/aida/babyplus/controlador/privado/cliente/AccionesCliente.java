package com.aida.babyplus.controlador.privado.cliente;

import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.servicio.ServicioMensajes;
import com.aida.babyplus.servicio.ServicioProveedores;
import com.aida.babyplus.servicio.TipoUsuario;
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
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioProveedores = new ServicioProveedores();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        boolean esVerDetalle = request.getParameter("verDetalle") != null;
        boolean esPedirCita = request.getParameter("pedirCita") != null;
        boolean esEnviarMensaje = request.getParameter("verConversacion") != null;
        
        if (esVerDetalle) {
            verDetalleProveedor(request, response);
        } else if (esPedirCita) {
            pedirCitaAProveedor(request, response);
        } else if (esEnviarMensaje) {
            enviarMensajeAProveedor(request, response);
        }else {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void enviarMensajeAProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/babyplus/jsp/privado/gestorMensajes").forward(request, response);
    }
    
     private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("error", "cliente.gestion.error.no.disponible");
        response.sendRedirect(request.getParameter("origen"));
    }
}
