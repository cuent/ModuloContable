/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.modelo;

import edu.uc.modulocontable.domain.entity.AsientoFacade;
import edu.uc.modulocontable.domain.entity.CuentaFacade;
import edu.uc.modulocontable.services.ejb.Asiento;
import edu.uc.modulocontable.services.ejb.Cuenta;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "beanTransaccion")
@RequestScoped
public class BeanTransacciones {

    private String referencia;
    private BigDecimal totalDebe = new BigDecimal(0);
    private BigDecimal totalHaber = new BigDecimal(0);
    
    @EJB
    private AsientoFacade asientoFacade;
    private List<Asiento> asientos;
    @EJB
    private CuentaFacade cuentaFacade;
    private List<Cuenta> cuentas;

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<Asiento> getAsientos() {
        asientos = asientoFacade.findAll();

        //asientoFacade.test();
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }
    
    public List<Cuenta> getCuentas() {
        cuentas = cuentaFacade.findAll();

        //asientoFacade.test();
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
