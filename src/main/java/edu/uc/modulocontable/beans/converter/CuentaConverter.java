/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.converter;

import edu.uc.modulocontable.domain.entity.CuentaFacade;
import edu.uc.modulocontable.services.ejb.Cuenta;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author cuent
 */
@ManagedBean(name="cuentaConverter")
public class CuentaConverter implements Converter{

    @EJB
    private CuentaFacade cuentaFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return cuentaFacade.find(Integer.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Cuenta)value).getIdcodcuenta().toString();
    }
}