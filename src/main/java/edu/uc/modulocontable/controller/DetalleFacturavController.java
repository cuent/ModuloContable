package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.DetalleFacturavFacade;
import edu.uc.modulocontable.modelo2.DetalleFacturav;
import edu.uc.modulocontable.modelo2.DetalleFacturavPK;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "detalleFacturavController")
@SessionScoped
public class DetalleFacturavController extends AbstractController<DetalleFacturav> {

    @EJB
    private DetalleFacturavFacade ejbFacade;
   

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

    public DetalleFacturavController() {
        // Inform the Abstract parent controller of the concrete DetalleFacturav?cap_first Entity
        super(DetalleFacturav.class);
    }

    @Override
    protected void setEmbeddableKeys() {
        this.getSelected().getDetalleFacturavPK().setCodigoFactura(this.getSelected().getCabeceraFacturav().getCodigoFactura());
        this.getSelected().getDetalleFacturavPK().setCodigoProducto(this.getSelected().getProducto().getCodigoProducto());
    }

    @Override
    protected void initializeEmbeddableKey() {
        this.getSelected().setDetalleFacturavPK(new DetalleFacturavPK());
    }


}
