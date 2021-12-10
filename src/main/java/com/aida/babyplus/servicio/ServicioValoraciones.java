package com.aida.babyplus.servicio;

import com.aida.babyplus.modelo.dao.ValoracionDAO;
import com.aida.babyplus.modelo.entidades.Proveedor;
import com.aida.babyplus.modelo.entidades.Valoracion;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Tuple;
import org.eclipse.persistence.internal.jpa.querydef.TupleImpl;

/**
 *
 * @author Aida
 */
public class ServicioValoraciones {
    
    private final ValoracionDAO valoracionDAO = new ValoracionDAO();
    
    public List<Valoracion> buscarValoracionesDeProveedor(Integer idProveedor) {
        return valoracionDAO.buscarValoracionesDeProveedor(idProveedor);
    }
    
}
