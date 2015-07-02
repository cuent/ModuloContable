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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cabecera_facturac", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CabeceraFacturac.findAll", query = "SELECT c FROM CabeceraFacturac c"),
    @NamedQuery(name = "CabeceraFacturac.findByCodigoFactura", query = "SELECT c FROM CabeceraFacturac c WHERE c.codigoFactura = :codigoFactura"),
    @NamedQuery(name = "CabeceraFacturac.findByNumeroFactura", query = "SELECT c FROM CabeceraFacturac c WHERE c.numeroFactura = :numeroFactura"),
    @NamedQuery(name = "CabeceraFacturac.findByFecha", query = "SELECT c FROM CabeceraFacturac c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CabeceraFacturac.findByAutorizacionSri", query = "SELECT c FROM CabeceraFacturac c WHERE c.autorizacionSri = :autorizacionSri"),
    @NamedQuery(name = "CabeceraFacturac.findByEstablecimiento", query = "SELECT c FROM CabeceraFacturac c WHERE c.establecimiento = :establecimiento"),
    @NamedQuery(name = "CabeceraFacturac.findByPtoEmision", query = "SELECT c FROM CabeceraFacturac c WHERE c.ptoEmision = :ptoEmision"),
    @NamedQuery(name = "CabeceraFacturac.findBySubtotal", query = "SELECT c FROM CabeceraFacturac c WHERE c.subtotal = :subtotal"),
    @NamedQuery(name = "CabeceraFacturac.findBySubtotalBase0", query = "SELECT c FROM CabeceraFacturac c WHERE c.subtotalBase0 = :subtotalBase0"),
    @NamedQuery(name = "CabeceraFacturac.findBySubtotalBaseIva", query = "SELECT c FROM CabeceraFacturac c WHERE c.subtotalBaseIva = :subtotalBaseIva"),
    @NamedQuery(name = "CabeceraFacturac.findByDescuento", query = "SELECT c FROM CabeceraFacturac c WHERE c.descuento = :descuento"),
    @NamedQuery(name = "CabeceraFacturac.findByIva", query = "SELECT c FROM CabeceraFacturac c WHERE c.iva = :iva"),
    @NamedQuery(name = "CabeceraFacturac.findByTotal", query = "SELECT c FROM CabeceraFacturac c WHERE c.total = :total")})
public class CabeceraFacturac implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_factura", nullable = false)
    private Integer codigoFactura;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numero_factura", nullable = false, length = 45)
    private String numeroFactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "autorizacion_sri", nullable = false, length = 120)
    private String autorizacionSri;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "establecimiento", nullable = false, length = 45)
    private String establecimiento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pto_emision", nullable = false, length = 45)
    private String ptoEmision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subtotal", nullable = false)
    private double subtotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subtotal_base_0", nullable = false)
    private double subtotalBase0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subtotal_base_iva", nullable = false)
    private double subtotalBaseIva;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "descuento", precision = 22)
    private Double descuento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iva", nullable = false)
    private double iva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total", nullable = false)
    private double total;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cabeceraFacturac")
    private List<DetalleFacturac> detalleFacturacList;
    @OneToMany(mappedBy = "codigoFacturac")
    private List<Kardex> kardexList;
    @JoinColumn(name = "forma_pago", referencedColumnName = "codigo_formap", nullable = false)
    @ManyToOne(optional = false)
    private FormasPago formaPago;
    @JoinColumn(name = "codigo_proveedor", referencedColumnName = "codigo_proveedores", nullable = false)
    @ManyToOne(optional = false)
    private Proveedores codigoProveedor;

    public CabeceraFacturac() {
    }

    public CabeceraFacturac(Integer codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public CabeceraFacturac(Integer codigoFactura, String numeroFactura, Date fecha, String autorizacionSri, String establecimiento, String ptoEmision, double subtotal, double subtotalBase0, double subtotalBaseIva, double iva, double total) {
        this.codigoFactura = codigoFactura;
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.autorizacionSri = autorizacionSri;
        this.establecimiento = establecimiento;
        this.ptoEmision = ptoEmision;
        this.subtotal = subtotal;
        this.subtotalBase0 = subtotalBase0;
        this.subtotalBaseIva = subtotalBaseIva;
        this.iva = iva;
        this.total = total;
    }

    public Integer getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(Integer codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getAutorizacionSri() {
        return autorizacionSri;
    }

    public void setAutorizacionSri(String autorizacionSri) {
        this.autorizacionSri = autorizacionSri;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getPtoEmision() {
        return ptoEmision;
    }

    public void setPtoEmision(String ptoEmision) {
        this.ptoEmision = ptoEmision;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getSubtotalBase0() {
        return subtotalBase0;
    }

    public void setSubtotalBase0(double subtotalBase0) {
        this.subtotalBase0 = subtotalBase0;
    }

    public double getSubtotalBaseIva() {
        return subtotalBaseIva;
    }

    public void setSubtotalBaseIva(double subtotalBaseIva) {
        this.subtotalBaseIva = subtotalBaseIva;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public FormasPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormasPago formaPago) {
        this.formaPago = formaPago;
    }

    public Proveedores getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(Proveedores codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoFactura != null ? codigoFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CabeceraFacturac)) {
            return false;
        }
        CabeceraFacturac other = (CabeceraFacturac) object;
        if ((this.codigoFactura == null && other.codigoFactura != null) || (this.codigoFactura != null && !this.codigoFactura.equals(other.codigoFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.CabeceraFacturac[ codigoFactura=" + codigoFactura + " ]";
    }
    
}
