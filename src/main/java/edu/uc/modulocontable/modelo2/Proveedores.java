/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.modelo2;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "proveedores", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedores.findAll", query = "SELECT p FROM Proveedores p"),
    @NamedQuery(name = "Proveedores.findByCodigoProveedores", query = "SELECT p FROM Proveedores p WHERE p.codigoProveedores = :codigoProveedores"),
    @NamedQuery(name = "Proveedores.findByTipoIdentificacion", query = "SELECT p FROM Proveedores p WHERE p.tipoIdentificacion = :tipoIdentificacion"),
    @NamedQuery(name = "Proveedores.findByIdentificacion", query = "SELECT p FROM Proveedores p WHERE p.identificacion = :identificacion"),
    @NamedQuery(name = "Proveedores.findByNombre", query = "SELECT p FROM Proveedores p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proveedores.findByDireccion", query = "SELECT p FROM Proveedores p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Proveedores.findByTelefono", query = "SELECT p FROM Proveedores p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Proveedores.findByAutorizacion", query = "SELECT p FROM Proveedores p WHERE p.autorizacion = :autorizacion"),
    @NamedQuery(name = "Proveedores.findByFechaCaducidadAutorizacion", query = "SELECT p FROM Proveedores p WHERE p.fechaCaducidadAutorizacion = :fechaCaducidadAutorizacion")})
public class Proveedores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_proveedores", nullable = false)
    private Integer codigoProveedores;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tipo_identificacion", nullable = false, length = 100)
    private String tipoIdentificacion;
    @Size(max = 100)
    @Column(name = "identificacion", length = 100)
    private String identificacion;
    @Size(max = 80)
    @Column(name = "nombre", length = 80)
    private String nombre;
    @Size(max = 100)
    @Column(name = "direccion", length = 100)
    private String direccion;
    @Size(max = 45)
    @Column(name = "telefono", length = 45)
    private String telefono;
    @Size(max = 120)
    @Column(name = "autorizacion", length = 120)
    private String autorizacion;
    @Column(name = "fecha_caducidad_autorizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCaducidadAutorizacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoProveedor")
    private List<CabeceraFacturac> cabeceraFacturacList;

    public Proveedores() {
    }

    public Proveedores(Integer codigoProveedores) {
        this.codigoProveedores = codigoProveedores;
    }

    public Proveedores(Integer codigoProveedores, String tipoIdentificacion) {
        this.codigoProveedores = codigoProveedores;
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public Integer getCodigoProveedores() {
        return codigoProveedores;
    }

    public void setCodigoProveedores(Integer codigoProveedores) {
        this.codigoProveedores = codigoProveedores;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Date getFechaCaducidadAutorizacion() {
        return fechaCaducidadAutorizacion;
    }

    public void setFechaCaducidadAutorizacion(Date fechaCaducidadAutorizacion) {
        this.fechaCaducidadAutorizacion = fechaCaducidadAutorizacion;
    }

    @XmlTransient
    public List<CabeceraFacturac> getCabeceraFacturacList() {
        return cabeceraFacturacList;
    }

    public void setCabeceraFacturacList(List<CabeceraFacturac> cabeceraFacturacList) {
        this.cabeceraFacturacList = cabeceraFacturacList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoProveedores != null ? codigoProveedores.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedores)) {
            return false;
        }
        Proveedores other = (Proveedores) object;
        if ((this.codigoProveedores == null && other.codigoProveedores != null) || (this.codigoProveedores != null && !this.codigoProveedores.equals(other.codigoProveedores))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.Proveedores[ codigoProveedores=" + codigoProveedores + " ]";
    }
    
}
