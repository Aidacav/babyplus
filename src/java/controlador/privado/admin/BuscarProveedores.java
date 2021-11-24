/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.privado.admin;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.BusquedaProveedores;
import modelo.entidades.Proveedor;
import servicio.ServicioProveedores;
import util.Parseador;

/**
 *
 * @author Aida
 */
public class BuscarProveedores extends HttpServlet {

    private ServicioProveedores servicioProveedores;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioProveedores = new ServicioProveedores();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
