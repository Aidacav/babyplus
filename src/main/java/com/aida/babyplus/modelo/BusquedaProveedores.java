/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.modelo;

import java.util.Date;
import com.aida.babyplus.util.Parseador;

/**
 *
 * @author Aida
 */
public class BusquedaProveedores {
    
    private Integer id;
    private String nombreUsuario;
    private String razonSocial;
    private String cif;
    private Date fechaAlta;
    private Boolean activo;
    private String origen;

    public BusquedaProveedores(String id, String nombreUsuario, String razonSocial, String cif, String fechaAlta, String activo, String origen) {
        this.id = Parseador.aNumero(id);
        this.nombreUsuario = Parseador.aLike(nombreUsuario);
        this.razonSocial = Parseador.aLike(razonSocial);
        this.cif = Parseador.aLike(cif);
        this.fechaAlta = Parseador.aFecha(fechaAlta);
        this.activo = Parseador.aBoolean(activo);
        this.origen = origen;
    }

    public Integer getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getCif() {
        return cif;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public String getOrigen() {
        return origen;
    }
}
