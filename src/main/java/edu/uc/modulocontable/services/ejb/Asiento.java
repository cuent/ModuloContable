/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.services.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author cuent
 */
@Entity
@Table(name = "asiento", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asiento.findAll", query = "SELECT a FROM Asiento a"),
    @NamedQuery(name = "Asiento.findByIdcodasiento", query = "SELECT a FROM Asiento a WHERE a.idcodasiento = :idcodasiento"),
    @NamedQuery(name = "Asiento.findByNumdiario", query = "SELECT a FROM Asiento a WHERE a.numdiario = :numdiario"),
    @NamedQuery(name = "Asiento.findByPeriodo", query = "SELECT a FROM Asiento a WHERE a.periodo = :periodo"),
    @NamedQuery(name = "Asiento.findByFecha", query = "SELECT a FROM Asiento a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Asiento.findByNumasiento", query = "SELECT a FROM Asiento a WHERE a.numasiento = :numasiento"),
    @NamedQuery(name = "Asiento.findByDebe", query = "SELECT a FROM Asiento a WHERE a.debe = :debe"),
    @NamedQuery(name = "Asiento.findByHaber", query = "SELECT a FROM Asiento a WHERE a.haber = :haber"),
    @NamedQuery(name = "Asiento.findByConcepto", query = "SELECT a FROM Asiento a WHERE a.concepto = :concepto"),
    @NamedQuery(name = "Asiento.findByDocumento", query = "SELECT a FROM Asiento a WHERE a.documento = :documento")})
public class Asiento implements Serializable {
    private static final long serialVersionUID = 1L;
    public static String findByNumCuenta = "Cuentas.findByNumCuenta";
    public static String findByNombre = "Cuentas.findByNombre";
    public static String findByNumCuentaLike = "Cuentas.findByNumCuentaLike";
    public static String findByNumeroCategoria = "Cuentas.findByNumeroCategoria";
    public static String findByNombreCategoria = "Cuentas.findByNombreCategoria";
    public static String findByNumasiento = "Asiento.findByNumasiento";
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcodasiento")
    private Integer idcodasiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numdiario")
    private int numdiario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "periodo")
    private int periodo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numasiento")
    private int numasiento;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "debe")
    private BigDecimal debe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "haber")
    private BigDecimal haber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "concepto")
    private String concepto;
    @Column(name = "documento")
    private Integer documento;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "idcodasiento")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "idcodasiento",cascade = CascadeType.ALL)
    private List<Transaccion> transaccionList;

    public Asiento() {
    }

    public Asiento(Integer idcodasiento) {
        this.idcodasiento = idcodasiento;
    }

    public Asiento(Integer idcodasiento, int numdiario, int periodo, Date fecha, int numasiento, BigDecimal debe, BigDecimal haber, String concepto) {
        this.idcodasiento = idcodasiento;
        this.numdiario = numdiario;
        this.periodo = periodo;
        this.fecha = fecha;
        this.numasiento = numasiento;
        this.debe = debe;
        this.haber = haber;
        this.concepto = concepto;
    }

    public Integer getIdcodasiento() {
        return idcodasiento;
    }

    public void setIdcodasiento(Integer idcodasiento) {
        this.idcodasiento = idcodasiento;
    }

    public int getNumdiario() {
        return numdiario;
    }

    public void setNumdiario(int numdiario) {
        this.numdiario = numdiario;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNumasiento() {
        return numasiento;
    }

    public void setNumasiento(int numasiento) {
        this.numasiento = numasiento;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    @XmlTransient
    public List<Transaccion> getTransaccionList() {
        return transaccionList;
    }

    public void setTransaccionList(List<Transaccion> transaccionList) {
        this.transaccionList = transaccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcodasiento != null ? idcodasiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asiento)) {
            return false;
        }
        Asiento other = (Asiento) object;
        if ((this.idcodasiento == null && other.idcodasiento != null) || (this.idcodasiento != null && !this.idcodasiento.equals(other.idcodasiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.services.ejb.Asiento[ idcodasiento=" + idcodasiento + " ]";
    }
    
}
