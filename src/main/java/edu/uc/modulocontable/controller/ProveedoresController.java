package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.ProveedoresFacade;
import edu.uc.modulocontable.modelo2.Proveedores;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean(name = "proveedoresController")
@SessionScoped
public class ProveedoresController extends AbstractController<Proveedores> implements Serializable {

    @EJB
    private ProveedoresFacade ejbFacade;

    public ProveedoresController() {
        // Inform the Abstract parent controller of the concrete Proveedores?cap_first Entity
        super(Proveedores.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }

}
