/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.converter;

import edu.uc.modulocontable.domain.entity.TipoFacade;
import edu.uc.modulocontable.services.ejb.Tipo;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author cuent
 */

@ManagedBean(name="tipoConverter")
public class TipoConverter implements Converter{

    @EJB
    private TipoFacade tipoFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return tipoFacade.find(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Tipo)value).getIdtipo().toString();
    }
}
