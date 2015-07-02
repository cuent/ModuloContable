/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.modelo2;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Juan Pablo
 */
@Embeddable
public class DetalleFacturavPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_factura", nullable = false)
    private int codigoFactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo_producto", nullable = false)
    private int codigoProducto;

    public DetalleFacturavPK() {
    }

    public DetalleFacturavPK(int codigoFactura, int codigoProducto) {
        this.codigoFactura = codigoFactura;
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoFactura() {
        return codigoFactura;
    }

    public void setCodigoFactura(int codigoFactura) {
        this.codigoFactura = codigoFactura;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codigoFactura;
        hash += (int) codigoProducto;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleFacturavPK)) {
            return false;
        }
        DetalleFacturavPK other = (DetalleFacturavPK) object;
        if (this.codigoFactura != other.codigoFactura) {
            return false;
        }
        if (this.codigoProducto != other.codigoProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uc.modulocontable.modelo2.DetalleFacturavPK[ codigoFactura=" + codigoFactura + ", codigoProducto=" + codigoProducto + " ]";
    }
    
}
