/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.modelo2;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "detalle_facturac", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleFacturac.findAll", query = "SELECT d FROM DetalleFacturac d"),
    @NamedQuery(name = "DetalleFacturac.findByCodigoFactura", query = "SELECT d FROM DetalleFacturac d WHERE d.detalleFacturacPK.codigoFactura = :codigoFactura"),
    @NamedQuery(name = "DetalleFacturac.findByCodigoProducto", query = "SELECT d FROM DetalleFacturac d WHERE d.detalleFacturacPK.codigoProducto = :codigoProducto"),
    @NamedQuery(name = "DetalleFacturac.findByCantidad", query = "SELECT d FROM DetalleFacturac d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleFacturac.findByPrecioUnitario", query = "SELECT d FROM DetalleFacturac d WHERE d.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "DetalleFacturac.findByTotal", query = "SELECT d FROM DetalleFacturac d WHERE d.total = :total")})
public class DetalleFacturac implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetalleFacturacPK detalleFacturacPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad", nullable = false)
    private int cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total", nullable = false)
    private double total;
    @JoinColumn(name = "codigo_factura", referencedColumnName = "codigo_factura", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CabeceraFacturac cabeceraFacturac;
    @JoinColumn(name = "codigo_producto", referencedColumnName = "codigo_producto", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public DetalleFacturac() {
    }

    public DetalleFacturac(DetalleFacturacPK detalleFacturacPK) {
        this.detalleFacturacPK = detalleFacturacPK;
    }

    public DetalleFacturac(DetalleFacturacPK detalleFacturacPK, int cantidad, double precioUnitario, double total) {
        this.detalleFacturacPK = detalleFacturacPK;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public DetalleFacturac(int codigoFactura, int codigoProducto) {
        this.detalleFacturacPK = new DetalleFacturacPK(codigoFactura, codigoProducto);
    }

    public DetalleFacturacPK getDetalleFacturacPK() {
        return detalleFacturacPK;
    }

    public void setDetalleFacturacPK(DetalleFacturacPK detalleFacturacPK) {
        this.detalleFacturacPK = detalleFacturacPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CabeceraFacturac getCabeceraFacturac() {
        return cabeceraFacturac;
    }

    public void setCabeceraFacturac(CabeceraFacturac cabeceraFacturac) {
        this.cabeceraFacturac = cabeceraFacturac;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (detalleFacturacPK != null ? detalleFacturacPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFacturac)) {
            return false;
        }
        DetalleFacturac other = (DetalleFacturac) object;
        if ((this.detalleFacturacPK == null && other.detalleFacturacPK != null) || (this.detalleFacturacPK != null && !this.detalleFacturacPK.equals(other.detalleFacturacPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.DetalleFacturac[ detalleFacturacPK=" + detalleFacturacPK + " ]";
    }
    
}
