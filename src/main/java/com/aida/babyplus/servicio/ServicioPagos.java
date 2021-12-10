package com.aida.babyplus.servicio;

import com.aida.babyplus.util.Parseador;

/**
 *
 * @author Aida
 */
public class ServicioPagos {
    
    // Devolvera true si la tarjeta es par, false si no
    public boolean tarjetaValida(String numeroTarjeta) {
        Integer tarjeta = Parseador.aNumero(numeroTarjeta);
        return tarjeta != null && tarjeta % 2 == 0;
    }
}
