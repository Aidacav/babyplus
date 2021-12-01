package com.aida.babyplus.controlador;

import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.servicio.ServicioClientes;
import com.aida.babyplus.servicio.ServicioProveedores;
import com.aida.babyplus.servicio.ServicioUsuarios;
import com.aida.babyplus.servicio.TipoUsuario;
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
@WebServlet(name = "Registrar", urlPatterns = {"/registrar"})
public class Registrar extends HttpServlet {
    
    private ServicioClientes servicioClientes;
    private ServicioProveedores servicioProveedores;
    private ServicioUsuarios servicioUsuarios;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioClientes = new ServicioClientes();
        servicioUsuarios = new ServicioUsuarios();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean esRegistroCliente = request.getParameter("registrarCliente") != null;
        boolean esRegistroProveedor = request.getParameter("registrarProveedor") != null;
        
        if (esRegistroCliente) {
            registraCliente(request, response);
        } else if (esRegistroProveedor) {
            registraProveedor(request, response);
        } else {
            devolverNoDisponible(request, response);
        }
    }
    
    private void registraCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            boolean usuarioExiste = servicioUsuarios.existeUsuario(request.getParameter("usuario"));
            if (! usuarioExiste) {
                Usuario nuevoUsuario = servicioUsuarios.creaNuevoUsuario(request, TipoUsuario.CLIENTE);
                servicioClientes.crearCliente(nuevoUsuario, request);
                session.setAttribute("mensaje", "registro.registrar.ok");
            } else {
                session.setAttribute("error", "registro.registrar.error");
            }
        } catch (Exception e) {
            session.setAttribute("error", "registro.registrar.error");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void registraProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            boolean usuarioExiste = servicioUsuarios.existeUsuario(request.getParameter("usuario"));
            if (! usuarioExiste) {
                Usuario nuevoUsuario = servicioUsuarios.creaNuevoUsuario(request, TipoUsuario.PROVEEDOR);
                servicioProveedores.crearProveedor(nuevoUsuario, request);
                session.setAttribute("mensaje", "registro.registrar.ok");
            } else {
                session.setAttribute("error", "registro.registrar.error");
            }
        } catch (Exception e) {
            session.setAttribute("error", "registro.registrar.error");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    
    private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String origen = request.getParameter("origen");
        HttpSession session = request.getSession();
        session.setAttribute("error", "registro.registrar.error");
        response.sendRedirect(origen);
    }
}
