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
@Table(name = "cabecera_facturav", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CabeceraFacturav.findAll", query = "SELECT c FROM CabeceraFacturav c"),
    @NamedQuery(name = "CabeceraFacturav.findByCodigoFactura", query = "SELECT c FROM CabeceraFacturav c WHERE c.codigoFactura = :codigoFactura"),
    @NamedQuery(name = "CabeceraFacturav.findByNumeroFactura", query = "SELECT c FROM CabeceraFacturav c WHERE c.numeroFactura = :numeroFactura"),
    @NamedQuery(name = "CabeceraFacturav.findByFecha", query = "SELECT c FROM CabeceraFacturav c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CabeceraFacturav.findByAutorizacionSri", query = "SELECT c FROM CabeceraFacturav c WHERE c.autorizacionSri = :autorizacionSri"),
    @NamedQuery(name = "CabeceraFacturav.findByEstablecimiento", query = "SELECT c FROM CabeceraFacturav c WHERE c.establecimiento = :establecimiento"),
    @NamedQuery(name = "CabeceraFacturav.findByPtoEmision", query = "SELECT c FROM CabeceraFacturav c WHERE c.ptoEmision = :ptoEmision"),
    @NamedQuery(name = "CabeceraFacturav.findBySubtotal", query = "SELECT c FROM CabeceraFacturav c WHERE c.subtotal = :subtotal"),
    @NamedQuery(name = "CabeceraFacturav.findBySubtotalBase0", query = "SELECT c FROM CabeceraFacturav c WHERE c.subtotalBase0 = :subtotalBase0"),
    @NamedQuery(name = "CabeceraFacturav.findBySubtotalBaseIva", query = "SELECT c FROM CabeceraFacturav c WHERE c.subtotalBaseIva = :subtotalBaseIva"),
    @NamedQuery(name = "CabeceraFacturav.findByDescuento", query = "SELECT c FROM CabeceraFacturav c WHERE c.descuento = :descuento"),
    @NamedQuery(name = "CabeceraFacturav.findByIva", query = "SELECT c FROM CabeceraFacturav c WHERE c.iva = :iva"),
    @NamedQuery(name = "CabeceraFacturav.findByTotal", query = "SELECT c FROM CabeceraFacturav c WHERE c.total = :total")})
public class CabeceraFacturav implements Serializable {
     public static String findByNumeroFactura = "CabeceraFacturav.findByNumeroFactura";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_factura", nullable = false)
    private Integer codigoFactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_factura", nullable = false)
    private int numeroFactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "autorizacion_sri", nullable = false, length = 150)
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
    @OneToMany(mappedBy = "codigoFacturav")
    private List<Kardex> kardexList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cabeceraFacturav")
    private List<DetalleFacturav> detalleFacturavList;
    @JoinColumn(name = "codigo_cliente", referencedColumnName = "codigo_cliente", nullable = false)
    @ManyToOne(optional = false)
    private Cliente codigoCliente;
    @JoinColumn(name = "forma_pago", referencedColumnName = "codigo_formap", nullable = false)
    @ManyToOne(optional = false)
    private FormasPago formaPago;

    public CabeceraFacturav() {
    }

    public CabeceraFacturav(Integer codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public CabeceraFacturav(Integer codigoFactura, int numeroFactura, Date fecha, String autorizacionSri, String establecimiento, String ptoEmision, double subtotal, double subtotalBase0, double subtotalBaseIva, double iva, double total) {
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

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
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
    public List<Kardex> getKardexList() {
        return kardexList;
    }

    public void setKardexList(List<Kardex> kardexList) {
        this.kardexList = kardexList;
    }

    @XmlTransient
    public List<DetalleFacturav> getDetalleFacturavList() {
        return detalleFacturavList;
    }

    public void setDetalleFacturavList(List<DetalleFacturav> detalleFacturavList) {
        this.detalleFacturavList = detalleFacturavList;
    }

    public Cliente getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Cliente codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public FormasPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormasPago formaPago) {
        this.formaPago = formaPago;
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
        if (!(object instanceof CabeceraFacturav)) {
            return false;
        }
        CabeceraFacturav other = (CabeceraFacturav) object;
        if ((this.codigoFactura == null && other.codigoFactura != null) || (this.codigoFactura != null && !this.codigoFactura.equals(other.codigoFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.CabeceraFacturav[ codigoFactura=" + codigoFactura + " ]";
    }
    
}
