package com.aida.babyplus.modelo.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Aida
 */
@Entity
@Table(name = "PROVEEDOR")
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USUARIO")
    private Integer usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "CIF")
    private String cif;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "LOCALIDAD")
    private String localidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CP")
    private Integer cp;
    @Basic(optional = false)
    @Lob
    @Column(name = "LOGO")
    private byte[] logo;
    @Size(max = 100)
    @Column(name = "RESPONSABLE")
    private String responsable;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Usuario usuario1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedor")
    private Collection<ProveedorServicio> servicios;

    public Proveedor() {
    }

    public Proveedor(Integer usuario) {
        this.usuario = usuario;
    }

    public Proveedor(Integer usuario, String razonSocial, String cif, String direccion, String localidad, int cp, byte[] logo) {
        this.usuario = usuario;
        this.razonSocial = razonSocial;
        this.cif = cif;
        this.direccion = direccion;
        this.localidad = localidad;
        this.cp = cp;
        this.logo = logo;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public Collection<ProveedorServicio> getServicios() {
        return servicios;
    }

    public void setServicios(Collection<ProveedorServicio> servicios) {
        this.servicios = servicios;
    }
}
