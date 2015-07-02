package edu.uc.modulocontable.converter;

import edu.uc.modulocontable.modelo2.DetalleFacturac;
import edu.uc.modulocontable.facade.DetalleFacturacFacade;
import edu.uc.modulocontable.bean.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "detalleFacturacConverter")
public class DetalleFacturacConverter implements Converter {

    @Inject
    private DetalleFacturacFacade ejbFacade;

    private static final String SEPARATOR = "#";
    private static final String SEPARATOR_ESCAPED = "\\#";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.ejbFacade.find(getKey(value));
    }

    edu.uc.modulocontable.modelo2.DetalleFacturacPK getKey(String value) {
        edu.uc.modulocontable.modelo2.DetalleFacturacPK key;
        String values[] = value.split(SEPARATOR_ESCAPED);
        key = new edu.uc.modulocontable.modelo2.DetalleFacturacPK();
        key.setCodigoFactura(Integer.parseInt(values[0]));
        key.setCodigoProducto(Integer.parseInt(values[1]));
        return key;
    }

    String getStringKey(edu.uc.modulocontable.modelo2.DetalleFacturacPK value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value.getCodigoFactura());
        sb.append(SEPARATOR);
        sb.append(value.getCodigoProducto());
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null
                || (object instanceof String && ((String) object).length() == 0)) {
            return null;
        }
        if (object instanceof DetalleFacturac) {
            DetalleFacturac o = (DetalleFacturac) object;
            return getStringKey(o.getDetalleFacturacPK());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DetalleFacturac.class.getName()});
            return null;
        }
    }

}
