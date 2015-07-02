package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.ImpuestoFacade;
import edu.uc.modulocontable.modelo2.Impuesto;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean(name = "impuestoController")
@SessionScoped
public class ImpuestoController extends AbstractController<Impuesto> implements Serializable {

    @EJB
    private ImpuestoFacade ejebFacade;

    public ImpuestoController() {
        // Inform the Abstract parent controller of the concrete Impuesto?cap_first Entity
        super(Impuesto.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }

}
