package com.aida.babyplus.servicio;

import com.aida.babyplus.modelo.dao.ClienteDAO;
import com.aida.babyplus.modelo.dao.MensajeDAO;
import com.aida.babyplus.modelo.dao.ProveedorDAO;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.Mensaje;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.util.Parseador;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aida
 */
public class ServicioMensajes {
    
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final MensajeDAO mensajeDAO = new MensajeDAO();

    public boolean enviarMensaje(HttpServletRequest request, TipoUsuario tipoUsuario) {
        
        Mensaje mensajeAEnviar = aMensaje(request, tipoUsuario);
        return mensajeAEnviar != null && mensajeDAO.guardar(mensajeAEnviar) != null;
    }

    public Map<Integer, String> obtenerHistorico(Integer idUsuario, TipoUsuario tipo) throws Exception {
        
        List<Mensaje> listadoMensajes = mensajeDAO.buscarHistorico(idUsuario, tipo);
        Map<Integer, String> historico = new HashMap<>();
        
        for(Mensaje mensaje : listadoMensajes) {
            Integer id;
            String nombre;
            
            switch(tipo) {
                case CLIENTE:
                    Proveedor proveedor = mensaje.getProveedor();
                    id = proveedor.getUsuario();
                    nombre = proveedor.getRazonSocial();
                    break;
                case PROVEEDOR:
                    Cliente cliente = mensaje.getCliente();
                    id = cliente.getUsuario();
                    nombre = cliente.getNombre() + " " + cliente.getApellidos();
                    break;
                default:
                    throw new Exception("Forzando Salida");
            }
            
            
            historico.putIfAbsent(id, nombre);
        }
        
        return historico;
    }
    
    public List<Mensaje> obtenerMensajes(Integer idCliente, Integer idProveedor) {
        return mensajeDAO.buscarPorClienteYProveedor(idCliente, idProveedor);
    }
    
    private Mensaje aMensaje(HttpServletRequest request, TipoUsuario tipoUsuario) {
        
        Integer idCliente;
        Integer idProveedor;
        
        switch (tipoUsuario) {
            case CLIENTE:
                idCliente = Parseador.aNumero(request.getParameter("idOrigen"));
                idProveedor = Parseador.aNumero(request.getParameter("idDestino"));
                break;
            case PROVEEDOR:
                idCliente = Parseador.aNumero(request.getParameter("idDestino"));
                idProveedor = Parseador.aNumero(request.getParameter("idOrigen"));
                break;
            default:
                return null;
        }
        
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        Proveedor proveedor = proveedorDAO.buscarPorId(idProveedor);
        
        if(cliente == null || proveedor == null) {
            return null;
        }
        
        Mensaje mensaje = new Mensaje();
        mensaje.setFecha(Date.from(Instant.now()));
        mensaje.setMensaje(request.getParameter("mensaje"));
        mensaje.setOrigen(Parseador.aNumero(request.getParameter("idOrigen")));
        mensaje.setCliente(cliente);
        mensaje.setProveedor(proveedor);
        
        return mensaje;
    }
}
