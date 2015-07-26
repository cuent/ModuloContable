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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;

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
    FacesMessage msg;

    @PostConstruct
    public void init() {
        items = ejbFacade.findAll();
    }

    public List<FormasPago> getItems() {
        return items;
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

    public void iniciarNuevo() {
        this.setSelected(new FormasPago());
        this.setItems(ejbFacade.findAll());
    }

    public void guardarNuevo(ActionEvent event) {
        if (this.getSelected() != null) {
            ejbFacade.create(this.getSelected());
            this.setItems(ejbFacade.findAll());
            iniciarNuevo();
        }
    }

    public void onRowEdit(RowEditEvent event) {
        if ((FormasPago) event.getObject() != null) {
            ejbFacade.edit((FormasPago) event.getObject());
            msg = new FacesMessage("Informacion", "La Forma de Pago ha sido Modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Error", "La Forma de Pago no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Informacion", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void eliminar(FormasPago impuesto) {
        if (impuesto != null) {
            ejbFacade.remove(impuesto);
            this.setItems(ejbFacade.findAll());

        }
    }
}
