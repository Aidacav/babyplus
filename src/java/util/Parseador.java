/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import modelo.BusquedaClientes;

/**
 *
 * @author Aida
 */
public final class Parseador {
    
    private static final SimpleDateFormat SDF =  new SimpleDateFormat("yyyy-MM-dd");
    
    // Peticiones
    public static BusquedaClientes aBusquedaClientes(HttpServletRequest request) {
        return new BusquedaClientes(
                request.getParameter("idCliente"),
                request.getParameter("usuarioCliente"), 
                request.getParameter("nombreCliente"), 
                request.getParameter("apellidosCliente"),
                request.getParameter("fechaCliente"),
                request.getParameter("estadoCliente"),
                request.getParameter("origen"));
    }
    
    // Genéricos
    public static final Integer aNumero(String valor) {
        try {
            return Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            // No es parseable
        }
        return null;
    }
    
    public static final String aLike(String valor) {
        if(valor == null || valor.equals("null")) {
            return null;
        }
        return "%".concat(valor).concat("%");
    }

    public static Boolean aBoolean(String valor) {
        if(valor == null || valor.equals("null")) {
            return null;
        }
        return valor.equals("1");
    }

    public static Date aFecha(String fechaAlta) {
        if(fechaAlta == null || fechaAlta.equals("null")) {
            return null;
        }
        try {
            return SDF.parse(fechaAlta);
        } catch (ParseException e) {
            // No es parseable
        }
        return null;
    }
}
