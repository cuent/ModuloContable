package edu.uc.modulocontable.converter;

import edu.uc.modulocontable.modelo2.CabeceraFacturac;
import edu.uc.modulocontable.facade.CabeceraFacturacFacade;
import edu.uc.modulocontable.bean.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "cabeceraFacturacConverter")
public class CabeceraFacturacConverter implements Converter {

    @Inject
    private CabeceraFacturacFacade ejbFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.ejbFacade.find(getKey(value));
    }

    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Integer value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof CabeceraFacturac) {
            CabeceraFacturac o = (CabeceraFacturac) object;
            return getStringKey(o.getCodigoFactura());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), CabeceraFacturac.class.getName()});
            return null;
        }
    }

}
