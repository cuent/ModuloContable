/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.modelo2;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "impuesto", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Impuesto.findAll", query = "SELECT i FROM Impuesto i"),
    @NamedQuery(name = "Impuesto.findByCodigoImpuesto", query = "SELECT i FROM Impuesto i WHERE i.codigoImpuesto = :codigoImpuesto"),
    @NamedQuery(name = "Impuesto.findByDescripcion", query = "SELECT i FROM Impuesto i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Impuesto.findByValor", query = "SELECT i FROM Impuesto i WHERE i.valor = :valor")})
public class Impuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_impuesto", nullable = false)
    private Integer codigoImpuesto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor", nullable = false)
    private double valor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "impuesto")
    private List<Producto> productoList;

    public Impuesto() {
    }

    public Impuesto(Integer codigoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
    }

    public Impuesto(Integer codigoImpuesto, String descripcion, double valor) {
        this.codigoImpuesto = codigoImpuesto;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public Integer getCodigoImpuesto() {
        return codigoImpuesto;
    }

    public void setCodigoImpuesto(Integer codigoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @XmlTransient
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoImpuesto != null ? codigoImpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Impuesto)) {
            return false;
        }
        Impuesto other = (Impuesto) object;
        if ((this.codigoImpuesto == null && other.codigoImpuesto != null) || (this.codigoImpuesto != null && !this.codigoImpuesto.equals(other.codigoImpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.Impuesto[ codigoImpuesto=" + codigoImpuesto + " ]";
    }
    
}
