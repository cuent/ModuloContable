/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.factura.venta;

import edu.uc.modulocontable.facade.FormasPagoFacade;
import edu.uc.modulocontable.modelo2.FormasPago;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "formasPagoHelper")
@SessionScoped
public class FormasPagoHelper implements Serializable {

    @EJB
    private FormasPagoFacade ejbFacade;
    private List<FormasPago> items;
    private FormasPago selected;

    public List<FormasPago> getItems() {
        return items = ejbFacade.findAll();
    }

    public void setItems(List<FormasPago> items) {
        this.items = items;
    }

    public FormasPago getSelected() {
        return selected;
    }

    public void setSelected(FormasPago selected) {
        this.selected = selected;
    }

}
