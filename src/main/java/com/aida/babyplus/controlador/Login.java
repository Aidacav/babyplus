package com.aida.babyplus.controlador;

import com.aida.babyplus.modelo.entidades.Post;
import com.aida.babyplus.modelo.entidades.Servicio;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.servicio.ServicioLogin;
import com.aida.babyplus.servicio.ServicioPosts;
import com.aida.babyplus.servicio.ServicioProveedores;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Aida
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {
    
    private ServicioLogin servicioLogin;
    private ServicioProveedores servicioProveedores;
    private ServicioPosts servicioPosts;

    @Override
    public void init() throws ServletException {
        super.init();
        servicioLogin = new ServicioLogin();
        servicioProveedores = new ServicioProveedores();
        servicioPosts = new ServicioPosts();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        try {
            Usuario usuario = servicioLogin.comprobarUsuario(login, password);

            if (session != null) {
                if (usuario != null) {
                    List<Servicio> catalogoServicios = servicioProveedores.buscarListadoServicios();
                    Map<Post, Integer> ultimosPosts = servicioPosts.buscarUltimosParaRol(usuario.getRol().getDescripcion());
                    session.setAttribute("catalogoServicios", catalogoServicios);
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("posts", ultimosPosts);
                    String paginaIndex = (request.getContextPath() + "/babyplus/jsp/privado/[ROL]/principal.jsp").replace("[ROL]", String.valueOf(usuario.getRol().getDescripcion()).toLowerCase());
                    response.sendRedirect(paginaIndex);
                } else {
                    session.setAttribute("error", "login.error.incorrecto");
                    response.sendRedirect(request.getContextPath() + "/babyplus/jsp/paginaLogin.jsp");
                }
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getContextPath() + "/babyplus/jsp/paginaLogin.jsp");
        }
    }
}
