package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.KardexFacade;
import edu.uc.modulocontable.modelo2.Kardex;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "kardexController")
@SessionScoped
public class KardexController extends AbstractController<Kardex> implements Serializable {

    @EJB
    private KardexFacade ejbFacade;

    public KardexController() {
        // Inform the Abstract parent controller of the concrete Kardex?cap_first Entity
        super(Kardex.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }

}
