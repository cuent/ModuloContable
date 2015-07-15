package edu.uc.modulocontable.factura.compra;

import edu.uc.modulocontable.bean.util.Sesion;
import edu.uc.modulocontable.facade.CabeceraFacturacFacade;
import edu.uc.modulocontable.facade.DetalleFacturacFacade;
import edu.uc.modulocontable.facade.FormasPagoFacade;
import edu.uc.modulocontable.facade.ImpuestoFacade;
import edu.uc.modulocontable.facade.ProductoFacade;
import edu.uc.modulocontable.facade.ProveedoresFacade;
import edu.uc.modulocontable.modelo2.CabeceraFacturac;
import edu.uc.modulocontable.modelo2.DetalleFacturac;
import edu.uc.modulocontable.modelo2.DetalleFacturacPK;
import edu.uc.modulocontable.modelo2.Producto;
import edu.uc.modulocontable.modelo2.Proveedores;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "cabeceraFacturacHelper")
@SessionScoped
public class CabeceraFacturacHelper implements Serializable {

    @EJB
    private CabeceraFacturacFacade ejbFacade;
    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private DetalleFacturacFacade detalleFCFacade;
    @EJB
    private ImpuestoFacade impuestoFacade;

    @EJB
    private ProveedoresFacade codigoProveedorController;
    @EJB
    private FormasPagoFacade formaPagoController;
    private CabeceraFacturac selected;
    ArrayList<DetalleFacturac> detalles;
    List<Producto> productos;
    DetalleFacturac selectedDetalle;
    Producto productoFoco;
    FacesMessage msg;

    @PostConstruct
    public void init() {
        //super.setFacade(ejbFacade);
    }

    public CabeceraFacturacHelper() {
        // Inform the Abstract parent controller of the concrete CabeceraFacturac Entity
        //super(CabeceraFacturac.class);
        detalles = new ArrayList<DetalleFacturac>();
        selectedDetalle = new DetalleFacturac();

    }

    public void iniciarNuevo() {
        this.setSelected(new CabeceraFacturac());
        detalles = new ArrayList<DetalleFacturac>();
        selectedDetalle = new DetalleFacturac();
    }

    public CabeceraFacturac getSelected() {
        return selected;
    }

    public void setSelected(CabeceraFacturac selected) {
        this.selected = selected;
    }

