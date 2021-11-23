/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.privado.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.BusquedaClientes;
import modelo.entidades.Cliente;
import servicio.ServicioClientes;
import util.Parseador;

/**
 *
 * @author Aida
 */
public class AccionesCliente extends HttpServlet {

    private ServicioClientes servicioClientes;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioClientes = new ServicioClientes();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        boolean esCambioEstado = request.getParameter("cambiarEstado") != null;
        boolean esVerDetalle = request.getParameter("verDetalle") != null;
        
        if (esCambioEstado) {
            cambiaEstado(request, response);
        } else if (esVerDetalle) {
            //cargarDetalle(request, response);
        } else {
            devolverNoDisponible(request, response);
        }
    }
    
    private void cambiaEstado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        BusquedaClientes criterios = Parseador.aBusquedaClientes(request);

        try {
            Cliente clienteAModificar = servicioClientes.buscarPorid(criterios.getId());
            if (clienteAModificar != null) {
                servicioClientes.cambiarEstado(clienteAModificar.getUsuario1());
                session.setAttribute("mensaje", "administrador.gestion.clientes.accion.cambio.estado.ok");
                session.removeAttribute("clientes");
            } else {
                session.setAttribute("error", "administrador.gestion.clientes.accion.cambio.estado.ko");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.clientes.accion.cambio.estado.ko");
        }
        
        response.sendRedirect(criterios.getOrigen());
    }
    
    private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String origen = request.getParameter("origen");
        HttpSession session = request.getSession();
        session.setAttribute("error", "administrador.gestion.clientes.error.no.disponible");
        response.sendRedirect(origen);
    }
}
