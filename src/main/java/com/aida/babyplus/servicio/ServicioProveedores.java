package com.aida.babyplus.servicio;


import com.aida.babyplus.modelo.dao.ProveedorDAO;
import com.aida.babyplus.modelo.dao.ServicioDAO;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.modelo.entidades.ProveedorServicio;
import com.aida.babyplus.modelo.entidades.Servicio;
import com.aida.babyplus.modelo.entidades.Usuario;
import com.aida.babyplus.util.Parseador;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Aida
 */
public class ServicioProveedores {
    
    private final ProveedorDAO proveedorDAO = new ProveedorDAO();
    private final ServicioDAO servicioDAO = new ServicioDAO();
    
    public Proveedor buscarPorid(Integer id) {
        if(id == null) {
            return null;
        }
        return proveedorDAO.buscarPorId(id);
    }

    public List<Proveedor> buscarPorCriteriosAdmin(HttpServletRequest request) {
        
        Proveedor proveedorABuscar = aProveedor(request);
        if(proveedorABuscar.getUsuario() != null) {
            Proveedor proveedor = buscarPorid(proveedorABuscar.getUsuario());
            return proveedor != null ? new LinkedList<Proveedor>(){{add(proveedor);}} : new LinkedList<>();
        }
        
        return proveedorDAO.buscarPorCriteriosAdmin(proveedorABuscar);
    }

    public Proveedor actualizarProveedor(HttpServletRequest request) {
        
        Proveedor datosProveedor = aProveedor(request);
        String nuevoPassword = request.getParameter("password");
        return proveedorDAO.actualizarValores(datosProveedor, nuevoPassword);
    }

    public Proveedor crearProveedor(Usuario nuevoUsuario, byte[] logo, HttpServletRequest request) {
        
        Proveedor nuevoProveedor = aProveedor(request);
        nuevoProveedor.setUsuario(nuevoUsuario.getId());
        nuevoProveedor.setUsuario1(nuevoUsuario);
        nuevoProveedor.setLogo(logo);
        
        return proveedorDAO.guardar(nuevoProveedor);
    }
    
    public List<Proveedor> buscarPorCriteriosCliente(HttpServletRequest request) {
        
        Proveedor proveedorABuscar = aProveedor(request);
        List<Proveedor> proveedores = proveedorDAO.buscarPorCriteriosCliente(proveedorABuscar);
        Integer tipoServicioSeleccionado = Parseador.aNumero(request.getParameter("servicio"));
        
        if(tipoServicioSeleccionado != 0) {
            List<Proveedor> proveedoresConServicio = new LinkedList<>();
            for(Proveedor posible : proveedores) {
                for(ProveedorServicio servicio : posible.getServicios()) {
                    if(servicio.getServicio().getId() == tipoServicioSeleccionado) {
                        proveedoresConServicio.add(posible);
                        break;
                    }
                }
            }
            return proveedoresConServicio;
        }

        return proveedores;
    }
    
    public List<Servicio> buscarListadoServicios() {
        return servicioDAO.buscarTodos();
    }
    
    public Proveedor agregarServicio(HttpServletRequest request) {
        
        ProveedorServicio nuevoServicio = aProveedorServicio(request);
        return nuevoServicio == null ? null : proveedorDAO.agregarServicio(nuevoServicio);
    }
    
    public Proveedor actualizarServicio(HttpServletRequest request) {
        
        ProveedorServicio datosServicio = aProveedorServicio(request);
        return datosServicio == null ? null : proveedorDAO.actualizarServicio(datosServicio);
    }
    
    public Proveedor eliminarServicio(HttpServletRequest request) {
        
        ProveedorServicio datosServicio = aProveedorServicio(request);
        return datosServicio == null ? null : proveedorDAO.eliminarServicio(datosServicio);
    }
    
    private Proveedor aProveedor(HttpServletRequest request) {
        
        Usuario usuario = new Usuario();
        
        usuario.setUsuario(request.getParameter("usuario"));
        usuario.setActivo(Parseador.aBoolean(request.getParameter("activo")));
        usuario.setFechaAlta(Parseador.aFecha(request.getParameter("fechaAlta")));
        
        Proveedor proveedor = new Proveedor();
        proveedor.setRazonSocial(request.getParameter("razon"));
        proveedor.setCif(request.getParameter("cif"));
        proveedor.setDireccion(request.getParameter("direccion"));
        proveedor.setLocalidad(request.getParameter("localidad"));
        proveedor.setCp(Parseador.aNumero(request.getParameter("cp")));
        proveedor.setResponsable(request.getParameter("responsable"));
        proveedor.setUsuario(Parseador.aNumero(request.getParameter("id")));
        proveedor.setUsuario1(usuario);
        
        return proveedor;
    }
    
    private ProveedorServicio aProveedorServicio(HttpServletRequest request) {
        
        Integer idProveedor = ((Usuario)request.getSession().getAttribute("usuario")).getId();
        Integer idServicioBase = Parseador.aNumero(request.getParameter("tipo"));
        
        Proveedor proveedor = proveedorDAO.buscarPorId(idProveedor);
        Servicio servicioBase = servicioDAO.buscarPorId(idServicioBase);
        
        if(proveedor == null || servicioBase == null) {
            return null;
        }

        ProveedorServicio servicio = new ProveedorServicio();
        servicio.setProveedor(proveedor);
        servicio.setServicio(servicioBase);
        servicio.setId(Parseador.aNumero(request.getParameter("idServicio")));
        servicio.setPrecio(Parseador.aNumero(request.getParameter("precio")));
        servicio.setDescripcion(request.getParameter("descripcion"));
        
        return servicio;
    }
}
