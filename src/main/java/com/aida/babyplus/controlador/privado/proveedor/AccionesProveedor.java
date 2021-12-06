package com.aida.babyplus.controlador.privado.proveedor;

import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.modelo.entidades.Servicio;
import com.aida.babyplus.modelo.entidades.Usuario;
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
@WebServlet(name = "AccionesProveedor", urlPatterns = {"/babyplus/jsp/privado/proveedor/accionesProveedor"})
public class AccionesProveedor extends HttpServlet {
    
    private ServicioProveedores servicioProveedores;
    private ServicioClientes servicioClientes; 
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioProveedores = new ServicioProveedores();
        servicioClientes = new ServicioClientes();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            Usuario usuario = ((Usuario)session.getAttribute("usuario"));
            Proveedor proveedor = servicioProveedores.buscarPorid(usuario.getId());
            if(proveedor != null) {
                List<Servicio> catalogoServicios = servicioProveedores.buscarListadoServicios();
                session.setAttribute("proveedor", proveedor);
                session.setAttribute("catalogoServicios", catalogoServicios);
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/proveedor/perfil.jsp");
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
        
        boolean esActualizar = request.getParameter("actualizar") != null;
        boolean esCrearServicio = request.getParameter("crearServicio") != null;
        boolean esModificarServicio = request.getParameter("modificarServicio") != null;
        boolean esEliminarServicio = request.getParameter("eliminarServicio") != null;
        
        if (esActualizar) {
          actualizarDatos(request, response);  
        } else if(esCrearServicio) {
            crearServicio(request, response);
        } else if(esModificarServicio) {
            actualizarServicio(request, response);
        } else if(esEliminarServicio) {
            eliminarServicio(request, response);
        } else {
          devolverNoDisponible(request, response);
        }
    }
    
    private void actualizarDatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        try {
            Proveedor proveedor = servicioProveedores.actualizarProveedor(request);
            session.setAttribute("mensaje", "proveedor.gestion.actualizar.ok");
            session.setAttribute("proveedor", proveedor);
        } catch (Exception e) {
            session.setAttribute("error", "proveedor.gestion.actualizar.ko");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void crearServicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            Proveedor proveedor = servicioProveedores.agregarServicio(request);
            if (proveedor != null) {
                session.setAttribute("mensaje", "proveedor.gestion.servicio.crear.ok");
                session.setAttribute("proveedor", proveedor);
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "proveedor.gestion.servicio.crear.ko");
        }
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void actualizarServicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            Proveedor proveedor = servicioProveedores.actualizarServicio(request);
            if (proveedor != null) {
                session.setAttribute("mensaje", "proveedor.gestion.servicio.actualizar.ok");
                session.setAttribute("proveedor", proveedor);
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "proveedor.gestion.servicio.actualizar.ko");
        }
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void eliminarServicio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            Proveedor proveedor = servicioProveedores.eliminarServicio(request);
            if (proveedor != null) {
                session.setAttribute("mensaje", "proveedor.gestion.servicio.eliminar.ok");
                session.setAttribute("proveedor", proveedor);
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "proveedor.gestion.servicio.eliminar.ko");
        }
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("error", "cliente.gestion.error.no.disponible");
        response.sendRedirect(request.getParameter("origen"));
    }
}
