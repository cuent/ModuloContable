/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.services.ejb;

import edu.uc.modulocontable.modelo2.Cliente;
import edu.uc.modulocontable.modelo2.FormasPago;
import edu.uc.modulocontable.modelo2.Proveedores;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author cuent
 */
@Entity
@Table(name = "cuenta", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByIdcodcuenta", query = "SELECT c FROM Cuenta c WHERE c.idcodcuenta = :idcodcuenta"),
    @NamedQuery(name = "Cuenta.findByNumcuenta", query = "SELECT c FROM Cuenta c WHERE c.numcuenta = :numcuenta"),
    @NamedQuery(name = "Cuenta.findByDescripcion", query = "SELECT c FROM Cuenta c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Cuenta.findByCategoria", query = "SELECT c FROM Cuenta c WHERE c.categoria = :categoria"),
    @NamedQuery(name = "Cuenta.findBySaldoinicial", query = "SELECT c FROM Cuenta c WHERE c.saldoinicial = :saldoinicial"),
    @NamedQuery(name = "Cuentas.findByNumeroCategoria", query = "SELECT c FROM Cuenta c WHERE c.numcuenta like :numcuenta AND c.categoria = :categoria"),
    @NamedQuery(name = "Cuenta.findBySaldofinal", query = "SELECT c FROM Cuenta c WHERE c.saldofinal = :saldofinal")})
public class Cuenta implements Serializable {
    @OneToMany(mappedBy = "idcuentaxcobrar")
    private List<Cliente> clienteList;
    @OneToMany(mappedBy = "iddocxcobrar")
    private List<Cliente> clienteList1;
    @OneToMany(mappedBy = "idcuentaxpagar")
    private List<Proveedores> proveedoresList;
    @OneToMany(mappedBy = "iddocxpagar")
    private List<Proveedores> proveedoresList1;
    @OneToMany(mappedBy = "idcodcuenta")
    private List<FormasPago> formasPagoList;

