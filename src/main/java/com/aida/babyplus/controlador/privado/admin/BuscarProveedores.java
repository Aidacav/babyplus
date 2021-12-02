package com.aida.babyplus.controlador.privado.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aida.babyplus.modelo.BusquedaProveedores;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.servicio.ServicioProveedores;
import com.aida.babyplus.util.Parseador;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Aida
 */
@WebServlet(name = "BuscarProveedores", urlPatterns = {"/babyplus/jsp/privado/admin/buscarProveedores"})
public class BuscarProveedores extends HttpServlet {

    private ServicioProveedores servicioProveedores;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioProveedores = new ServicioProveedores();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        BusquedaProveedores criterios = Parseador.aBusquedaProveedores(request);
        HttpSession session = request.getSession();

        try {
            List<Proveedor> proveedores = servicioProveedores.buscarPorCriterios(criterios);
            if (!proveedores.isEmpty()) {
                session.setAttribute("proveedores", proveedores);
            } else {
                session.setAttribute("error", "buscador.proveedores.error.no.encontrado");
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
        }
        
        response.sendRedirect(criterios.getOrigen());
    }
}
