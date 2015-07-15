package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.FormasPagoFacade;
import edu.uc.modulocontable.modelo2.FormasPago;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "formasPagoController")
@SessionScoped
public class FormasPagoController extends AbstractController<FormasPago> implements Serializable {

    @EJB
    private FormasPagoFacade ejbFacade;
 

    /**
     * Initialize the concrete FormasPago controller bean. The
     * AbstractController requires the EJB Facade object for most operations.
     * <p>
     * In addition, this controller also requires references to controllers for
     * parent entities in order to display their information from a context
     * menu.
     */
    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

    public FormasPagoController() {
        // Inform the Abstract parent controller of the concrete FormasPago?cap_first Entity
        super(FormasPago.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

    /**
     * Sets the "items" attribute with a collection of CabeceraFacturac entities
     * that are retrieved from FormasPago?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for CabeceraFacturac page
     */
    public String navigateCabeceraFacturacList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("CabeceraFacturac_items", this.getSelected().getCabeceraFacturacList());
        }
        return "/generado/nuevo/cabeceraFacturac/index";
    }

    /**
     * Sets the "items" attribute with a collection of CabeceraFacturav entities
     * that are retrieved from FormasPago?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for CabeceraFacturav page
     */
    public String navigateCabeceraFacturavList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("CabeceraFacturav_items", this.getSelected().getCabeceraFacturavList());
        }
        return "/generado/nuevo/cabeceraFacturav/index";
    }

}
