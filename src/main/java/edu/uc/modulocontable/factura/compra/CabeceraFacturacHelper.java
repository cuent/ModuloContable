package edu.uc.modulocontable.factura.compra;

import edu.uc.modulocontable.bean.util.JsfUtil;
import edu.uc.modulocontable.bean.util.Sesion;
import edu.uc.modulocontable.domain.entity.AsientoFacade;
import edu.uc.modulocontable.domain.entity.CuentaFacade;
import edu.uc.modulocontable.facade.CabeceraFacturacFacade;
import edu.uc.modulocontable.facade.DetalleFacturacFacade;
import edu.uc.modulocontable.facade.FormasPagoFacade;
import edu.uc.modulocontable.facade.ImpuestoFacade;
import edu.uc.modulocontable.facade.ProductoFacade;
import edu.uc.modulocontable.facade.ProveedoresFacade;
import edu.uc.modulocontable.general.GenerarFacturaComprasPDF;
import edu.uc.modulocontable.modelo2.CabeceraFacturac;
import edu.uc.modulocontable.modelo2.DetalleFacturac;
import edu.uc.modulocontable.modelo2.DetalleFacturacPK;
import edu.uc.modulocontable.modelo2.Producto;
import edu.uc.modulocontable.modelo2.Proveedores;
import edu.uc.modulocontable.services.ejb.Asiento;
import edu.uc.modulocontable.services.ejb.Cuenta;
import edu.uc.modulocontable.services.ejb.Transaccion;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    private AsientoFacade asientoFacade;
    @EJB
    private ProveedoresFacade codigoProveedorController;
    @EJB
    private FormasPagoFacade formaPagoController;
    @EJB
    private CuentaFacade ejbCuenta;
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
        getSelected().setFecha(new Date());
        int numFactura = ejbFacade.findAll().size() + 1;
        getSelected().setNumeroFactura(String.valueOf(numFactura));
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
            if (Integer.parseInt(getSelected().getNumeroFactura())
                    >= Integer.parseInt(getSelected().getAutorizacionSri().getNumeroInicialDocumento())
                    && Integer.parseInt(getSelected().getNumeroFactura())
                    <= Integer.parseInt(getSelected().getAutorizacionSri().getNumeroFinalDocumento())) {

                if (getSelected().getCodigoProveedor() != null) {

                    if (getSelected().getFormaPago() != null) {
                        this.getSelected().setFecha(new Date());
                        ejbFacade.create(this.getSelected());
                        List< CabeceraFacturac> listac = ejbFacade.buscaxNumeroFac(getSelected().getNumeroFactura());
                        if (listac != null && listac.size() > 0) {
                            CabeceraFacturac c = listac.get(0);
                            for (DetalleFacturac d : detalles) {
                                d.getProducto().setStock(d.getCantidad() + d.getProducto().getStock());
                                productoFacade.edit(d.getProducto());
                            }

                            for (DetalleFacturac d : detalles) {
                                System.out.println("codigo factura # " + c.getCodigoFactura());
                                System.out.println("codigo producto: " + d.getProducto().getCodigoProducto());
                                d.setDetalleFacturacPK(new DetalleFacturacPK(c.getCodigoFactura(), d.getProducto().getCodigoProducto()));
                                detalleFCFacade.create(d);
                            }
                            //Sesion.redireccionaPagina(ResourceBundle.getBundle("/MyBundle").getString("listaFacturasc"));
                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ustede ha realizado una compra!", "Factura Generada");
                            FacesContext.getCurrentInstance().addMessage(null, msg);

                            //this.getSelected()
                            this.getSelected().setDetalleFacturacList(detalles);

                            descargarFactura();
                            generarAsientos(c, detalles);
                            iniciarNuevo();
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
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA", "Revise el rango de facturas ["
                        + getSelected().getAutorizacionSri().getNumeroInicialDocumento() + ","
                        + getSelected().getAutorizacionSri().getNumeroFinalDocumento() + "]");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } else {

            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "select en null");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    private Cuenta getCuenta(String nombre) {
        List<Cuenta> cuentas = ejbCuenta.getCuentaxNumbreCuentaYCategoria(nombre.trim(), "DETALLE");
        if (cuentas != null && cuentas.size() > 0) { // cuendo la cuenta existe
            return cuentas.get(0);
        } else {
            return null;
        }
    }

    public int obtenerAnio(Date date) {
        if (null == date) {

            return 0;
        } else {
            String formato = "yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Integer.parseInt(dateFormat.format(date));
        }

    }

    private void generarAsientos(CabeceraFacturac cabecera, ArrayList<DetalleFacturac> detalles) {
        BigDecimal totalDebe = new BigDecimal(cabecera.getTotal());
        BigDecimal totalHaber = new BigDecimal(cabecera.getTotal());
        //Redondear 2 decimales
        totalDebe = totalDebe.setScale(2, BigDecimal.ROUND_HALF_UP);
        totalHaber = totalHaber.setScale(2, BigDecimal.ROUND_HALF_UP);
        Cuenta cuenta;
        if (totalDebe.equals(totalHaber) && totalDebe.compareTo(BigDecimal.ZERO) > 0
                && totalHaber.compareTo(BigDecimal.ZERO) > 0) {
            List<Transaccion> tSalida = new ArrayList<>();

            //Transaccion Mercaderias
            Transaccion tMercaderias = new Transaccion();
            tMercaderias.setDebe(new BigDecimal(cabecera.getSubtotal()).setScale(2, BigDecimal.ROUND_HALF_UP));
            tMercaderias.setHaber(BigDecimal.ZERO);
            tMercaderias.setReferencia("Ingresa $" + cabecera.getSubtotal() + " en Mercaderia");
            cuenta = getCuenta("1.1.5.1.01");
            tMercaderias.setIdcodcuenta(cuenta);
            tSalida.add(tMercaderias);
            if (cabecera.getSubtotalBaseIva() != 0.0) {
                //Transaccion IVA
                Transaccion tIva = new Transaccion();
                tIva.setDebe(new BigDecimal(cabecera.getIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
                tIva.setHaber(BigDecimal.ZERO);
                tIva.setReferencia("$" + cabecera.getIva() + " de Iva por Compras");
                cuenta = getCuenta("1.1.3.1.01");
                tIva.setIdcodcuenta(cuenta);
                tSalida.add(tIva);
            }

            //Forma de Pago
            if (cabecera.getFormaPago().getIdcodcuenta() != null) {//No es DxP o CxP
                Transaccion tPago = new Transaccion();
                tPago.setDebe(BigDecimal.ZERO);
                tPago.setHaber(new BigDecimal(cabecera.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));
                tPago.setReferencia("Se pagó $" + cabecera.getTotal() + " por la compra de Mercaderias");
                tPago.setIdcodcuenta(cabecera.getFormaPago().getIdcodcuenta());
                tSalida.add(tPago);
            }

            //Validar que Debe==Haber
            BigDecimal tdebe = BigDecimal.ZERO;
            BigDecimal thaber = BigDecimal.ZERO;
            for (Transaccion tSalida1 : tSalida) {
                tdebe = tdebe.add(tSalida1.getDebe());
                thaber = thaber.add(tSalida1.getHaber());
            }

            //Ingresar Transacciones y Asiento
            if (thaber.equals(tdebe)) {
                List<Asiento> asientosAux = asientoFacade.findAll();
                int numAsiento = asientosAux.size() + 1;

                Asiento asientoAux = new Asiento();
                asientoAux.setIdcodasiento(numAsiento);
                asientoAux.setNumasiento(numAsiento);
                asientoAux.setNumdiario(1);
                asientoAux.setPeriodo(obtenerAnio(cabecera.getFecha()));
                asientoAux.setFecha(cabecera.getFecha());
                asientoAux.setDebe(tdebe);
                asientoAux.setHaber(thaber);
                asientoAux.setConcepto("Compra de Mercaderia - Factura #" + cabecera.getNumeroFactura());
                asientoAux.setDocumento(cabecera.getCodigoFactura());

                for (Transaccion t : tSalida) {
                    t.setIdcodasiento(asientoAux);
                }
                asientoAux.setTransaccionList(tSalida);
                asientoFacade.create(asientoAux);
            } else {
                JsfUtil.addErrorMessage("Verifique los valores ERROR: Debe!=Haber en comprobación");
            }
        } else {
            JsfUtil.addErrorMessage("Verifique los valores ERROR: Debe!=Haber");
        }
    }

    private void descargarFactura() {
        //String ruta = "/Users/cuent/" + nombre + ".pdf";
        CabeceraFacturac c = this.getSelected();
        String nombre = "FACT_COMPRAS" + c.getAutorizacionSri().getPtoEmision() + "-" + c.getEstablecimiento() + "-" + 
                c.getNumeroFactura() + new Date().toString() + ".pdf";
        String ruta = "/Users/cuent/Downloads/" + nombre;

        GenerarFacturaComprasPDF generarPdf = new GenerarFacturaComprasPDF();
        generarPdf.generarFactura(c, ruta);

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

        for (DetalleFacturac detalle : detalles) {
            //detalle.getProducto().setStock(detalle.getCantidad());
            detalle.setPrecioUnitario(detalle.getProducto().getCosto());
            detalle.setTotal(detalle.getCantidad() * detalle.getProducto().getCosto());

            if (detalle.getProducto().getImpuesto().getValor() != 0) {
                baseIva = baseIva + (detalle.getCantidad() * (detalle.getProducto().getCosto()));
                iva += 0.12 * detalle.getCantidad() * detalle.getProducto().getCosto();
            } else {
                baseCero = baseCero + detalle.getCantidad() * detalle.getProducto().getCosto();
            }

            subtotal = subtotal + detalle.getTotal();
        }

        this.getSelected().setSubtotal(subtotal);
        this.getSelected().setSubtotalBase0(baseCero);
        this.getSelected().setSubtotalBaseIva(baseIva);
        this.getSelected().setIva(iva);
        this.getSelected().setTotal(subtotal + iva);

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
