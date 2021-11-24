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
import modelo.ActualizacionClientes;
import modelo.ActualizacionProveedores;
import modelo.BusquedaClientes;
import modelo.BusquedaProveedores;
import modelo.entidades.Cliente;
import modelo.entidades.Proveedor;
import servicio.ServicioClientes;
import servicio.ServicioProveedores;
import servicio.ServicioUsuarios;
import util.Parseador;

/**
 *
 * @author Aida
 */
public class AccionesProveedor extends HttpServlet {

    private ServicioProveedores servicioProveedores;
    private ServicioUsuarios servicioUsuarios;
   
    @Override
    public void init() throws ServletException {
        super.init();
        servicioProveedores = new ServicioProveedores();
        servicioUsuarios = new ServicioUsuarios();
    }

    // <editor-fold defaultstate="collapsed" desc="Expression servletEditorFold is undefined on line 41, column 54 in Templates/JSP_Servlet/Servlet_Custom.java.">
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
        BusquedaProveedores criterios = Parseador.aBusquedaProveedores(request);

        try {
            Proveedor proveedorAModificar = servicioProveedores.buscarPorid(criterios.getId());
            if (proveedorAModificar != null) {
                servicioUsuarios.cambiarEstado(criterios.getId());
                session.setAttribute("mensaje", "administrador.gestion.proveedores.accion.cambio.estado.ok");
                session.removeAttribute("proveedores");
            } else {
                session.setAttribute("error", "administrador.gestion.proveedores.accion.cambio.estado.ko");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.clientes.accion.cambio.estado.ko");
        }
        
        response.sendRedirect(criterios.getOrigen());
    }
    
    private void cargarDetalle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        BusquedaProveedores criterios = Parseador.aBusquedaProveedores(request);

        try {
            Proveedor proveedor = servicioProveedores.buscarPorid(criterios.getId());
            if (proveedor != null) {
                session.removeAttribute("proveedores");
                session.setAttribute("proveedor", proveedor);
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/admin/detalleProveedor.jsp");
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.proveedores.accion.detalles.ko");
            response.sendRedirect(criterios.getOrigen());
        }
    }
    
    private void actualizarDatosCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        ActualizacionProveedores nuevosValores = Parseador.aActualizacionProveedores(request);

        try {
            Proveedor proveedorAModificar = servicioProveedores.buscarPorid(nuevosValores.getId());
            if (proveedorAModificar != null) {
                Proveedor proveedorModificado = servicioProveedores.actualizarClienteAdmin(nuevosValores);
                session.setAttribute("mensaje", "administrador.gestion.proveedores.accion.actualizar.ok");
                session.setAttribute("proveedor", proveedorModificado);
            } else {
                session.setAttribute("error", "administrador.gestion.proveedores.accion.actualizar.ko");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.proveedores.accion.actualizar.ko");
        }
        
        response.sendRedirect(nuevosValores.getOrigen());
    }
    
    private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String origen = request.getParameter("origen");
        HttpSession session = request.getSession();
        session.setAttribute("error", "administrador.gestion.proveedores.error.no.disponible");
        response.sendRedirect(origen);
    }
}
