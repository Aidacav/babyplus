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
public class BusquedaClientes {
    
    private Integer id;
    private String nombreUsuario;
    private String nombre;
    private String apellidos;
    private Date fechaAlta;
    private Boolean activo;
    private String origen;

    public BusquedaClientes(String id, String nombreUsuario, String nombre, String apellidos, String fechaAlta, String activo, String origen) {
        this.id = Parseador.aNumero(id);
        this.nombreUsuario = Parseador.aLike(nombreUsuario);
        this.nombre = Parseador.aLike(nombre);
        this.apellidos = Parseador.aLike(apellidos);
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
    
    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
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
