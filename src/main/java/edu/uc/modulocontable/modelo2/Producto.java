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
@Table(name = "producto", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByCodigoProducto", query = "SELECT p FROM Producto p WHERE p.codigoProducto = :codigoProducto"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByCosto", query = "SELECT p FROM Producto p WHERE p.costo = :costo"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByStock", query = "SELECT p FROM Producto p WHERE p.stock = :stock")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_producto", nullable = false)
    private Integer codigoProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costo", nullable = false)
    private double costo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio", nullable = false)
    private double precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock", nullable = false)
    private int stock;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<DetalleFacturac> detalleFacturacList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoProducto")
    private List<Kardex> kardexList;
    @JoinColumn(name = "impuesto", referencedColumnName = "codigo_impuesto", nullable = false)
    @ManyToOne(optional = false)
    private Impuesto impuesto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<DetalleFacturav> detalleFacturavList;

    public Producto() {
    }

    public Producto(Integer codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Producto(Integer codigoProducto, String nombre, double costo, double precio, int stock) {
        this.codigoProducto = codigoProducto;
        this.nombre = nombre;
        this.costo = costo;
        this.precio = precio;
        this.stock = stock;
    }

    public Integer getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Integer codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @XmlTransient
    public List<DetalleFacturac> getDetalleFacturacList() {
        return detalleFacturacList;
    }

    public void setDetalleFacturacList(List<DetalleFacturac> detalleFacturacList) {
        this.detalleFacturacList = detalleFacturacList;
    }

    @XmlTransient
    public List<Kardex> getKardexList() {
        return kardexList;
    }

    public void setKardexList(List<Kardex> kardexList) {
        this.kardexList = kardexList;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    @XmlTransient
    public List<DetalleFacturav> getDetalleFacturavList() {
        return detalleFacturavList;
    }

    public void setDetalleFacturavList(List<DetalleFacturav> detalleFacturavList) {
        this.detalleFacturavList = detalleFacturavList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoProducto != null ? codigoProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.codigoProducto == null && other.codigoProducto != null) || (this.codigoProducto != null && !this.codigoProducto.equals(other.codigoProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.Producto[ codigoProducto=" + codigoProducto + " ]";
    }
    
}
