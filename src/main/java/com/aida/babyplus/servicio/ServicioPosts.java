package com.aida.babyplus.servicio;

import com.aida.babyplus.modelo.dao.PostDAO;
import com.aida.babyplus.modelo.entidades.Post;
import com.aida.babyplus.util.Parseador;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aida
 */
public class ServicioPosts {
    
    private final PostDAO postDAO = new PostDAO();

    public List<Post> buscarTodos() {
        return postDAO.buscarTodos();
    }
    
    public void postearNuevoProveedor(String razonSocial) {
        
        Instant ahora = Instant.now();
        
        Post nuevoPost = new Post();
        nuevoPost.setAmbito(TipoUsuario.CLIENTE.toString());
        nuevoPost.setFechaCreacion(Date.from(ahora));
        nuevoPost.setFechaExpiracion(Date.from(ahora.plus(2, ChronoUnit.DAYS)));
        nuevoPost.setPost("Un nuevo proveedor, " + razonSocial + ", se ha unido a nosotros!!!");
        
        postDAO.guardar(nuevoPost);
    }

    public Post agregarPost(HttpServletRequest request) {
        
        String ambito = request.getParameter("ambito");
        
        Post nuevoPost = new Post();
        nuevoPost.setAmbito(ambito.isBlank() ? null : ambito);
        nuevoPost.setFechaCreacion(Date.from(Instant.now()));
        nuevoPost.setFechaExpiracion(Parseador.aFecha(request.getParameter("fechaExpiracion")));
        nuevoPost.setPost(request.getParameter("post"));
        
        return postDAO.guardar(nuevoPost);
    }

    public List<Post> buscarUltimosParaRol(String rol) {
        
        return postDAO.buscarConRol(rol);
    }
}
