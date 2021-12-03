package com.aida.babyplus.controlador.privado.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        Integer idCliente = Parseador.aNumero(request.getParameter("id"));

        try {
            Cliente clienteAModificar = servicioClientes.buscarPorid(idCliente);
            if (clienteAModificar != null) {
                servicioUsuarios.cambiarEstado(idCliente);
                session.setAttribute("mensaje", "administrador.gestion.clientes.accion.cambio.estado.ok");
                session.removeAttribute("clientes");
            } else {
                session.setAttribute("error", "administrador.gestion.clientes.accion.cambio.estado.ko");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.clientes.accion.cambio.estado.ko");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void cargarDetalle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idCliente = Parseador.aNumero(request.getParameter("id"));

        try {
            Cliente cliente = servicioClientes.buscarPorid(idCliente);
            if (cliente != null) {
                session.removeAttribute("clientes");
                session.setAttribute("cliente", cliente);
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/admin/detalleCliente.jsp");
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.clientes.accion.detalles.ko");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void actualizarDatosCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idCliente = Parseador.aNumero(request.getParameter("id"));

        try {
            Cliente clienteAModificar = servicioClientes.buscarPorid(idCliente);
            if (clienteAModificar != null) {
                Cliente clienteModificado = servicioClientes.actualizarClienteAdmin(request);
                session.setAttribute("mensaje", "administrador.gestion.clientes.accion.actualizar.ok");
                session.setAttribute("cliente", clienteModificado);
            } else {
                session.setAttribute("error", "administrador.gestion.clientes.accion.actualizar.ko");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.clientes.accion.actualizar.ko");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("error", "administrador.gestion.clientes.error.no.disponible");
        response.sendRedirect(request.getParameter("origen"));
    }
}
