/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.administracion;

import edu.uc.modulocontable.facade.DocumentoFacade;
import edu.uc.modulocontable.modelo2.Documento;
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
@ManagedBean(name = "documentHelper")
@SessionScoped
public class DocumentosHelper implements Serializable {

    @EJB
    private DocumentoFacade ejbFacade;
    private List<Documento> items;
    private Documento selected;
    FacesMessage msg;

    public void iniciarNuevo() {
        this.setSelected(new Documento());
        this.setItems(ejbFacade.findAll());
    }

    @PostConstruct
    public void init() {
        items = ejbFacade.findAll();
    }

    public Documento getSelected() {
        return selected;
    }

    public void setSelected(Documento selected) {
        this.selected = selected;
    }

    public List<Documento> getItems() {
        return items;
    }

    public void setItems(List<Documento> items) {
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
        if ((Documento) event.getObject() != null) {
            ejbFacade.edit((Documento) event.getObject());
            msg = new FacesMessage("Informacion", "El Documento ha sido Modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Error", "El Documento no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Informacion", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void eliminar(Documento documento) {
        if (documento != null) {
            ejbFacade.remove(documento);
            this.setItems(ejbFacade.findAll());

        }
    }
}
