/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.backing;

import edu.uc.modulocontable.beans.modelo.BeanTransacciones;
import edu.uc.modulocontable.domain.entity.AsientoFacade;
import edu.uc.modulocontable.domain.entity.CuentaFacade;
import edu.uc.modulocontable.domain.entity.TransaccionFacade;
import edu.uc.modulocontable.services.ejb.Asiento;
import edu.uc.modulocontable.services.ejb.Cuenta;
import edu.uc.modulocontable.services.ejb.Transaccion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "transaccionHelper")
@RequestScoped
public class TransaccionHelper {

    @ManagedProperty("#{beanTransaccion}")
    private BeanTransacciones beanTransaccion;
    private List<Cuenta> cuentas;
    private List<Asiento> asientos;
    private List<Transaccion> transacciones;

    @EJB
    private AsientoFacade asientoFacade;
    @EJB
    private CuentaFacade cuentaFacade;
    @EJB
    private TransaccionFacade transaccionFacade;

    public List<Transaccion> getTransacciones() {
        return transacciones = transaccionFacade.findAll();
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
    
    public TransaccionFacade getTransaccionFacade() {
        return transaccionFacade;
    }

    public void setTransaccionFacade(TransaccionFacade transaccionFacade) {
        this.transaccionFacade = transaccionFacade;
    }

    public List<Asiento> getAsientos() {
        return asientos = asientoFacade.findAll();
    }

    public List<Cuenta> getCuentas() {
        cuentas = cuentaFacade.findAll();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getCategoria().equalsIgnoreCase("DETALLE")) {
                cuentasAux.add(cuenta);
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public BeanTransacciones getBeanTransaccion() {
        return beanTransaccion;
    }

    public void setBeanTransaccion(BeanTransacciones beanTransaccion) {
        this.beanTransaccion = beanTransaccion;
    }

}
