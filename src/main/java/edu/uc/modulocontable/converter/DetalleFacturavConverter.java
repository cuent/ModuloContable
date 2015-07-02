package edu.uc.modulocontable.converter;

import edu.uc.modulocontable.modelo2.DetalleFacturav;
import edu.uc.modulocontable.facade.DetalleFacturavFacade;
import edu.uc.modulocontable.bean.util.JsfUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@FacesConverter(value = "detalleFacturavConverter")
public class DetalleFacturavConverter implements Converter {

    @Inject
    private DetalleFacturavFacade ejbFacade;

    private static final String SEPARATOR = "#";
    private static final String SEPARATOR_ESCAPED = "\\#";

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0 || JsfUtil.isDummySelectItem(component, value)) {
            return null;
        }
        return this.ejbFacade.find(getKey(value));
    }

    edu.uc.modulocontable.modelo2.DetalleFacturavPK getKey(String value) {
        edu.uc.modulocontable.modelo2.DetalleFacturavPK key;
        String values[] = value.split(SEPARATOR_ESCAPED);
        key = new edu.uc.modulocontable.modelo2.DetalleFacturavPK();
        key.setCodigoFactura(Integer.parseInt(values[0]));
        key.setCodigoProducto(Integer.parseInt(values[1]));
        return key;
    }

    String getStringKey(edu.uc.modulocontable.modelo2.DetalleFacturavPK value) {
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
        if (object instanceof DetalleFacturav) {
            DetalleFacturav o = (DetalleFacturav) object;
            return getStringKey(o.getDetalleFacturavPK());
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DetalleFacturav.class.getName()});
            return null;
        }
    }

}