    public static String findByNumcuenta = "Cuenta.findByNumcuenta";
    public static String findByNumeroCategoria = "Cuentas.findByNumeroCategoria";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcodcuenta")
    private Integer idcodcuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "numcuenta")
    private String numcuenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "categoria")
    private String categoria;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "saldoinicial")
    private BigDecimal saldoinicial;
    @Column(name = "saldofinal")
    private BigDecimal saldofinal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcodcuenta")
    private List<Transaccion> transaccionList;
    @OneToMany(mappedBy = "idcodcuentapadre")
    private List<Cuenta> cuentaList;
    @JoinColumn(name = "idcodcuentapadre", referencedColumnName = "idcodcuenta")
    @ManyToOne
    private Cuenta idcodcuentapadre;
    @JoinColumn(name = "idtipo", referencedColumnName = "idtipo")
    @ManyToOne(optional = false)
    private Tipo idtipo;

    public Cuenta() {
    }

    public Cuenta(Integer idcodcuenta) {
        this.idcodcuenta = idcodcuenta;
    }

    public Cuenta(Integer idcodcuenta, String numcuenta, String descripcion, String categoria) {
        this.idcodcuenta = idcodcuenta;
        this.numcuenta = numcuenta;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public Integer getIdcodcuenta() {
        return idcodcuenta;
    }

    public void setIdcodcuenta(Integer idcodcuenta) {
        this.idcodcuenta = idcodcuenta;
    }

    public String getNumcuenta() {
        return numcuenta;
    }

    public void setNumcuenta(String numcuenta) {
        this.numcuenta = numcuenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getSaldoinicial() {
        return saldoinicial;
    }

    public void setSaldoinicial(BigDecimal saldoinicial) {
        this.saldoinicial = saldoinicial;
    }

    public BigDecimal getSaldofinal() {
        return saldofinal;
    }

    public void setSaldofinal(BigDecimal saldofinal) {
        this.saldofinal = saldofinal;
    }

    @XmlTransient
    public List<Transaccion> getTransaccionList() {
        return transaccionList;
    }

    public void setTransaccionList(List<Transaccion> transaccionList) {
        this.transaccionList = transaccionList;
    }

    public BigDecimal getTotalDebe() {
        BigDecimal totalDebe = new BigDecimal(BigInteger.ZERO);
        for (int i = 0; i < getTransaccionList().size(); i++) {
            totalDebe = totalDebe.add(getTransaccionList().get(i).getDebe());
        }
        return totalDebe;
    }

    public BigDecimal getTotalHaber() {
        BigDecimal totalHaber = new BigDecimal(BigInteger.ZERO);
        for (int i = 0; i < getTransaccionList().size(); i++) {
            totalHaber = totalHaber.add(getTransaccionList().get(i).getHaber());
        }
        return totalHaber;
    }

    public String getTipo() {
        BigDecimal total = new BigDecimal(BigInteger.ZERO);
        total = getTotalDebe().subtract(getTotalHaber());
        if (total.doubleValue() < 0) {
            System.out.println("Haber");
            return "Acreedor";
        } else {
            System.out.println("Debe");
            return "Deudor";
        }
    }

    public BigDecimal getAcreedor() {
        BigDecimal total = new BigDecimal(BigInteger.ZERO);
        BigDecimal cero = new BigDecimal(BigInteger.ZERO);
        total = getTotalDebe().subtract(getTotalHaber());
        if (total.doubleValue() < 0) {
            System.out.println("Haber");
            return total.negate();
        } else {
            System.out.println("Debe");
            return cero;
        }
    }

    public BigDecimal getDeudor() {
        BigDecimal total = new BigDecimal(BigInteger.ZERO);
        BigDecimal cero = new BigDecimal(BigInteger.ZERO);
        total = getTotalDebe().subtract(getTotalHaber());
        if (total.doubleValue() < 0) {
            System.out.println("Haber");
            return cero;
        } else {
            System.out.println("Debe");
            return total;
        }
    }

    public BigDecimal getDiferencia() {
        BigDecimal total = new BigDecimal(BigInteger.ZERO);
        total = getTotalDebe().subtract(getTotalHaber());
        if (total.doubleValue() < 0) {
            System.out.println("Haber");
            return total.negate();
        } else {
            System.out.println("Debe");
            return total;
        }
    }

    @XmlTransient
    public List<Cuenta> getCuentaList() {
        return cuentaList;
    }

    public void setCuentaList(List<Cuenta> cuentaList) {
        this.cuentaList = cuentaList;
    }

    public Cuenta getIdcodcuentapadre() {
        return idcodcuentapadre;
    }

    public void setIdcodcuentapadre(Cuenta idcodcuentapadre) {
        this.idcodcuentapadre = idcodcuentapadre;
    }

    public Tipo getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(Tipo idtipo) {
        this.idtipo = idtipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcodcuenta != null ? idcodcuenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.idcodcuenta == null && other.idcodcuenta != null) || (this.idcodcuenta != null && !this.idcodcuenta.equals(other.idcodcuenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.services.ejb.Cuenta[ idcodcuenta=" + idcodcuenta + " ]";
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @XmlTransient
    public List<Cliente> getClienteList1() {
        return clienteList1;
    }

    public void setClienteList1(List<Cliente> clienteList1) {
        this.clienteList1 = clienteList1;
    }

    @XmlTransient
    public List<Proveedores> getProveedoresList() {
        return proveedoresList;
    }

    public void setProveedoresList(List<Proveedores> proveedoresList) {
        this.proveedoresList = proveedoresList;
    }

    @XmlTransient
    public List<Proveedores> getProveedoresList1() {
        return proveedoresList1;
    }

    public void setProveedoresList1(List<Proveedores> proveedoresList1) {
        this.proveedoresList1 = proveedoresList1;
    }

    @XmlTransient
    public List<FormasPago> getFormasPagoList() {
        return formasPagoList;
    }

    public void setFormasPagoList(List<FormasPago> formasPagoList) {
        this.formasPagoList = formasPagoList;
    }

}
