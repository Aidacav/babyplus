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
public class ActualizacionClientes {
    
    private Integer id;
    private String password;
    
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String domicilio;
    private String localidad;
    private Integer cp;
    
    private String origen;

    public ActualizacionClientes(String id, String password, String nombre, String apellidos, String fechaNacimiento, String domicilio, String localidad, String cp, String origen) {
        this.id = Parseador.aNumero(id);
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = Parseador.aFecha(fechaNacimiento);
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.cp = Parseador.aNumero(cp);
        this.origen = origen;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public Integer getCp() {
        return cp;
    }

    public String getOrigen() {
        return origen;
    }
}
