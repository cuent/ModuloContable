/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.factura.venta;

import edu.uc.modulocontable.facade.ProductoFacade;
import edu.uc.modulocontable.modelo2.Producto;
import java.io.Serializable;
import java.util.List;
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
@ManagedBean(name = "productoHelper")
@SessionScoped
public class ProductoHelper implements Serializable {

    @EJB
    private ProductoFacade ejbFacade;
    private List<Producto> items;
    private Producto selected;
    FacesMessage msg;

    public void iniciarNuevo() {
        this.setSelected(new Producto());
        this.setItems(ejbFacade.findAll());
    }

    public Producto getSelected() {
        return selected;
    }

    public void setSelected(Producto selected) {
        this.selected = selected;
    }

    public List<Producto> getItems() {
        return items;
    }

    public void setItems(List<Producto> items) {
        this.items = items;
    }

    public void guardarNuevo(ActionEvent event) {
        if (this.getSelected() != null) {
            ejbFacade.create(this.getSelected());
            this.setItems(ejbFacade.findAll());
            iniciarNuevo();
        }
    }

    public void onRowEdit(RowEditEvent event) {
        if ((Producto) event.getObject() != null) {
            ejbFacade.edit((Producto) event.getObject());
            msg = new FacesMessage("Informacion", "El Producto ha sido Modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Error", "El Producto no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Informacion", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void eliminar(Producto producto) {
        if (producto != null) {
            ejbFacade.remove(producto);
            this.setItems(ejbFacade.findAll());

        }
    }
}
