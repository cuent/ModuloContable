package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.ProveedoresFacade;
import edu.uc.modulocontable.modelo2.Proveedores;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "proveedoresController")
@SessionScoped
public class ProveedoresController extends AbstractController<Proveedores> {

    @EJB
    private ProveedoresFacade ejbFacade;
   
    /**
     * Initialize the concrete Proveedores controller bean. The
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

    public ProveedoresController() {
        // Inform the Abstract parent controller of the concrete Proveedores?cap_first Entity
        super(Proveedores.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

    /**
     * Sets the "items" attribute with a collection of CabeceraFacturac entities
     * that are retrieved from Proveedores?cap_first and returns the navigation
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

}
