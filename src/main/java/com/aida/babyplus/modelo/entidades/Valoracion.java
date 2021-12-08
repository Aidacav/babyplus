package com.aida.babyplus.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "valoracion")
public class Valoracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CITA")
    private Integer cita;
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHUPETES")
    private int chupetes;
    @Size(max = 255)
    @Column(name = "MENSAJE")
    private String mensaje;
    @JoinColumn(name = "CITA", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Cita cita1;

    public Valoracion() {
    }

    public Valoracion(Integer cita) {
        this.cita = cita;
    }

    public Valoracion(Integer cita, int chupetes) {
        this.cita = cita;
        this.chupetes = chupetes;
    }

    public Integer getCita() {
        return cita;
    }

    public void setCita(Integer cita) {
        this.cita = cita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getChupetes() {
        return chupetes;
    }

    public void setChupetes(int chupetes) {
        this.chupetes = chupetes;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Cita getCita1() {
        return cita1;
    }

    public void setCita1(Cita cita1) {
        this.cita1 = cita1;
    }
}
