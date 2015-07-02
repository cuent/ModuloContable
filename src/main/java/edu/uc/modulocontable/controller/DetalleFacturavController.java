package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.DetalleFacturavFacade;
import edu.uc.modulocontable.modelo2.DetalleFacturav;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "detalleFacturavController")
@SessionScoped
public class DetalleFacturavController extends AbstractController<DetalleFacturav> implements Serializable {

    @EJB
    private DetalleFacturavFacade ejebFacade;

    public DetalleFacturavController() {
        // Inform the Abstract parent controller of the concrete DetalleFacturav?cap_first Entity
        super(DetalleFacturav.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }
}
