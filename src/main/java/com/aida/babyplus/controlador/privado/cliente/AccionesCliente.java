package com.aida.babyplus.controlador.privado.cliente;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        boolean esVerDetalle = request.getParameter("verDetalle") != null;
        boolean esPedirCita = request.getParameter("pedirCita") != null;
        boolean esEnviarMensaje = request.getParameter("enviarMensaje") != null;
        
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
    
    private void verDetalleProveedor(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void pedirCitaAProveedor(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void enviarMensajeAProveedor(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("error", "cliente.gestion.error.no.disponible");
        response.sendRedirect(request.getParameter("origen"));
    }
}
