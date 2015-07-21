/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.factura.venta;

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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "cabeceraFacturavHelper")
@SessionScoped
public class CabeceraFacturavHelper implements Serializable {

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
    private DetalleFacturav detalleFactura;
    private String codigoProducto;
    private String nombreProducto;
    private List<DetalleFacturav> listaDetalle;
    private CabeceraFacturav selected;
    private double descuento = 0;
    private Autorizaciones autorizacion;
    private String tipoIdentificacion = "Consumidor Final";
    private boolean esconsumidorfinal = true;
    private String identificacionCliente;
    private FormasPago formaPago;
    private Cliente cliente;
    private String ruta = null;
    FacesMessage msg;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public FormasPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormasPago formaPago) {
        this.formaPago = formaPago;
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    public boolean isEsconsumidorfinal() {
        return esconsumidorfinal;
    }

    public void setEsconsumidorfinal(boolean esconsumidorfinal) {
        this.esconsumidorfinal = esconsumidorfinal;
    }

    public Autorizaciones getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(Autorizaciones autorizacion) {
        this.autorizacion = autorizacion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public CabeceraFacturav getSelected() {
        return selected;
    }

    public void setSelected(CabeceraFacturav selected) {
        this.selected = selected;
    }

    public List<DetalleFacturav> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetalleFacturav> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public void iniciarDetalleFactura() {
        detalleFactura = new DetalleFacturav();
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void eliminarDetalle(DetalleFacturav detalle) {

        if (listaDetalle != null) {
            listaDetalle.remove(detalle);
            detalleFactura = new DetalleFacturav();
        }

    }

        public void buscarCliente() {
        if (identificacionCliente != null && identificacionCliente.length() > 0) {
            List<Cliente> listaC = ejeClienteFacade.buscaCliente(getIdentificacionCliente());
            if (listaC != null && listaC.size() > 0) {
                this.getSelected().setCodigoCliente(listaC.get(0));
            }
        }
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
            getSelected().setAutorizacionSri(autorizacion);
            getSelected().setFecha(new Date());
        }
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
            double subtotal = 0;
            double iva = 0;
            double descuentos = 0;
            boolean tieneIva = true;
            for (DetalleFacturav detalle : listaDetalle) {

                if (detalle.getProducto().getImpuesto().getValor() == 12 || detalle.getProducto().getImpuesto().getValor() == 0.12) {
                    tieneIva = true;
                } else if (detalle.getProducto().getImpuesto().getValor() == 0 || detalle.getProducto().getImpuesto().getValor() == 0.00) {
                    tieneIva = false;
                } else {
                    subtotal = detalle.getTotal();
                }
                //cargar esta variable dependiendo de que si tien iva o no
                if (tieneIva) {
                    SubtotalIva += detalle.getTotal();

                } else {
                    subTotalCero += detalle.getTotal();

                }

            }//fin
            iva = (SubtotalIva - descuentos) * 0.12;
            descuentos = (SubtotalIva + subTotalCero + subtotal) * (getDescuento() / 100);
            this.getSelected().setSubtotalBase0(subTotalCero);
            this.getSelected().setSubtotalBaseIva(SubtotalIva);
            this.getSelected().setSubtotal(subtotal + subTotalCero + SubtotalIva);
            this.getSelected().setDescuento(descuentos);
            this.getSelected().setIva(iva);
            this.getSelected().setTotal(subTotalCero + SubtotalIva + iva - descuentos);

        }
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
                        ejbFacade.create(this.getSelected());
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

    public void RestablecerValores() {
        identificacionCliente = null;
        listaDetalle = new ArrayList<>();
        formaPago = null;
        cliente = null;
        nombreProducto = null;
        identificacionCliente = null;
        iniciarDetalleFactura();
    }

    private void descargarFactura(CabeceraFacturav c) {
        //String ruta = "/Users/cuent/" + nombre + ".pdf";
        String nombre = c.getPtoEmision() + "-" + c.getEstablecimiento() + "-" + c.getNumeroFactura() + ".pdf";
        String ruta = "/Users/cuent/Downloads/" + nombre;

        //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        //ruta = ec.getRealPath("/" + nombre);
        System.out.println("ruta: " + ruta);
        this.setRuta(ruta);
        GenerarFacturaVentas generarPdf = new GenerarFacturaVentas();
        generarPdf.generarFactura(c, ruta);

    }

    private void guardarDetalles(CabeceraFacturav cabezera) {
        for (DetalleFacturav listaDetalle1 : listaDetalle) {
            listaDetalle1.setDetalleFacturavPK(new DetalleFacturavPK(cabezera.getCodigoFactura(), listaDetalle1.getProducto().getCodigoProducto()));
            ejebDetalleFacturavFacade.create(listaDetalle1);
            listaDetalle1.getProducto().setStock(listaDetalle1.getProducto().getStock() - listaDetalle1.getCantidad());
            ejebProoductoFacade.edit(listaDetalle1.getProducto());

        }

    }

    private boolean productoExiste(int codigo) {
        for (DetalleFacturav d : listaDetalle) {
            if (d.getProducto().getCodigoProducto() == codigo) {
                return true;
            }
        }
        return false;
    }

    public DetalleFacturav getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(DetalleFacturav detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

}
