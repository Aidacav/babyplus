package com.aida.babyplus.controlador;

import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.servicio.ServicioClientes;
import com.aida.babyplus.servicio.ServicioProveedores;
import com.aida.babyplus.servicio.ServicioUsuarios;
import com.aida.babyplus.servicio.TipoUsuario;
import com.aida.babyplus.util.Parseador;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class Registrar extends HttpServlet {
    
    private ServicioClientes servicioClientes;
    private ServicioProveedores servicioProveedores;
    private ServicioUsuarios servicioUsuarios;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioClientes = new ServicioClientes();
        servicioProveedores = new ServicioProveedores();
        servicioUsuarios = new ServicioUsuarios();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        boolean esRegistroCliente = request.getParameter("registrarCliente") != null;
        boolean esRegistroProveedor = request.getParameter("registrarProveedor") != null;
        
        if (esRegistroCliente) {
            registraCliente(request, response);
        } else if (esRegistroProveedor) {
            byte[] logo = Parseador.aImagenEscalada(request.getPart("logo"));
            registraProveedor(request, response, logo);
        } else {
            devolverNoDisponible(request, response);
        }
    }
    
    private void registraCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            boolean usuarioExiste = servicioUsuarios.existeUsuario(request.getParameter("usuario"));
            Usuario nuevoUsuario = null;
            if (! usuarioExiste) {
                nuevoUsuario = servicioUsuarios.creaNuevoUsuario(request, TipoUsuario.CLIENTE);
                servicioClientes.crearCliente(nuevoUsuario, request);
                session.setAttribute("mensaje", "registro.registrar.ok");
            } else {
                if(nuevoUsuario != null) {
                    servicioUsuarios.borrarUsuario(nuevoUsuario.getId());
                }
                session.setAttribute("error", "registro.registrar.error");
            }
        } catch (Exception e) {
            session.setAttribute("error", "registro.registrar.error");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
    
    private void registraProveedor(HttpServletRequest request, HttpServletResponse response, byte[] logo) throws ServletException, IOException {

        HttpSession session = request.getSession();

        try {
            boolean usuarioExiste = servicioUsuarios.existeUsuario(request.getParameter("usuario"));
            Usuario nuevoUsuario = null;
            if (! usuarioExiste) {
                nuevoUsuario = servicioUsuarios.creaNuevoUsuario(request, TipoUsuario.PROVEEDOR);
                servicioProveedores.crearProveedor(nuevoUsuario, logo, request);
                session.setAttribute("mensaje", "registro.registrar.ok");
            } else {
                if(nuevoUsuario != null) {
                    servicioUsuarios.borrarUsuario(nuevoUsuario.getId());
                }
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
