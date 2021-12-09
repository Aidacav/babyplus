package com.aida.babyplus.controlador.privado.admin;

import com.aida.babyplus.modelo.entidades.Post;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.servicio.ServicioPosts;
import com.aida.babyplus.servicio.TipoUsuario;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AdministrarPosts", urlPatterns = {"/babyplus/jsp/privado/admin/administrarPosts"})
public class AdministrarPosts extends HttpServlet {
    
    private ServicioPosts servicioPosts;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioPosts = new ServicioPosts();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            List<Post> posts = servicioPosts.buscarTodos();
            if(posts != null) {
                session.setAttribute("posts", posts);
                session.setAttribute("ambitos", TipoUsuario.values());
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/admin/posts.jsp");
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
        
        HttpSession session = request.getSession();
        
        try {
            Post post = servicioPosts.agregarPost(request);
            if(post != null) {
                response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/admin/administrarPosts");
            } else {
                throw new Exception("Forzando Salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
}
