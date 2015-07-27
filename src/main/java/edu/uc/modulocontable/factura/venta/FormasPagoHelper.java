/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.factura.venta;

import edu.uc.modulocontable.domain.entity.CuentaFacade;
import edu.uc.modulocontable.facade.FormasPagoFacade;
import edu.uc.modulocontable.modelo2.FormasPago;
import edu.uc.modulocontable.services.ejb.Cuenta;
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
    @EJB
    private CuentaFacade ejbCuenta;
    private List<FormasPago> items;
    private List<Cuenta> cuentas;
    private FormasPago selected;
    FacesMessage msg;

    @PostConstruct
    public void init() {
        items = ejbFacade.findAll();
        cuentas = ejbCuenta.getCuentasActivosPasivosDetalle();
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
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
        this.setCuentas(ejbCuenta.getCuentasActivosPasivosDetalle());
    }

    public void guardarNuevo(ActionEvent event) {
        if (this.getSelected() != null) {
            ejbFacade.create(this.getSelected());
            this.setItems(ejbFacade.findAll());
            this.setCuentas(ejbCuenta.getCuentasActivosPasivosDetalle());
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
