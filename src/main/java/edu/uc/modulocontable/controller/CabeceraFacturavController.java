package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.AutorizacionesFacade;
import edu.uc.modulocontable.facade.CabeceraFacturavFacade;
import edu.uc.modulocontable.facade.ClienteFacade;
import edu.uc.modulocontable.facade.DetalleFacturavFacade;
import edu.uc.modulocontable.facade.ProductoFacade;
import edu.uc.modulocontable.general.GenerarFacturaVentas;
import edu.uc.modulocontable.modelo2.Autorizaciones;
import edu.uc.modulocontable.modelo2.CabeceraFacturav;
import edu.uc.modulocontable.modelo2.Cliente;
import edu.uc.modulocontable.modelo2.DetalleFacturav;
import edu.uc.modulocontable.modelo2.DetalleFacturavPK;
import edu.uc.modulocontable.modelo2.FormasPago;
import edu.uc.modulocontable.modelo2.Producto;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "cabeceraFacturavController")
@SessionScoped
public class CabeceraFacturavController extends AbstractController<CabeceraFacturav> implements Serializable {

    @EJB
    private CabeceraFacturavFacade ejbFacade;
    @EJB
    private ClienteFacade ejeClienteFacade;
    @EJB
    private ProductoFacade ejebProoductoFacade;
    @EJB
    private AutorizacionesFacade ejebAutorizacionesFacade;
    @EJB
    private DetalleFacturavFacade ejebDetalleFacturavFacade;
    private String identificacionCliente;
    private Cliente cliente;
    private FormasPago formaPago;
    private List<DetalleFacturav> listaDetalle;
    private DetalleFacturav detalleFactura;
    private Producto producto;
    private String codigoProducto;
    private String nombreProducto;
    FacesMessage msg;
    private double descuento = 0;
    private Autorizaciones autorizacion;
    private StreamedContent file;
    private String tipoIdentificacion = "Consumidor Final";
    private boolean esconsumidorfinal = true;
    private String ruta = null;

    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

    public CabeceraFacturavController() {
        super(CabeceraFacturav.class);
    }

    public void iniciarNuevo() {
        this.setSelected(new CabeceraFacturav());
        listaDetalle = new ArrayList<DetalleFacturav>();
        List<Autorizaciones> listaautorizacion = ejebAutorizacionesFacade.findAll();
        if (listaautorizacion != null && listaautorizacion.size() > 0) {
            setAutorizacion(listaautorizacion.get(0));
            getSelected().setNumeroFactura(Integer.parseInt(getAutorizacion().getNumeroActual()) + 1);
            getSelected().setPtoEmision(getAutorizacion().getPtoEmision());
            getSelected().setEstablecimiento(getAutorizacion().getEstablecimiento());
            getSelected().setAutorizacionSri(getAutorizacion().getNumeroAutorizacion());
            getSelected().setFecha(new Date());

        }

    }

    public void iniciarDetalleFactura() {
        detalleFactura = new DetalleFacturav();
    }

