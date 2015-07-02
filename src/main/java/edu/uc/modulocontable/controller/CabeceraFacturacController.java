package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.CabeceraFacturacFacade;
import edu.uc.modulocontable.modelo2.CabeceraFacturac;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "cabeceraFacturacController")
@SessionScoped
public class CabeceraFacturacController extends AbstractController<CabeceraFacturac> implements Serializable {

    @EJB
    private CabeceraFacturacFacade ejbFacade;

    public CabeceraFacturacController() {
        // Inform the Abstract parent controller of the concrete CabeceraFacturac?cap_first Entity
        super(CabeceraFacturac.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }

}
