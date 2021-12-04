package com.aida.babyplus.controlador.privado.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.servicio.ServicioProveedores;
import com.aida.babyplus.servicio.ServicioUsuarios;
import com.aida.babyplus.util.Parseador;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Aida
 */
@WebServlet(name = "AdministrarProveedor", urlPatterns = {"/babyplus/jsp/privado/admin/administrarProveedor"})
public class AdministrarProveedor extends HttpServlet {

    private ServicioProveedores servicioProveedores;
    private ServicioUsuarios servicioUsuarios;
   
    @Override
    public void init() throws ServletException {
        super.init();
        servicioProveedores = new ServicioProveedores();
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
        Integer idProveedor = Parseador.aNumero(request.getParameter("id"));

        try {
            Proveedor proveedorAModificar = servicioProveedores.buscarPorid(idProveedor);
            if (proveedorAModificar != null) {
                servicioUsuarios.cambiarEstado(idProveedor);
                session.setAttribute("mensaje", "administrador.gestion.proveedores.accion.cambio.estado.ok");
                session.removeAttribute("proveedores");
            } else {
                session.setAttribute("error", "administrador.gestion.proveedores.accion.cambio.estado.ko");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.clientes.accion.cambio.estado.ko");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void cargarDetalle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idProveedor = Parseador.aNumero(request.getParameter("id"));

        try {
            Proveedor proveedor = servicioProveedores.buscarPorid(idProveedor);
            if (proveedor != null) {
                session.removeAttribute("proveedores");
                session.setAttribute("proveedor", proveedor);
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/admin/detalleProveedor.jsp");
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.proveedores.accion.detalles.ko");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
    private void actualizarDatosCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer idProveedor = Parseador.aNumero(request.getParameter("id"));

        try {
            Proveedor proveedorAModificar = servicioProveedores.buscarPorid(idProveedor);
            if (proveedorAModificar != null) {
                Proveedor proveedorModificado = servicioProveedores.actualizarClienteAdmin(request);
                session.setAttribute("mensaje", "administrador.gestion.proveedores.accion.actualizar.ok");
                session.setAttribute("proveedor", proveedorModificado);
            } else {
                session.setAttribute("error", "administrador.gestion.proveedores.accion.actualizar.ko");
            }
        } catch (Exception e) {
            session.setAttribute("error", "administrador.gestion.proveedores.accion.actualizar.ko");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("error", "administrador.gestion.proveedores.error.no.disponible");
        response.sendRedirect(request.getParameter("origen"));
    }
}