    public void isConsumidorfinal() {
        System.out.println("tipo: " + tipoIdentificacion);
        if (tipoIdentificacion != null) {
            if (tipoIdentificacion.trim().equalsIgnoreCase("Consumidor Final")) {
                esconsumidorfinal = true;

            } else {
                esconsumidorfinal = false;

            }
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formFactura");

        System.out.println(esconsumidorfinal);
    }

    public void buscarCliente() {
        if (identificacionCliente != null && identificacionCliente.length() > 0) {
            List<Cliente> listaC = ejeClienteFacade.buscaCliente(getIdentificacionCliente());
            if (listaC != null && listaC.size() > 0) {
                this.getSelected().setCodigoCliente(listaC.get(0));
            }

        }

    }

    public void buscarProducto() {
        if (getCodigoProducto() != null && getCodigoProducto().length() > 0) {
            List<Producto> listaC = ejebProoductoFacade.buscaProducto(Integer.parseInt(codigoProducto));
            if (listaC != null && listaC.size() > 0) {
                getDetalleFactura().setProducto(listaC.get(0));
                setNombreProducto(getDetalleFactura().getProducto().getNombre());
                getDetalleFactura().setPrecioUnitario(listaC.get(0).getPrecio());
            } else {
                getDetalleFactura().setProducto(null);
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "No Existe El producto");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    private boolean comprobarStok() {
        boolean hayStock = false;
        if (getDetalleFactura().getCantidad() > 0) {
            int stock = getDetalleFactura().getProducto().getStock();
            if ((stock - getDetalleFactura().getCantidad()) < 0) {
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "No hay Producto:\n Hay un stock de: " + getDetalleFactura().getProducto().getStock());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                hayStock = false;
            } else {
                hayStock = true;
            }
        }
        return hayStock;
    }

    public void calcularTotal() {
        if (comprobarStok()) {
            double unit = getDetalleFactura().getPrecioUnitario();
            int cantidad = getDetalleFactura().getCantidad();
            getDetalleFactura().setTotal(unit * cantidad);
        } else {
            getDetalleFactura().setCantidad(0);
            getDetalleFactura().setTotal(0);
        }
    }

    public void eliminarDetalle(DetalleFacturav detalle) {

        if (listaDetalle != null) {
            listaDetalle.remove(detalle);
            detalleFactura = new DetalleFacturav();
        }

    }

    public void buscarProductoNombre() {

        List<Producto> listaC = ejebProoductoFacade.buscaProducto(getDetalleFactura().getProducto().getNombre());
        if (listaC != null && listaC.size() > 0) {
            getDetalleFactura().setProducto(listaC.get(0));
            setCodigoProducto(String.valueOf(getDetalleFactura().getProducto().getCodigoProducto()));
            getDetalleFactura().setPrecioUnitario(listaC.get(0).getPrecio());
        } else {

            getDetalleFactura().setProducto(null);
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "No Existe El producto");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void gudarFactura(ActionEvent event) {

        if (getSelected() != null) {
            if (isEsconsumidorfinal()) {

                List<Cliente> listCliente = ejeClienteFacade.buscaTipoIdentificador(tipoIdentificacion);
                if (listCliente != null && listCliente.size() > 0) {
                    Cliente c = listCliente.get(0);
                    this.getSelected().setCodigoCliente(c);
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "Cree un Cliente Consumidor final");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }

            }
            if (getSelected().getCodigoCliente() != null) {

                if (formaPago != null) {
                    if (listaDetalle != null && listaDetalle.size() > 0) {

                        this.getSelected().setFormaPago(formaPago);
                        this.save(event);
                        int numfactura = Integer.valueOf(getAutorizacion().getNumeroActual()) + 1;
                        autorizacion.setNumeroActual(String.valueOf(numfactura));
                        ejebAutorizacionesFacade.edit(autorizacion);

                        List<CabeceraFacturav> lista = ejbFacade.buscaxNumeroFac(getSelected().getNumeroFactura());
                        if (lista != null && lista.size() > 0) {
                            CabeceraFacturav cabe = lista.get(0);

                            guardarDetalles(cabe);
                            cabe.setDetalleFacturavList(listaDetalle);
                            descargarFactura(cabe);
                            RestablecerValores();
                        }

                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "No hay Productos Cargados");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        return;
                    }
                } else {
                    msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "Selecione forma Pago");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }

            } else {

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "No hay Clientes Cargados");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        }

    }

    private void guardarDetalles(CabeceraFacturav cabezera) {
        for (DetalleFacturav listaDetalle1 : listaDetalle) {
            listaDetalle1.setDetalleFacturavPK(new DetalleFacturavPK(cabezera.getCodigoFactura(), listaDetalle1.getProducto().getCodigoProducto()));
            ejebDetalleFacturavFacade.create(listaDetalle1);
            listaDetalle1.getProducto().setStock(listaDetalle1.getProducto().getStock() - listaDetalle1.getCantidad());
            ejebProoductoFacade.edit(listaDetalle1.getProducto());

        }

    }

    public void RestablecerValores() {
        identificacionCliente = null;
        listaDetalle = new ArrayList<>();
        formaPago = null;
        cliente = null;
        nombreProducto = null;
        identificacionCliente = null;
        iniciarDetalleFactura();
    }

    private boolean productoExiste(int codigo) {
        for (DetalleFacturav d : listaDetalle) {
            if (d.getProducto().getCodigoProducto() == codigo) {
                return true;
            }
        }
        return false;
    }

