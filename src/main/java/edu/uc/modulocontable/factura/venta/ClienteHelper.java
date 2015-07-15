/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.factura.venta;

import edu.uc.modulocontable.facade.ClienteFacade;
import edu.uc.modulocontable.modelo2.Cliente;
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
@ManagedBean(name = "clienteHelper")
@SessionScoped
public class ClienteHelper implements Serializable {

    @EJB
    private ClienteFacade ejbFacade;
    FacesMessage msg;
    private String tipoIdentificacion;
    private List<Cliente> items;
    private Cliente selected;

    public void iniciarCliente() {
        this.setSelected(new Cliente());
        this.setItems(ejbFacade.findAll());
    }

    public List<Cliente> getItems() {
        return items;
    }

    public void setItems(List<Cliente> items) {
        this.items = items;
    }

    public Cliente getSelected() {
        return selected;
    }

    public void setSelected(Cliente selected) {
        this.selected = selected;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void validarCedulaORuc() {
//        System.out.println("hola" +getTipoIdentificacion());
//        if (getTipoIdentificacion().equalsIgnoreCase("0")) {
//
//            if (this.getSelected().getIdentificacion() != null && this.getSelected().getIdentificacion().length() > 0) {
//                if (this.getSelected().getTipoIdentificacion().equalsIgnoreCase("Cedula")) {
//                    if (validarCedula(this.getSelected().getIdentificacion())) {
//                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La Cedula es Correcta");
//                        FacesContext.getCurrentInstance().addMessage(null, msg);
//                    } else {
//                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "La Cedula es Incorrecta");
//                        FacesContext.getCurrentInstance().addMessage(null, msg);
//                    }
//
//                } else if (getTipoIdentificacion().equalsIgnoreCase("RUC")) {
//
//                }
//            }
//        }
    }

    public void guardarNuevo(ActionEvent evet) {
        if (this.getSelected() != null) {
            this.getSelected().setTipoIdentificacion(getTipoIdentificacion());

            ejbFacade.create(this.getSelected());
            setItems(ejbFacade.findAll());
            iniciarCliente();

        } else {
            System.out.println("no guardo cliente");
        }
    }

    public void onRowEdit(RowEditEvent event) {
        if ((Cliente) event.getObject() != null) {
            ejbFacade.edit((Cliente) event.getObject());
            msg = new FacesMessage("Cliente Editado", "El cliente ha sido Modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Cliente", "El cliente no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Cliente", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
       public void eliminarcliente(Cliente c) {
        
        if (c != null) {
            ejbFacade.remove(c);
            this.setItems(ejbFacade.findAll());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cliente Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
}
