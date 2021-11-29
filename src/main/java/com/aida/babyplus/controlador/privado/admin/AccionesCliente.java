/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.controlador.privado.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aida.babyplus.modelo.ActualizacionClientes;
import com.aida.babyplus.modelo.BusquedaClientes;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.servicio.ServicioClientes;
import com.aida.babyplus.servicio.ServicioUsuarios;
import com.aida.babyplus.util.Parseador;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Aida
 */
@WebServlet(name = "AccionesCliente", urlPatterns = {"/babyplus/jsp/privado/admin/accionesCliente"})
public class AccionesCliente extends HttpServlet {

    private ServicioClientes servicioClientes;
    private ServicioUsuarios servicioUsuarios;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioClientes = new ServicioClientes();
        servicioUsuarios = new ServicioUsuarios();
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
        boolean esActualizacion = request.getParameter("actualizar") != null;
        
        if (esCambioEstado) {
            cambiaEstado(request, response);
        } else if (esVerDetalle) {
            cargarDetalle(request, response);
        } else if (esActualizacion) {
            actualizarDatosCliente(request, response);
        }else {
            devolverNoDisponible(request, response);
        }
    }
    
    private void cambiaEstado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        BusquedaClientes criterios = Parseador.aBusquedaClientes(request);

        try {
            Cliente clienteAModificar = servicioClientes.buscarPorid(criterios.getId());
            if (clienteAModificar != null) {
                servicioUsuarios.cambiarEstado(criterios.getId());
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
    
    private void cargarDetalle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        BusquedaClientes criterios = Parseador.aBusquedaClientes(request);

        try {
            Cliente cliente = servicioClientes.buscarPorid(criterios.getId());
            if (cliente != null) {
                session.removeAttribute("clientes");
                session.setAttribute("cliente", cliente);
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/admin/detalleCliente.jsp");
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.clientes.accion.detalles.ko");
            response.sendRedirect(criterios.getOrigen());
        }
    }
    
    private void actualizarDatosCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        ActualizacionClientes nuevosValores = Parseador.aActualizacionClientes(request);

        try {
            Cliente clienteAModificar = servicioClientes.buscarPorid(nuevosValores.getId());
            if (clienteAModificar != null) {
                Cliente clienteModificado = servicioClientes.actualizarClienteAdmin(nuevosValores);
                session.setAttribute("mensaje", "administrador.gestion.clientes.accion.actualizar.ok");
                session.setAttribute("cliente", clienteModificado);
            } else {
                session.setAttribute("error", "administrador.gestion.clientes.accion.actualizar.ko");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.clientes.accion.actualizar.ko");
        }
        
        response.sendRedirect(nuevosValores.getOrigen());
    }
    
    private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String origen = request.getParameter("origen");
        HttpSession session = request.getSession();
        session.setAttribute("error", "administrador.gestion.clientes.error.no.disponible");
        response.sendRedirect(origen);
    }
}
