package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.FormasPagoFacade;
import edu.uc.modulocontable.modelo2.FormasPago;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean(name = "formasPagoController")
@SessionScoped
public class FormasPagoController extends AbstractController<FormasPago> implements Serializable {

    @EJB
    private FormasPagoFacade ejbFacade;

    public FormasPagoController() {
        // Inform the Abstract parent controller of the concrete FormasPago?cap_first Entity
        super(FormasPago.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }
}
