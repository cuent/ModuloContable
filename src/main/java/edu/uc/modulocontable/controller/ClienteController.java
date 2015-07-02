package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.ClienteFacade;
import edu.uc.modulocontable.modelo2.Cliente;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "clienteController")
@SessionScoped
public class ClienteController extends AbstractController<Cliente> implements Serializable {

    @EJB
    private ClienteFacade ejeFacade;

    public ClienteController() {
        // Inform the Abstract parent controller of the concrete Cliente?cap_first Entity
        super(Cliente.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }

}