    public void guadarDetalleFactura() {

        if (getDetalleFactura().getProducto() != null) {
            if (comprobarStok()) {
                if (getDetalleFactura().getTotal() > 0) {
                    if (!productoExiste(getDetalleFactura().getProducto().getCodigoProducto())) {
                        listaDetalle.add(getDetalleFactura());
                        detalleFactura = new DetalleFacturav();
                        codigoProducto = null;
                        calacularSubTotalesFactura();
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Producto Agregado");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "El Producto ya fue Escojido");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                }
            } else {
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "El Stock o la cantidad del Producto Esta en Cero");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Informacion", "Carge Un Producto");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void calacularSubTotalesFactura() {
        if (listaDetalle != null && listaDetalle.size() > 0) {
            double SubtotalIva = 0;
            double subTotalCero = 0;
            double total = 0;
            double subtotal=0;
            double iva = 0;
            double descuentos = 0;
            boolean tieneIva = true;
            for (DetalleFacturav detalle : listaDetalle) {

                if (detalle.getProducto().getImpuesto().getValor() == 12 || detalle.getProducto().getImpuesto().getValor() == 0.12) {
                    tieneIva = true;
                } else if (detalle.getProducto().getImpuesto().getValor() == 0 || detalle.getProducto().getImpuesto().getValor() == 0.00) {
                    tieneIva = false;
                }else{
                  subtotal=detalle.getTotal();
                }
                //cargar esta variable dependiendo de que si tien iva o no
                if (tieneIva) {
                    SubtotalIva += detalle.getTotal();

                } else {
                    subTotalCero += detalle.getTotal();

                }

            }//fin
            iva = (SubtotalIva - descuentos) * 0.12;
            descuentos = (SubtotalIva + subTotalCero+subtotal) * (getDescuento() / 100);
            this.getSelected().setSubtotalBase0(subTotalCero);
            this.getSelected().setSubtotalBaseIva(SubtotalIva);
            this.getSelected().setSubtotal(subtotal+subTotalCero+SubtotalIva);
            this.getSelected().setDescuento(descuentos);
            this.getSelected().setIva(iva);
            this.getSelected().setTotal(subTotalCero + SubtotalIva + iva - descuentos);

        }
    }

    public StreamedContent prepDownload() {
        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(getRuta());
        file = new DefaultStreamedContent(stream, "application/pdf", "factura.pdf");

        return file;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    /**
     * @return the formaPago
     */
    public FormasPago getFormaPago() {
        return formaPago;
    }

    /**
     * @param formaPago the formaPago to set
     */
    public void setFormaPago(FormasPago formaPago) {
        this.formaPago = formaPago;
    }

    /**
     * @return the listaDetalle
     */
    public List<DetalleFacturav> getListaDetalle() {
        return listaDetalle;
    }

    /**
     * @param listaDetalle the listaDetalle to set
     */
    public void setListaDetalle(List<DetalleFacturav> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    /**
     * @return the detalleFactura
     */
    public DetalleFacturav getDetalleFactura() {
        return detalleFactura;
    }

    /**
     * @param detalleFactura the detalleFactura to set
     */
    public void setDetalleFactura(DetalleFacturav detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * @return the codigoProducto
     */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * @param codigoProducto the codigoProducto to set
     */
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    /**
     * @return the nombreProducto
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * @param nombreProducto the nombreProducto to set
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * @return the descuento
     */
    public double getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the autorizacion
     */
    public Autorizaciones getAutorizacion() {
        return autorizacion;
    }

    /**
     * @param autorizacion the autorizacion to set
     */
    public void setAutorizacion(Autorizaciones autorizacion) {
        this.autorizacion = autorizacion;
    }

    private void descargarFactura(CabeceraFacturav c) {
        String ruta = "";
        String nombre = c.getPtoEmision() + "-" + c.getEstablecimiento() + "-" + c.getNumeroFactura() + ".pdf";
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ruta = ec.getRealPath("/" + nombre);
        System.out.println("ruta: " + ruta);
        this.setRuta(ruta);
        GenerarFacturaVentas generarPdf = new GenerarFacturaVentas();
        generarPdf.generarFactura(c, ruta);

    }

    /**
     * @return the file
     */
    public StreamedContent getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(StreamedContent file) {
        this.file = file;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the esconsumidorfinal
     */
    public boolean isEsconsumidorfinal() {
        return esconsumidorfinal;
    }

    /**
     * @param esconsumidorfinal the esconsumidorfinal to set
     */
    public void setEsconsumidorfinal(boolean esconsumidorfinal) {
        this.esconsumidorfinal = esconsumidorfinal;
    }

    /**
     * @return the ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

}
