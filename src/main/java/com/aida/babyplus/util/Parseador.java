package com.aida.babyplus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Aida
 */
public final class Parseador {
    
    private static final SimpleDateFormat SDF =  new SimpleDateFormat("yyyy-MM-dd");

    public static final Integer aNumero(String valor) {
        if(valor == null || valor.equals("null")) {
            return null;
        }
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

    public static Date aFecha(String valor) {
        if(valor == null || valor.equals("null")) {
            return null;
        }
        try {
            return SDF.parse(valor);
        } catch (ParseException e) {
            // No es parseable
        }
        return null;
    }
    
    public static byte[] aBytes(String valor) {
        if(valor == null || valor.equals("null")) {
            return null;
        }
        return valor.getBytes();
    }
}
