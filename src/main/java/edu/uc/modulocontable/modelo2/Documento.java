/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.modelo2;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "documento", catalog = "contables", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"),
    @NamedQuery(name = "Documento.findByCodigoDocumento", query = "SELECT d FROM Documento d WHERE d.codigoDocumento = :codigoDocumento"),
    @NamedQuery(name = "Documento.findByDocuemnto", query = "SELECT d FROM Documento d WHERE d.docuemnto = :docuemnto")})
public class Documento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo_documento", nullable = false)
    private Integer codigoDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "docuemnto", nullable = false, length = 45)
    private String docuemnto;

    public Documento() {
    }

    public Documento(Integer codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public Documento(Integer codigoDocumento, String docuemnto) {
        this.codigoDocumento = codigoDocumento;
        this.docuemnto = docuemnto;
    }

    public Integer getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(Integer codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getDocuemnto() {
        return docuemnto;
    }

    public void setDocuemnto(String docuemnto) {
        this.docuemnto = docuemnto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoDocumento != null ? codigoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documento)) {
            return false;
        }
        Documento other = (Documento) object;
        if ((this.codigoDocumento == null && other.codigoDocumento != null) || (this.codigoDocumento != null && !this.codigoDocumento.equals(other.codigoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.Documento[ codigoDocumento=" + codigoDocumento + " ]";
    }
    
}
