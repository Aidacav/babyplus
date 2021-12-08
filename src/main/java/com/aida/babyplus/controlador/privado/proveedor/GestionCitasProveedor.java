package com.aida.babyplus.controlador.privado.proveedor;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aida
 */
@WebServlet(name = "GestionCitasProveedor", urlPatterns = {"/babyplus/jsp/privado/proveedor/gestionCitasProveedor"})
public class GestionCitasProveedor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getParameter("origen"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getParameter("origen"));
    }
}