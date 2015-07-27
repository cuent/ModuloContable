/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.services.ejb;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cuent
 */
@Entity
@Table(name = "transaccion", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
    @NamedQuery(name = "Transaccion.findByIdtransaccion", query = "SELECT t FROM Transaccion t WHERE t.idtransaccion = :idtransaccion"),
    @NamedQuery(name = "Transaccion.findByDebe", query = "SELECT t FROM Transaccion t WHERE t.debe = :debe"),
    @NamedQuery(name = "Transaccion.findByHaber", query = "SELECT t FROM Transaccion t WHERE t.haber = :haber"),
    @NamedQuery(name = "Transaccion.findByReferencia", query = "SELECT t FROM Transaccion t WHERE t.referencia = :referencia"),
    @NamedQuery(name = "Transaccion.groupByCuentas", query = "SELECT t FROM Transaccion t group by t.idcodcuenta"),
    @NamedQuery(name = "Transaccion.findByCuenta", query = "SELECT t FROM Transaccion t where t.idcodcuenta = :idcodcuenta")})
public class Transaccion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String groupByCuentas = "Transaccion.groupByCuentas";
    public static final String findByCuenta = "Transaccion.findByCuenta";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransaccion")
    private Integer idtransaccion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "debe")
    private BigDecimal debe;
    @Column(name = "haber")
    private BigDecimal haber;
    @Size(max = 120)
    @Column(name = "referencia")
    private String referencia;
    @JoinColumn(name = "idcodasiento", referencedColumnName = "idcodasiento")
    @ManyToOne(optional = false)
    private Asiento idcodasiento;
    @JoinColumn(name = "idcodcuenta", referencedColumnName = "idcodcuenta")
    @ManyToOne(optional = false)
    private Cuenta idcodcuenta;

    public Transaccion() {
    }

    public Transaccion(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
    }

    public Integer getIdtransaccion() {
        return idtransaccion;
    }

    public void setIdtransaccion(Integer idtransaccion) {
        this.idtransaccion = idtransaccion;
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

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Asiento getIdcodasiento() {
        return idcodasiento;
    }

    public void setIdcodasiento(Asiento idcodasiento) {
        this.idcodasiento = idcodasiento;
    }

    public Cuenta getIdcodcuenta() {
        return idcodcuenta;
    }

    public void setIdcodcuenta(Cuenta idcodcuenta) {
        this.idcodcuenta = idcodcuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaccion != null ? idtransaccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaccion)) {
            return false;
        }
        Transaccion other = (Transaccion) object;
        if ((this.idtransaccion == null && other.idtransaccion != null) || (this.idtransaccion != null && !this.idtransaccion.equals(other.idtransaccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.services.ejb.Transaccion[ idtransaccion=" + idtransaccion + " ]";
    }

}