    /**
     * Sets the "items" attribute with a collection of Kardex entities that are
     * retrieved from CabeceraFacturac?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Kardex page
     */
    public String navigateKardexList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Kardex_items", this.getSelected().getKardexList());
        }
        return "/kardex/index";
    }

    /**
     * Sets the "selected" attribute of the Proveedores controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    /**
     * Sets the "items" attribute with a collection of DetalleFacturac entities
     * that are retrieved from CabeceraFacturac?cap_first and returns the
     * navigation outcome.
     *
     * @return navigation outcome for DetalleFacturac page
     */
    public String navigateDetalleFacturacList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("DetalleFacturac_items", this.getSelected().getDetalleFacturacList());
        }
        return "/detalleFacturac/index";
    }

    public DetalleFacturac getSelectedDetalle() {
        return selectedDetalle;
    }

    public void setSelectedDetalle(DetalleFacturac selectedDetalle) {
        this.selectedDetalle = selectedDetalle;
    }

    public ProductoFacade getProductoFacade() {
        return productoFacade;
    }

    public void setProductoFacade(ProductoFacade productoFacade) {
        this.productoFacade = productoFacade;
    }

    public ArrayList<DetalleFacturac> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetalleFacturac> detalles) {
        this.detalles = detalles;
    }

    public void proveedorSeleccionado(ValueChangeEvent evt) {

        Proveedores aux = (Proveedores) evt.getNewValue();
        this.getSelected().setAutorizacionSri(aux.getAutorizacion());
        this.getSelected().setPtoEmision(aux.getDireccion());
        this.getSelected().setEstablecimiento(aux.getNombre());

    }

    public void initFecha() {
        if (this.getSelected() != null) {
            CabeceraFacturac aux = this.getSelected();
            Calendar fecha = new GregorianCalendar();
            this.getSelected().setFecha(fecha.getTime());
        }
    }

    public void añadirDetalle() {
        System.out.println("anadio detalle");
        DetalleFacturac df = new DetalleFacturac();
        Producto p = new Producto();
        p.setCodigoProducto(productoFacade.findAll().size() + detalles.size() + 1);
        p.setImpuesto(impuestoFacade.find(1));
        df.setCantidad(1);
        df.setProducto(p);
        detalles.add(df);
    }

    public List<String> completeTextCodigo(String query) {
        DetalleFacturac d = selectedDetalle;
        productos = productoFacade.findAll();
        List<String> results = new ArrayList<String>();
        for (Producto p : productos) {
            if ((p.getCodigoProducto() + "").contains(query)) {
                results.add(p.getCodigoProducto() + "");
            }

        }

        return results;
    }

    public List<String> completeTextNombre(String query) {
        productos = productoFacade.findAll();
        List<String> results = new ArrayList<String>();
        for (Producto p : productos) {
            if ((p.getNombre().toLowerCase()).contains(query.toLowerCase())) {
                results.add(p.getNombre());
            }

        }

        return results;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null) {

            if (detalles.get(event.getRowIndex()).getCantidad() == 0) {
                detalles.get(event.getRowIndex()).setCantidad(1);
            }

            detalles.get(event.getRowIndex()).setProducto(productoFoco);
        }
    }

    public void gardarFactura(ActionEvent event) {

        if (getSelected() != null) {

            if (getSelected().getCodigoProveedor() != null) {

                if (getSelected().getFormaPago() != null) {
                    this.getSelected().setFecha(new Date());
                    ejbFacade.create(this.getSelected());
                    List< CabeceraFacturac> listac = ejbFacade.buscaxNumeroFac(getSelected().getNumeroFactura());
                    if (listac != null && listac.size() > 0) {
                        CabeceraFacturac c = listac.get(0);
                        for (DetalleFacturac d : detalles) {
                            productoFacade.edit(d.getProducto());
                        }

                        for (DetalleFacturac d : detalles) {
                            System.out.println("codigo fac" + c.getCodigoFactura());
                            System.out.println("codigo pro" + d.getProducto().getCodigoProducto());
                            d.setDetalleFacturacPK(new DetalleFacturacPK(c.getCodigoFactura(), d.getProducto().getCodigoProducto()));
                            detalleFCFacade.create(d);
                        }
                        Sesion.redireccionaPagina(ResourceBundle.getBundle("/MyBundle").getString("listaFacturasc"));

                    }

                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "Carge forma Pago");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }

            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "Carge un Proveedor");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {

            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "select en null");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void handleSelect(SelectEvent e) {
        if (e.getObject() != null) {
            Integer intt = (Integer) e.getObject();
            productos = productoFacade.findAll();
            for (Producto p : productos) {
                if (p.getCodigoProducto() == intt.intValue()) {
                    productoFoco = p;
                    break;
                }
            }

        }

    }

    public void handleSelectDescripcion(SelectEvent e) {
        if (e.getObject() != null) {
            String intt = (String) e.getObject();
            productos = productoFacade.findAll();
            for (Producto p : productos) {
                if (p.getNombre().equals(intt)) {
                    productoFoco = p;
                    break;
                }
            }

        }

    }

    public void actualizarFactura() {

        double subtotal = 0;
        double baseCero = 0;
        double baseIva = 0;
        double total = 0;
        double iva = 0;
        boolean isTarifaCero = false;

        for (DetalleFacturac detalle : detalles) {
            detalle.getProducto().setStock(detalle.getCantidad());
            detalle.setPrecioUnitario(detalle.getProducto().getPrecio());
            detalle.setTotal(detalle.getCantidad() * detalle.getProducto().getPrecio());

            if (detalle.getProducto().getImpuesto().getValor() == 12) {
                isTarifaCero = false;
            } else if (detalle.getProducto().getImpuesto().getValor() == 0) {
                isTarifaCero = true;
            } else {

            }

            if (isTarifaCero) {
                baseCero = baseCero + detalle.getCantidad() * detalle.getProducto().getPrecio();
            } else {
                baseIva = baseIva + (detalle.getCantidad() * (detalle.getProducto().getPrecio()));
            }

            subtotal = subtotal + detalle.getTotal();

        }

        this.getSelected().setSubtotal(subtotal);
        this.getSelected().setSubtotalBase0(baseCero);
        this.getSelected().setSubtotalBaseIva(baseIva);
        this.getSelected().setIva(subtotal * 0.12);
        this.getSelected().setTotal(subtotal + (subtotal * 0.12));

    }

    public void guadar(ActionEvent event) {
        if (this.getSelected() != null) {

            ///this.saveNew(event);
            for (CabeceraFacturac cfc : ejbFacade.findAll()) {
                if (cfc.getFecha().equals(this.getSelected().getFecha())) {
                    setSelected(cfc);
                }
            }
            for (DetalleFacturac d : detalles) {
                productoFacade.create(d.getProducto());

            }
            for (DetalleFacturac d : detalles) {
                d.setDetalleFacturacPK(new DetalleFacturacPK(this.getSelected().getCodigoFactura(), d.getProducto().getCodigoProducto()));
            }

            this.getSelected().setDetalleFacturacList(detalles);
            //super.save(event);

        }
    }

}
