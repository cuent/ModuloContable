/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.administracion;

import edu.uc.modulocontable.facade.AutorizacionesFacade;
import edu.uc.modulocontable.modelo2.Autorizaciones;
import java.io.Serializable;
import java.util.Date;
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
@ManagedBean(name = "autorizacionesHelper")
@SessionScoped
public class AutorizacionesHelper implements Serializable {

    @EJB
    private AutorizacionesFacade ejbFacade;
    private List<Autorizaciones> items;
    private Autorizaciones selected;
    private Date fechaCaducidad;
    FacesMessage msg;

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public void iniciarNuevo() {
        this.setSelected(new Autorizaciones());
        this.setItems(ejbFacade.findAll());
    }

    public Autorizaciones getSelected() {
        return selected;
    }

    public void setSelected(Autorizaciones selected) {
        this.selected = selected;
    }

    public List<Autorizaciones> getItems() {
        return items;
    }

    public void setItems(List<Autorizaciones> items) {
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
        if ((Autorizaciones) event.getObject() != null) {
            ejbFacade.edit((Autorizaciones) event.getObject());
            msg = new FacesMessage("Informacion", "La Autorizacion ha sido Modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Error", "La Autorizacion no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Informacion", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void eliminar(Autorizaciones autorizacion) {
        if (autorizacion != null) {
            ejbFacade.remove(autorizacion);
            this.setItems(ejbFacade.findAll());

        }
    }
}
