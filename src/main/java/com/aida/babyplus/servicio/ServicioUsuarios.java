/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.servicio;

import com.aida.babyplus.modelo.dao.RolDAO;
import com.aida.babyplus.modelo.dao.UsuarioDAO;
import com.aida.babyplus.modelo.entidades.Rol;
import com.aida.babyplus.modelo.entidades.Usuario;
import java.time.Instant;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aida
 */
public class ServicioUsuarios {
    
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final RolDAO rolDAO = new RolDAO();
    
    public void cambiarEstado(Integer id) {
        usuarioDAO.cambiarEstado(id);
    }

    public boolean existeUsuario(String usuario) {
        return usuarioDAO.buscarPorUsuario(usuario) != null;
    }
    
    public Usuario creaNuevoUsuario(HttpServletRequest request, TipoUsuario tipoUsuario) {
        
        Rol rol = rolDAO.buscarPorDescripcion(tipoUsuario.toString());
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsuario(request.getParameter("usuario"));
        nuevoUsuario.setPassword(request.getParameter("password"));
        nuevoUsuario.setIdioma(request.getParameter("idioma"));
        nuevoUsuario.setActivo(true);
        nuevoUsuario.setFechaAlta(Date.from(Instant.now()));
        nuevoUsuario.setRol(rol);

        return usuarioDAO.guardar(nuevoUsuario);
    }
}
