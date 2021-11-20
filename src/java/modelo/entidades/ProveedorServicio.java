/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aida
 */
@Entity
@Table(name = "proveedor_servicio")
public class ProveedorServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProveedorServicioPK proveedorServicioPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRECIO")
    private int precio;
    @JoinColumn(name = "PROVEEDOR", referencedColumnName = "USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proveedor proveedor1;
    @JoinColumn(name = "SERVICIO", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Servicio servicio1;

    public ProveedorServicio() {
    }

    public ProveedorServicio(ProveedorServicioPK proveedorServicioPK) {
        this.proveedorServicioPK = proveedorServicioPK;
    }

    public ProveedorServicio(ProveedorServicioPK proveedorServicioPK, int precio) {
        this.proveedorServicioPK = proveedorServicioPK;
        this.precio = precio;
    }

    public ProveedorServicio(int proveedor, int servicio) {
        this.proveedorServicioPK = new ProveedorServicioPK(proveedor, servicio);
    }

    public ProveedorServicioPK getProveedorServicioPK() {
        return proveedorServicioPK;
    }

    public void setProveedorServicioPK(ProveedorServicioPK proveedorServicioPK) {
        this.proveedorServicioPK = proveedorServicioPK;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Proveedor getProveedor1() {
        return proveedor1;
    }

    public void setProveedor1(Proveedor proveedor1) {
        this.proveedor1 = proveedor1;
    }

    public Servicio getServicio1() {
        return servicio1;
    }

    public void setServicio1(Servicio servicio1) {
        this.servicio1 = servicio1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proveedorServicioPK != null ? proveedorServicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProveedorServicio)) {
            return false;
        }
        ProveedorServicio other = (ProveedorServicio) object;
        if ((this.proveedorServicioPK == null && other.proveedorServicioPK != null) || (this.proveedorServicioPK != null && !this.proveedorServicioPK.equals(other.proveedorServicioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.ProveedorServicio[ proveedorServicioPK=" + proveedorServicioPK + " ]";
    }
    
}
