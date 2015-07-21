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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "autorizaciones", catalog = "contables", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"numero_autorizacion"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Autorizaciones.findAll", query = "SELECT a FROM Autorizaciones a"),
    @NamedQuery(name = "Autorizaciones.findByIdAutorizacion", query = "SELECT a FROM Autorizaciones a WHERE a.idAutorizacion = :idAutorizacion"),
    @NamedQuery(name = "Autorizaciones.findByNumeroAutorizacion", query = "SELECT a FROM Autorizaciones a WHERE a.numeroAutorizacion = :numeroAutorizacion"),
    @NamedQuery(name = "Autorizaciones.findByEstablecimiento", query = "SELECT a FROM Autorizaciones a WHERE a.establecimiento = :establecimiento"),
    @NamedQuery(name = "Autorizaciones.findByPtoEmision", query = "SELECT a FROM Autorizaciones a WHERE a.ptoEmision = :ptoEmision"),
    @NamedQuery(name = "Autorizaciones.findByNumeroInicialDocumento", query = "SELECT a FROM Autorizaciones a WHERE a.numeroInicialDocumento = :numeroInicialDocumento"),
    @NamedQuery(name = "Autorizaciones.findByNumeroFinalDocumento", query = "SELECT a FROM Autorizaciones a WHERE a.numeroFinalDocumento = :numeroFinalDocumento"),
    @NamedQuery(name = "Autorizaciones.findByNumeroActual", query = "SELECT a FROM Autorizaciones a WHERE a.numeroActual = :numeroActual"),
    @NamedQuery(name = "Autorizaciones.findByFechaCaducidad", query = "SELECT a FROM Autorizaciones a WHERE a.fechaCaducidad = :fechaCaducidad")})
public class Autorizaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_autorizacion", nullable = false)
    private Integer idAutorizacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "numero_autorizacion", nullable = false, length = 120)
    private String numeroAutorizacion;
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
    @Size(min = 1, max = 45)
    @Column(name = "numero_inicial_documento", nullable = false, length = 45)
    private String numeroInicialDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numero_final_documento", nullable = false, length = 45)
    private String numeroFinalDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "numero_actual", nullable = false, length = 45)
    private String numeroActual;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_caducidad", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCaducidad;
    @JoinColumn(name = "tipo_documento", referencedColumnName = "codigo_documento", nullable = false)
    @ManyToOne(optional = false)
    private Documento tipoDocumento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autorizacion")
    private List<Proveedores> proveedoresList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autorizacionSri")
    private List<CabeceraFacturac> cabeceraFacturacList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "autorizacionSri")
    private List<CabeceraFacturav> cabeceraFacturavList;

    public Autorizaciones() {
    }

    public Autorizaciones(Integer idAutorizacion) {
        this.idAutorizacion = idAutorizacion;
    }

    public Autorizaciones(Integer idAutorizacion, String numeroAutorizacion, String establecimiento, String ptoEmision, String numeroInicialDocumento, String numeroFinalDocumento, String numeroActual, Date fechaCaducidad) {
        this.idAutorizacion = idAutorizacion;
        this.numeroAutorizacion = numeroAutorizacion;
        this.establecimiento = establecimiento;
        this.ptoEmision = ptoEmision;
        this.numeroInicialDocumento = numeroInicialDocumento;
        this.numeroFinalDocumento = numeroFinalDocumento;
        this.numeroActual = numeroActual;
        this.fechaCaducidad = fechaCaducidad;
    }

    public Integer getIdAutorizacion() {
        return idAutorizacion;
    }

    public void setIdAutorizacion(Integer idAutorizacion) {
        this.idAutorizacion = idAutorizacion;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
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

    public String getNumeroInicialDocumento() {
        return numeroInicialDocumento;
    }

    public void setNumeroInicialDocumento(String numeroInicialDocumento) {
        this.numeroInicialDocumento = numeroInicialDocumento;
    }

    public String getNumeroFinalDocumento() {
        return numeroFinalDocumento;
    }

    public void setNumeroFinalDocumento(String numeroFinalDocumento) {
        this.numeroFinalDocumento = numeroFinalDocumento;
    }

    public String getNumeroActual() {
        return numeroActual;
    }

    public void setNumeroActual(String numeroActual) {
        this.numeroActual = numeroActual;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Documento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Documento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @XmlTransient
    public List<Proveedores> getProveedoresList() {
        return proveedoresList;
    }

    public void setProveedoresList(List<Proveedores> proveedoresList) {
        this.proveedoresList = proveedoresList;
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
        hash += (idAutorizacion != null ? idAutorizacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autorizaciones)) {
            return false;
        }
        Autorizaciones other = (Autorizaciones) object;
        if ((this.idAutorizacion == null && other.idAutorizacion != null) || (this.idAutorizacion != null && !this.idAutorizacion.equals(other.idAutorizacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nuevo.paquete.modeloNuevo.Autorizaciones[ idAutorizacion=" + idAutorizacion + " ]";
    }
    
}
