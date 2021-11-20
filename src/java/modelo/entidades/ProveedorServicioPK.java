/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Aida
 */
@Embeddable
public class ProveedorServicioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "PROVEEDOR")
    private int proveedor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SERVICIO")
    private int servicio;

    public ProveedorServicioPK() {
    }

    public ProveedorServicioPK(int proveedor, int servicio) {
        this.proveedor = proveedor;
        this.servicio = servicio;
    }

    public int getProveedor() {
        return proveedor;
    }

    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }

    public int getServicio() {
        return servicio;
    }

    public void setServicio(int servicio) {
        this.servicio = servicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) proveedor;
        hash += (int) servicio;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProveedorServicioPK)) {
            return false;
        }
        ProveedorServicioPK other = (ProveedorServicioPK) object;
        if (this.proveedor != other.proveedor) {
            return false;
        }
        if (this.servicio != other.servicio) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.ProveedorServicioPK[ proveedor=" + proveedor + ", servicio=" + servicio + " ]";
    }
    
}
