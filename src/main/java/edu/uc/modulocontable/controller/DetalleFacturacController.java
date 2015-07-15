package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.DetalleFacturacFacade;
import edu.uc.modulocontable.modelo2.DetalleFacturac;
import edu.uc.modulocontable.modelo2.DetalleFacturacPK;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name  = "detalleFacturacController")
@SessionScoped
public class DetalleFacturacController extends AbstractController<DetalleFacturac> {

    @EJB
    private DetalleFacturacFacade ejbFacade;
   

    /**
     * Initialize the concrete DetalleFacturac controller bean. The
     * AbstractController requires the EJB Facade object for most operations.
     * <p>
     * In addition, this controller also requires references to controllers for
     * parent entities in order to display their information from a context
     * menu.
     */
    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

    public DetalleFacturacController() {
        // Inform the Abstract parent controller of the concrete DetalleFacturac?cap_first Entity
        super(DetalleFacturac.class);
    }

    @Override
    protected void setEmbeddableKeys() {
        this.getSelected().getDetalleFacturacPK().setCodigoFactura(this.getSelected().getCabeceraFacturac().getCodigoFactura());
        this.getSelected().getDetalleFacturacPK().setCodigoProducto(this.getSelected().getProducto().getCodigoProducto());
    }

    @Override
    protected void initializeEmbeddableKey() {
        this.getSelected().setDetalleFacturacPK(new DetalleFacturacPK());
    }



}
