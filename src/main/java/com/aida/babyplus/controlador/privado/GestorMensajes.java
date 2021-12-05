package com.aida.babyplus.controlador.privado;

import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.modelo.entidades.Mensaje;
import com.aida.babyplus.servicio.ServicioMensajes;
import com.aida.babyplus.servicio.TipoUsuario;
import com.aida.babyplus.util.Parseador;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
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
@WebServlet(name = "GestorMensajes", urlPatterns = {"/babyplus/jsp/privado/gestorMensajes"})
public class GestorMensajes extends HttpServlet {

    private ServicioMensajes servicioMensajes;
    
    @Override
    public void init() throws ServletException {
        super.init();
        servicioMensajes = new ServicioMensajes();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            Usuario usuario = ((Usuario) session.getAttribute("usuario"));
            TipoUsuario tipo = TipoUsuario.valueOf(usuario.getRol().getDescripcion());
            
            switch(tipo) {
                case CLIENTE:
                case PROVEEDOR:
                    Map<Integer, String> historico = servicioMensajes.obtenerHistorico(usuario.getId(), tipo);
                    session.setAttribute("historico", historico);
                    response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/historicoMensajes.jsp");
                    break;
                default:
                    throw new Exception("Forzando Salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        boolean esEnviarMensaje = request.getParameter("enviarMensaje") != null;
        boolean esVerMensajes = request.getParameter("verConversacion") != null;
        
        if (esEnviarMensaje) {
            enviarMensaje(request, response);
        } else if (esVerMensajes) {
            verMensajes(request, response);
        } else {
            devolverNoDisponible(request, response);
        }
    }
    
    private void enviarMensaje(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        try {
            Usuario usuario = ((Usuario) session.getAttribute("usuario"));
            TipoUsuario tipo = TipoUsuario.valueOf(usuario.getRol().getDescripcion());
            if(servicioMensajes.enviarMensaje(request, tipo)) {
                session.setAttribute("mensaje", "mensaje.enviado.ok");
            } else {
                throw new Exception("Forzando salida");
            }
        } catch (Exception e) {
            session.setAttribute("error", "mensaje.enviado.ko");
        }
        verMensajes(request, response);
    }
    
    private void verMensajes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        try {
            Usuario usuario = ((Usuario) session.getAttribute("usuario"));
            TipoUsuario tipo = TipoUsuario.valueOf(usuario.getRol().getDescripcion());
            Integer idDestino = Parseador.aNumero(request.getParameter("idDestino"));
            List<Mensaje> mensajes = new LinkedList<>();
            
            switch(tipo) {
                case CLIENTE:
                    mensajes.addAll(servicioMensajes.obtenerMensajes(usuario.getId(), idDestino));
                    break;
                case PROVEEDOR:
                    mensajes.addAll(servicioMensajes.obtenerMensajes(idDestino, usuario.getId()));
                    break;
                default:
                    throw new Exception("Forzando Salida");
            }
            session.setAttribute("idDestino", idDestino);
            session.setAttribute("mensajes", mensajes);
            response.sendRedirect(request.getContextPath() + "/babyplus/jsp/privado/mensajes.jsp");
        } catch (Exception e) {
            session.setAttribute("error", "error.generico");
            response.sendRedirect(request.getParameter("origen"));
        }
    }
    
     private void devolverNoDisponible(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        session.setAttribute("error", "cliente.gestion.error.no.disponible");
        response.sendRedirect(request.getParameter("origen"));
    }
}
