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
@Table(name = "detalle_facturav", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleFacturav.findAll", query = "SELECT d FROM DetalleFacturav d"),
    @NamedQuery(name = "DetalleFacturav.findByCodigoFactura", query = "SELECT d FROM DetalleFacturav d WHERE d.detalleFacturavPK.codigoFactura = :codigoFactura"),
    @NamedQuery(name = "DetalleFacturav.findByCodigoProducto", query = "SELECT d FROM DetalleFacturav d WHERE d.detalleFacturavPK.codigoProducto = :codigoProducto"),
    @NamedQuery(name = "DetalleFacturav.findByCantidad", query = "SELECT d FROM DetalleFacturav d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleFacturav.findByPrecioUnitario", query = "SELECT d FROM DetalleFacturav d WHERE d.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "DetalleFacturav.findByTotal", query = "SELECT d FROM DetalleFacturav d WHERE d.total = :total")})
public class DetalleFacturav implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DetalleFacturavPK detalleFacturavPK;
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
    private CabeceraFacturav cabeceraFacturav;
    @JoinColumn(name = "codigo_producto", referencedColumnName = "codigo_producto", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public DetalleFacturav() {
    }

    public DetalleFacturav(DetalleFacturavPK detalleFacturavPK) {
        this.detalleFacturavPK = detalleFacturavPK;
    }

    public DetalleFacturav(DetalleFacturavPK detalleFacturavPK, int cantidad, double precioUnitario, double total) {
        this.detalleFacturavPK = detalleFacturavPK;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public DetalleFacturav(int codigoFactura, int codigoProducto) {
        this.detalleFacturavPK = new DetalleFacturavPK(codigoFactura, codigoProducto);
    }

    public DetalleFacturavPK getDetalleFacturavPK() {
        return detalleFacturavPK;
    }

    public void setDetalleFacturavPK(DetalleFacturavPK detalleFacturavPK) {
        this.detalleFacturavPK = detalleFacturavPK;
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

    public CabeceraFacturav getCabeceraFacturav() {
        return cabeceraFacturav;
    }

    public void setCabeceraFacturav(CabeceraFacturav cabeceraFacturav) {
        this.cabeceraFacturav = cabeceraFacturav;
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
        hash += (detalleFacturavPK != null ? detalleFacturavPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFacturav)) {
            return false;
        }
        DetalleFacturav other = (DetalleFacturav) object;
        if ((this.detalleFacturavPK == null && other.detalleFacturavPK != null) || (this.detalleFacturavPK != null && !this.detalleFacturavPK.equals(other.detalleFacturavPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.DetalleFacturav[ detalleFacturavPK=" + detalleFacturavPK + " ]";
    }
    
}
