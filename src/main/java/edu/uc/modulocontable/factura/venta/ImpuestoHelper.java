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
@ManagedBean(name = "impuestoHelper")
@SessionScoped
public class ImpuestoHelper implements Serializable {

    @EJB
    private ImpuestoFacade ejbFacade;
    private List<Impuesto> items;
    private Impuesto selected;
    FacesMessage msg;

    @PostConstruct
    public void init() {
        items = ejbFacade.findAll();
    }

    public List<Impuesto> getItems() {
        return items;
    }

    public void setItems(List<Impuesto> items) {
        this.items = items;
    }

    public void iniciarNuevo() {
        this.setSelected(new Impuesto());
        this.setItems(ejbFacade.findAll());
    }

    public Impuesto getSelected() {
        return selected;
    }

    public void setSelected(Impuesto selected) {
        this.selected = selected;
    }

    public void guardarNuevo(ActionEvent event) {
        if (this.getSelected() != null) {
            ejbFacade.create(this.getSelected());
            this.setItems(ejbFacade.findAll());
            iniciarNuevo();
        }
    }

    public void onRowEdit(RowEditEvent event) {
        if ((Impuesto) event.getObject() != null) {
            ejbFacade.edit((Impuesto) event.getObject());
            msg = new FacesMessage("Informacion", "El Impuesto ha sido Modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Error", "El Impuesto no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Informacion", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void eliminar(Impuesto impuesto) {
        if (impuesto != null) {
            ejbFacade.remove(impuesto);
            this.setItems(ejbFacade.findAll());

        }
    }
}
