package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.AutorizacionesFacade;
import edu.uc.modulocontable.modelo2.Autorizaciones;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name  = "autorizacionesController")
@SessionScoped
public class AutorizacionesController extends AbstractController<Autorizaciones>{

    @EJB
    private AutorizacionesFacade ejbFacade;

    /**
     * Initialize the concrete Autorizaciones controller bean. The
     * AbstractController requires the EJB Facade object for most operations.
     */
    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
        
    }

    public AutorizacionesController() {
        // Inform the Abstract parent controller of the concrete Autorizaciones?cap_first Entity
        super(Autorizaciones.class);
    }

}
