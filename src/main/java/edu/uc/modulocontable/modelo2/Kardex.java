/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.modelo2;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "kardex", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kardex.findAll", query = "SELECT k FROM Kardex k"),
    @NamedQuery(name = "Kardex.findByCodigoKardex", query = "SELECT k FROM Kardex k WHERE k.codigoKardex = :codigoKardex"),
    @NamedQuery(name = "Kardex.findByFecha", query = "SELECT k FROM Kardex k WHERE k.fecha = :fecha"),
    @NamedQuery(name = "Kardex.findByTipo", query = "SELECT k FROM Kardex k WHERE k.tipo = :tipo"),
    @NamedQuery(name = "Kardex.findByDetalle", query = "SELECT k FROM Kardex k WHERE k.detalle = :detalle"),
    @NamedQuery(name = "Kardex.findByCantidad", query = "SELECT k FROM Kardex k WHERE k.cantidad = :cantidad"),
    @NamedQuery(name = "Kardex.findByCosto", query = "SELECT k FROM Kardex k WHERE k.costo = :costo"),
    @NamedQuery(name = "Kardex.findBySubtotal", query = "SELECT k FROM Kardex k WHERE k.subtotal = :subtotal"),
    @NamedQuery(name = "Kardex.findByTotalCantidad", query = "SELECT k FROM Kardex k WHERE k.totalCantidad = :totalCantidad"),
    @NamedQuery(name = "Kardex.findByTotalCosto", query = "SELECT k FROM Kardex k WHERE k.totalCosto = :totalCosto"),
    @NamedQuery(name = "Kardex.findByTotalSubtotal", query = "SELECT k FROM Kardex k WHERE k.totalSubtotal = :totalSubtotal")})
public class Kardex implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_kardex", nullable = false)
    private Integer codigoKardex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo", nullable = false, length = 45)
    private String tipo;
    @Size(max = 100)
    @Column(name = "detalle", length = 100)
    private String detalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costo", nullable = false)
    private double costo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subtotal", nullable = false)
    private double subtotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_cantidad", nullable = false)
    private int totalCantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_costo", nullable = false)
    private double totalCosto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_subtotal", nullable = false)
    private double totalSubtotal;
    @JoinColumn(name = "codigo_facturac", referencedColumnName = "codigo_factura")
    @ManyToOne
    private CabeceraFacturac codigoFacturac;
    @JoinColumn(name = "codigo_producto", referencedColumnName = "codigo_producto", nullable = false)
    @ManyToOne(optional = false)
    private Producto codigoProducto;
    @JoinColumn(name = "codigo_facturav", referencedColumnName = "codigo_factura")
    @ManyToOne
    private CabeceraFacturav codigoFacturav;

    public Kardex() {
    }

    public Kardex(Integer codigoKardex) {
        this.codigoKardex = codigoKardex;
    }

    public Kardex(Integer codigoKardex, Date fecha, String tipo, int cantidad, double costo, double subtotal, int totalCantidad, double totalCosto, double totalSubtotal) {
        this.codigoKardex = codigoKardex;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.costo = costo;
        this.subtotal = subtotal;
        this.totalCantidad = totalCantidad;
        this.totalCosto = totalCosto;
        this.totalSubtotal = totalSubtotal;
    }

    public Integer getCodigoKardex() {
        return codigoKardex;
    }

    public void setCodigoKardex(Integer codigoKardex) {
        this.codigoKardex = codigoKardex;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getTotalCantidad() {
        return totalCantidad;
    }

    public void setTotalCantidad(int totalCantidad) {
        this.totalCantidad = totalCantidad;
    }

    public double getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(double totalCosto) {
        this.totalCosto = totalCosto;
    }

    public double getTotalSubtotal() {
        return totalSubtotal;
    }

    public void setTotalSubtotal(double totalSubtotal) {
        this.totalSubtotal = totalSubtotal;
    }

    public CabeceraFacturac getCodigoFacturac() {
        return codigoFacturac;
    }

    public void setCodigoFacturac(CabeceraFacturac codigoFacturac) {
        this.codigoFacturac = codigoFacturac;
    }

    public Producto getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Producto codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public CabeceraFacturav getCodigoFacturav() {
        return codigoFacturav;
    }

    public void setCodigoFacturav(CabeceraFacturav codigoFacturav) {
        this.codigoFacturav = codigoFacturav;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoKardex != null ? codigoKardex.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kardex)) {
            return false;
        }
        Kardex other = (Kardex) object;
        if ((this.codigoKardex == null && other.codigoKardex != null) || (this.codigoKardex != null && !this.codigoKardex.equals(other.codigoKardex))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.Kardex[ codigoKardex=" + codigoKardex + " ]";
    }
    
}
