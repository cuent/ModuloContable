/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.modelo2;

import edu.uc.modulocontable.services.ejb.Cuenta;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "formas_pago", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormasPago.findAll", query = "SELECT f FROM FormasPago f"),
    @NamedQuery(name = "FormasPago.findByCodigoFormap", query = "SELECT f FROM FormasPago f WHERE f.codigoFormap = :codigoFormap"),
    @NamedQuery(name = "FormasPago.findByDescripcion", query = "SELECT f FROM FormasPago f WHERE f.descripcion = :descripcion")})
public class FormasPago implements Serializable {
    @JoinColumn(name = "idcodcuenta", referencedColumnName = "idcodcuenta")
    @ManyToOne
    private Cuenta idcodcuenta;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_formap", nullable = false)
    private Integer codigoFormap;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "descripcion", nullable = false, length = 45)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formaPago")
    private List<CabeceraFacturac> cabeceraFacturacList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formaPago")
    private List<CabeceraFacturav> cabeceraFacturavList;

    public FormasPago() {
    }

    public FormasPago(Integer codigoFormap) {
        this.codigoFormap = codigoFormap;
    }

    public FormasPago(Integer codigoFormap, String descripcion) {
        this.codigoFormap = codigoFormap;
        this.descripcion = descripcion;
    }

    public Integer getCodigoFormap() {
        return codigoFormap;
    }

    public void setCodigoFormap(Integer codigoFormap) {
        this.codigoFormap = codigoFormap;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<CabeceraFacturac> getCabeceraFacturacList() {
        return cabeceraFacturacList;
    }

    public void setCabeceraFacturacList(List<CabeceraFacturac> cabeceraFacturacList) {
        this.cabeceraFacturacList = cabeceraFacturacList;
    }

    @XmlTransient
    public List<CabeceraFacturav> getCabeceraFacturavList() {
        return cabeceraFacturavList;
    }

    public void setCabeceraFacturavList(List<CabeceraFacturav> cabeceraFacturavList) {
        this.cabeceraFacturavList = cabeceraFacturavList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoFormap != null ? codigoFormap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormasPago)) {
            return false;
        }
        FormasPago other = (FormasPago) object;
        if ((this.codigoFormap == null && other.codigoFormap != null) || (this.codigoFormap != null && !this.codigoFormap.equals(other.codigoFormap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.FormasPago[ codigoFormap=" + codigoFormap + " ]";
    }

    public Cuenta getIdcodcuenta() {
        return idcodcuenta;
    }

    public void setIdcodcuenta(Cuenta idcodcuenta) {
        this.idcodcuenta = idcodcuenta;
    }
    
}
