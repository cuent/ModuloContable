package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.ProductoFacade;
import edu.uc.modulocontable.modelo2.Producto;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "productoController")
@SessionScoped
public class ProductoController extends AbstractController<Producto> implements Serializable {

    @EJB
    private ProductoFacade ejbFacade;

    public ProductoController() {
        // Inform the Abstract parent controller of the concrete Producto?cap_first Entity
        super(Producto.class);
    }

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);

    }

}
