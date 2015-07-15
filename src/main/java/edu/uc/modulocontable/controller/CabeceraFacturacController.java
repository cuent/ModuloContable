package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.CabeceraFacturacFacade;
import edu.uc.modulocontable.modelo2.CabeceraFacturac;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name  = "cabeceraFacturacController")
@SessionScoped
public class CabeceraFacturacController extends AbstractController<CabeceraFacturac> {

    @EJB
    private CabeceraFacturacFacade ejbFacade;
  

    /**
     * Initialize the concrete CabeceraFacturac controller bean. The
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

    public CabeceraFacturacController() {
        // Inform the Abstract parent controller of the concrete CabeceraFacturac?cap_first Entity
        super(CabeceraFacturac.class);
    }

 

    /**
     * Sets the "items" attribute with a collection of DetalleFacturac entities
     * that are retrieved from CabeceraFacturac?cap_first and returns the
     * navigation outcome.
     *
     * @return navigation outcome for DetalleFacturac page
     */
    public String navigateDetalleFacturacList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("DetalleFacturac_items", this.getSelected().getDetalleFacturacList());
        }
        return "/generado/nuevo/detalleFacturac/index";
    }

    /**
     * Sets the "items" attribute with a collection of Kardex entities that are
     * retrieved from CabeceraFacturac?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Kardex page
     */
    public String navigateKardexList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Kardex_items", this.getSelected().getKardexList());
        }
        return "/generado/nuevo/kardex/index";
    }


}
