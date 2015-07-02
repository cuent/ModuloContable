package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.modelo2.Autorizaciones;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@ManagedBean(name = "autorizacionesController")
@SessionScoped
public class AutorizacionesController extends AbstractController<Autorizaciones> implements Serializable {

    public AutorizacionesController() {
        // Inform the Abstract parent controller of the concrete Autorizaciones?cap_first Entity
        super(Autorizaciones.class);
    }

}
