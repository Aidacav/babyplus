/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aida
 */
public class Logout extends HttpServlet {
   
    @Override
    public void init() throws ServletException {
        super.init();
    }

    // <editor-fold defaultstate="collapsed" desc="Expression servletEditorFold is undefined on line 41, column 54 in Templates/JSP_Servlet/Servlet_Custom.java.">

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("usuario");
        request.getSession().removeAttribute("idioma");
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
