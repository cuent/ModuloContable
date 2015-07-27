/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.modelo2;

import edu.uc.modulocontable.services.ejb.Cuenta;
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
@Table(name = "cliente", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCodigoCliente", query = "SELECT c FROM Cliente c WHERE c.codigoCliente = :codigoCliente"),
    @NamedQuery(name = "Cliente.findByTipoIdentificacion", query = "SELECT c FROM Cliente c WHERE c.tipoIdentificacion = :tipoIdentificacion"),
    @NamedQuery(name = "Cliente.findByIdentificacion", query = "SELECT c FROM Cliente c WHERE c.identificacion = :identificacion"),
    @NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cliente.findByApellido", query = "SELECT c FROM Cliente c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion")})
public class Cliente implements Serializable {
    @JoinColumn(name = "idcuentaxcobrar", referencedColumnName = "idcodcuenta")
    @ManyToOne
    private Cuenta idcuentaxcobrar;
    @JoinColumn(name = "iddocxcobrar", referencedColumnName = "idcodcuenta")
    @ManyToOne
    private Cuenta iddocxcobrar;
    public static String findByIdentificacion = "Cliente.findByIdentificacion";
     public static String findByTipoIdentificacion = "Cliente.findByTipoIdentificacion";
   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_cliente", nullable = false)
    private Integer codigoCliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo_identificacion", nullable = false, length = 45)
    private String tipoIdentificacion;
    @Size(max = 45)
    @Column(name = "identificacion", length = 45)
    private String identificacion;
    @Size(max = 45)
    @Column(name = "nombre", length = 45)
    private String nombre;
    @Size(max = 45)
    @Column(name = "apellido", length = 45)
    private String apellido;
    @Size(max = 45)
    @Column(name = "telefono", length = 45)
    private String telefono;
    @Size(max = 100)
    @Column(name = "direccion", length = 100)
    private String direccion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoCliente")
    private List<CabeceraFacturav> cabeceraFacturavList;

    public Cliente() {
    }

    public Cliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Cliente(Integer codigoCliente, String tipoIdentificacion) {
        this.codigoCliente = codigoCliente;
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public Integer getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(Integer codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
        hash += (codigoCliente != null ? codigoCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.codigoCliente == null && other.codigoCliente != null) || (this.codigoCliente != null && !this.codigoCliente.equals(other.codigoCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.Cliente[ codigoCliente=" + codigoCliente + " ]";
    }

    public Cuenta getIdcuentaxcobrar() {
        return idcuentaxcobrar;
    }

    public void setIdcuentaxcobrar(Cuenta idcuentaxcobrar) {
        this.idcuentaxcobrar = idcuentaxcobrar;
    }

    public Cuenta getIddocxcobrar() {
        return iddocxcobrar;
    }

    public void setIddocxcobrar(Cuenta iddocxcobrar) {
        this.iddocxcobrar = iddocxcobrar;
    }
    
}
