package com.aida.babyplus.controlador.privado.cliente;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.servicio.ServicioProveedores;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Aida
 */
@WebServlet(name = "BusquedaProveedores", urlPatterns = {"/babyplus/jsp/privado/cliente/busquedaProveedores"})
public class BusquedaProveedores extends HttpServlet {

    private ServicioProveedores servicioProveedores;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioProveedores = new ServicioProveedores();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            List<Proveedor> proveedores = servicioProveedores.buscarPorCriteriosCliente(request);
            if (!proveedores.isEmpty()) {
                session.setAttribute("proveedores", proveedores);
            } else {
                session.setAttribute("error", "buscador.proveedores.error.no.encontrado");
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
        }
        
        response.sendRedirect(request.getParameter("origen"));
    }
}
