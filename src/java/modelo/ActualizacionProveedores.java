/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;
import util.Parseador;

/**
 *
 * @author Aida
 */
public class ActualizacionProveedores {
    
    private Integer id;
    private String password;
    
    private String razonSocial;
    private String cif;
    private String direccion;
    private String localidad;
    private Integer cp;
    private String responsable;
    
    private String origen;

    public ActualizacionProveedores(String id, String password, String razonSocial, String cif, String direccion, String localidad, String cp, String responsable, String origen) {
        this.id = Parseador.aNumero(id);
        this.password = password;
        this.razonSocial = razonSocial;
        this.cif = cif;
        this.direccion = direccion;
        this.localidad = localidad;
        this.cp = Parseador.aNumero(cp);
        this.responsable = responsable;
        this.origen = origen;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getCif() {
        return cif;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Integer getCp() {
        return cp;
    }

    public String getResponsable() {
        return responsable;
    }

    public String getOrigen() {
        return origen;
    }
}
