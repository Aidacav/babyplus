package com.aida.babyplus.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.servlet.http.Part;

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
    
    public static byte[] aImagenEscalada(Part valor) throws IOException {
        if(valor == null) {
            return null;
        }
        
        int ancho = 100;
        int alto = 100;
        
        BufferedImage original = ImageIO.read(valor.getInputStream());
        BufferedImage escalada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = escalada.createGraphics();
        graphics2D.drawImage(original, 0, 0, ancho, alto, null);
        graphics2D.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(escalada, "jpg", baos);
        return baos.toByteArray();
    }
}
