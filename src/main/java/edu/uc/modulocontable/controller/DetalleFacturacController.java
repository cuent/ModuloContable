package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.DetalleFacturacFacade;
import edu.uc.modulocontable.modelo2.DetalleFacturac;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "detalleFacturacController")
@SessionScoped
public class DetalleFacturacController extends AbstractController<DetalleFacturac> implements Serializable {

    @EJB
    private DetalleFacturacFacade ejbFacade;
  

    public DetalleFacturacController() {
        // Inform the Abstract parent controller of the concrete DetalleFacturac?cap_first Entity
        super(DetalleFacturac.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }

}
