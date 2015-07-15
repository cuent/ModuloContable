/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.factura.venta;

import edu.uc.modulocontable.facade.ImpuestoFacade;
import edu.uc.modulocontable.modelo2.Impuesto;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "impuestoHelper")
@SessionScoped
public class ImpuestoHelper implements Serializable{

    @EJB
    private ImpuestoFacade ejbFacade;
    private List<Impuesto> items;

    public List<Impuesto> getItems() {
        return items = ejbFacade.findAll();
    }

    public void setItems(List<Impuesto> items) {
        this.items = items;
    }

}
