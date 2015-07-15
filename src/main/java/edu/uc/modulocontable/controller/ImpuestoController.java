package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.ImpuestoFacade;
import edu.uc.modulocontable.modelo2.Impuesto;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "impuestoController")
@SessionScoped
public class ImpuestoController extends AbstractController<Impuesto> implements Serializable {

    @EJB
    private ImpuestoFacade ejbFacade;

    /**
     * Initialize the concrete Impuesto controller bean. The AbstractController
     * requires the EJB Facade object for most operations.
     * <p>
     * In addition, this controller also requires references to controllers for
     * parent entities in order to display their information from a context
     * menu.
     */
    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

    public ImpuestoController() {
        // Inform the Abstract parent controller of the concrete Impuesto?cap_first Entity
        super(Impuesto.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

    /**
     * Sets the "items" attribute with a collection of Producto entities that
     * are retrieved from Impuesto?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Producto page
     */
    public String navigateProductoList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Producto_items", this.getSelected().getProductoList());
        }
        return "/generado/nuevo/producto/index";
    }

}
