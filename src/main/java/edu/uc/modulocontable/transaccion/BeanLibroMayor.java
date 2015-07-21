/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uc.modulocontable.transaccion;

import edu.uc.modulocontable.domain.entity.AsientoFacade;
import edu.uc.modulocontable.domain.entity.CuentaFacade;
import edu.uc.modulocontable.services.ejb.Asiento;
import edu.uc.modulocontable.services.ejb.Cuenta;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


@ManagedBean(name = "beanLibroMayor")
@RequestScoped
public class BeanLibroMayor {

    private String referencia;
    private BigDecimal totalDebe = new BigDecimal(0);
    private BigDecimal totalHaber = new BigDecimal(0);
    
    @EJB
    private CuentaFacade cuentaFacade;
    private List<Cuenta> cuentas;

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<Cuenta> getCuentas() {
        cuentas = cuentaFacade.findAll();
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public BigDecimal getTotalDebe() {
        return totalDebe;
    }

    public void setTotalDebe(BigDecimal totalDebe) {
        this.totalDebe = totalDebe;
    }

    public BigDecimal getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(BigDecimal totalHaber) {
        this.totalHaber = totalHaber;
    }
}