package com.aida.babyplus.controlador.privado.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aida.babyplus.modelo.BusquedaClientes;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.servicio.ServicioClientes;
import com.aida.babyplus.util.Parseador;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Aida
 */
@WebServlet(name = "BuscarClientes", urlPatterns = {"/babyplus/jsp/privado/admin/buscarClientes"})
public class BuscarClientes extends HttpServlet {
    
    private ServicioClientes servicioClientes;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioClientes = new ServicioClientes();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        BusquedaClientes criterios = Parseador.aBusquedaClientes(request);
        HttpSession session = request.getSession();

        try {
            List<Cliente> clientes = servicioClientes.buscarPorCriterios(criterios);
            if (!clientes.isEmpty()) {
                session.setAttribute("clientes", clientes);
            } else {
                session.setAttribute("error", "buscador.clientes.error.no.encontrado");
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
        }
        
        response.sendRedirect(criterios.getOrigen());
    }
}
