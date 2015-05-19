/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.modelo;

import edu.uc.modulocontable.domain.entity.AsientoFacade;
import edu.uc.modulocontable.services.ejb.Asiento;
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

    @EJB
    private AsientoFacade asientoFacade;
    private List<Asiento> asientos;

    public List<Asiento> getAsientos() {
        asientos = asientoFacade.findAll();

        //asientoFacade.test();
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

}
