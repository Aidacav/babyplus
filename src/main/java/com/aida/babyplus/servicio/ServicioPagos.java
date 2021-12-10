package com.aida.babyplus.servicio;

import com.aida.babyplus.util.Parseador;

/**
 *
 * @author Aida
 */
public class ServicioPagos {
    
    // Devolvera true si la tarjeta es par, false si no
    public boolean tarjetaValida(String numeroTarjeta) {
        Long tarjeta = Parseador.aNumeroGrande(numeroTarjeta);
        return tarjeta != null && tarjeta % 2 == 0;
    }
}
