/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;

/**
 *
 * @author Aida
 */
public class ServicioLogin {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario comprobarUsuario(String login, String password) {
        
        return usuarioDAO.buscarUsuarioParaLogin(login, password);
    }
    
}
