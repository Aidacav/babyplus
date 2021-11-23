/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;


import java.util.List;
import modelo.BusquedaClientes;
import modelo.dao.ClienteDAO;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Cliente;
import modelo.entidades.Usuario;
import org.apache.tomcat.util.codec.binary.StringUtils;

/**
 *
 * @author Aida
 */
public class ServicioClientes {
    
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    public Cliente buscarPorid(Integer idCliente) {
        
        List<Cliente> clientes = clienteDAO.buscarPorId(idCliente);
        if(!clientes.isEmpty()) {
            return clientes.get(0);
        }
        return null;
    }

    public List<Cliente> buscarPorCriterios(BusquedaClientes criterios) {
        
        if(criterios.getId() != null) {
            return clienteDAO.buscarPorId(criterios.getId());
        }
        
        return clienteDAO.buscarPorCriterios(criterios);
    }

    public void cambiarEstado(Usuario usuarioAModificar) {
        usuarioAModificar.setActivo(!usuarioAModificar.getActivo());
        usuarioDAO.actualizar(usuarioAModificar);
    }
}
