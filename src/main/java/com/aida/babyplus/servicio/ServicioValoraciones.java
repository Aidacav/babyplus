package com.aida.babyplus.servicio;

import com.aida.babyplus.modelo.dao.ValoracionDAO;
import com.aida.babyplus.modelo.entidades.Valoracion;
import java.util.List;

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
