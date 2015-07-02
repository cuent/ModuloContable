package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.CabeceraFacturavFacade;
import edu.uc.modulocontable.modelo2.CabeceraFacturav;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "cabeceraFacturavController")
@SessionScoped
public class CabeceraFacturavController extends AbstractController<CabeceraFacturav> implements Serializable {

    @EJB
    private CabeceraFacturavFacade ejbFacade;

    public CabeceraFacturavController() {
        // Inform the Abstract parent controller of the concrete CabeceraFacturav?cap_first Entity
        super(CabeceraFacturav.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }

}
