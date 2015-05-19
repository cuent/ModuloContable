/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.backing;

import edu.uc.modulocontable.beans.backing.util.JsfUtil;
import edu.uc.modulocontable.beans.modelo.BeanTipo;
import edu.uc.modulocontable.services.ejb.Tipo;
import java.util.List;
import javax.ejb.EJB;
import edu.uc.modulocontable.domain.entity.TipoFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "tipoHelper")
@RequestScoped
public class TipoHelper implements Serializable {

    @ManagedProperty("#{beanTipo}")
    private BeanTipo beanTipo;
    @EJB
    private TipoFacade tipofacade;
    List<Tipo> tipos;

    public List<Tipo> getTipos() {
        return tipos;
    }

    @PostConstruct
    public void init() {
        tipos = tipofacade.findAll();
    }

    public void onRowEdit(RowEditEvent event) {
        Tipo tipo = (Tipo) (event.getObject());
        tipofacade.edit(tipo);
        FacesMessage msg = new FacesMessage("Edicion Exitosa", tipo.getNombretipo());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        tipos = tipofacade.findAll();
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edicion Cancelada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowDelete(Tipo tipo) {
        try {
            tipofacade.remove(tipo);
            tipos = tipofacade.findAll();
            addMessage("Eliminado Satisfactoriamente");
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "El nombre tiene dependencias", null);
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, "");
        FacesContext.getCurrentInstance().addMessage(null, message);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.renderResponse();
    }

    public void crearTipo() {
        String nombreTipo = getBeanTipo().getNombre();
        Tipo tipo = new Tipo();
        tipo.setNombretipo(nombreTipo);
        tipofacade.create(tipo);
        tipos = tipofacade.findAll();

        String mensaje = ResourceBundle.getBundle("/mensajes").getString("tipoCreacionExitosa");
        JsfUtil.addSuccessMessage(mensaje);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.renderResponse();

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(TipoHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BeanTipo getBeanTipo() {
        return beanTipo;
    }

    public void setBeanTipo(BeanTipo beanTipo) {
        this.beanTipo = beanTipo;
    }

}
