package com.aida.babyplus.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Aida
 */
@Entity
@Table(name = "SOLICITUD")
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 255)
    @Column(name = "NOTAS")
    private String notas;
    @JoinColumn(name = "CLIENTE", referencedColumnName = "USUARIO")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "PACIENTE", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Paciente paciente;
    @JoinColumn(name = "PROVEEDOR", referencedColumnName = "USUARIO")
    @ManyToOne(optional = false)
    private Proveedor proveedor;
    @JoinColumn(name = "SERVICIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private ProveedorServicio servicio;
    @JoinColumn(name = "ESTADO", referencedColumnName = "NOMBRE")
    @ManyToOne(optional = false)
    private EstadoSolicitud estado;

    public Solicitud() {
    }

    public Solicitud(Integer id) {
        this.id = id;
    }

    public Solicitud(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public ProveedorServicio getServicio() {
        return servicio;
    }

    public void setServicio(ProveedorServicio servicio) {
        this.servicio = servicio;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}
