package com.aida.babyplus.controlador.privado.cliente;

import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.servicio.ServicioClientes;
import com.aida.babyplus.servicio.ServicioProveedores;
import com.aida.babyplus.servicio.ServicioUsuarios;
import com.aida.babyplus.servicio.TipoUsuario;
import com.aida.babyplus.util.Parseador;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "RenovarSubscripcion", urlPatterns = {"/babyplus/jsp/privado/cliente/accionesCliente/renovarSubscripcion"})
public class RenovarSubscripcion extends HttpServlet {

    private ServicioClientes servicioClientes;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioClientes = new ServicioClientes();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            if(servicioClientes.renovarSubscripcion(request)) {
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/cliente/principal.jsp");
            } else {
                session.setAttribute("error", "subscricion.contratar.error");
                response.sendRedirect(request.getParameter("origen"));
            }
        } catch (Exception e) {
            session.setAttribute("error", "subscricion.contratar.error");
            response.sendRedirect(request.getParameter("origen"));
        }
    }

}
