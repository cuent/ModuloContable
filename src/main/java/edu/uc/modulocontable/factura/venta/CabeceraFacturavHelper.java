/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.factura.venta;

import edu.uc.modulocontable.bean.util.JsfUtil;
import edu.uc.modulocontable.domain.entity.AsientoFacade;
import edu.uc.modulocontable.domain.entity.CuentaFacade;
import edu.uc.modulocontable.domain.entity.TransaccionFacade;
import edu.uc.modulocontable.facade.AutorizacionesFacade;
import edu.uc.modulocontable.facade.CabeceraFacturavFacade;
import edu.uc.modulocontable.facade.ClienteFacade;
import edu.uc.modulocontable.facade.DetalleFacturavFacade;
import edu.uc.modulocontable.facade.KardexFacade;
import edu.uc.modulocontable.facade.ProductoFacade;
import edu.uc.modulocontable.general.GenerarFacturaVentasPDF;
import edu.uc.modulocontable.modelo2.Autorizaciones;
import edu.uc.modulocontable.modelo2.CabeceraFacturav;
import edu.uc.modulocontable.modelo2.Cliente;
import edu.uc.modulocontable.modelo2.DetalleFacturav;
import edu.uc.modulocontable.modelo2.DetalleFacturavPK;
import edu.uc.modulocontable.modelo2.FormasPago;
import edu.uc.modulocontable.modelo2.Producto;
import edu.uc.modulocontable.negocio.kardex.GenerarKardex;
import edu.uc.modulocontable.negocio.kardex.Inventario;
import edu.uc.modulocontable.services.ejb.Asiento;
import edu.uc.modulocontable.services.ejb.Cuenta;
import edu.uc.modulocontable.services.ejb.Transaccion;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
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
    private AsientoFacade ejbAsientoFacade;
    @EJB
    private AsientoFacade asientoFacade;
    @EJB
    private TransaccionFacade ejbKTransacionFacade;
    @EJB
    private ClienteFacade ejeClienteFacade;
    @EJB
    private ProductoFacade ejebProoductoFacade;
    @EJB
    private AutorizacionesFacade ejebAutorizacionesFacade;
    @EJB
    private DetalleFacturavFacade ejebDetalleFacturavFacade;
    @EJB
    private CuentaFacade ejeCuentasFacade;
    @EJB
    private KardexFacade kardexFacade;
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

    //////////////////////////////////////////////////
    private Cuenta identificarCuentaPago() {
        Cuenta cuenta = null;
        if (formaPago.getDescripcion() != null && formaPago.getDescripcion().length() > 0) {
            if (formaPago.getDescripcion().equalsIgnoreCase("cheque")) {
                cuenta = getCuenta("1.1.1.1.2.01");
            } else if (formaPago.getDescripcion().equalsIgnoreCase("banco pichincha")) {
                cuenta = getCuenta("1.1.1.1.2.01");
            } else if (formaPago.getDescripcion().equalsIgnoreCase("banco guayaquil")) {
                cuenta = getCuenta("1.1.1.2.02");
            } else if (formaPago.getDescripcion().equalsIgnoreCase("credito")) {
                cuenta = getCuenta("1.1.2.1.01");
            } else if (formaPago.getDescripcion().equalsIgnoreCase("efectivo")) {
                cuenta = getCuenta("1.1.1.1.01");
            }
        } else {
            cuenta = getCuenta("1.1.1.1.01");
        }
        return cuenta;
    }

    private Cuenta getCuenta(String nombre) {
        List<Cuenta> cuentas = ejeCuentasFacade.getCuentaxNumbreCuentaYCategoria(nombre.trim(), "DETALLE");
        if (cuentas != null && cuentas.size() > 0) { // cuendo la cuenta existe
            return cuentas.get(0);
        } else { //cuendo la cuanta no existe
            Cuenta cuenta = new Cuenta();
            cuenta.setCategoria("DETALLE");
            cuenta.setDescripcion(nombre);
            return null;
        }
    }

    public BigDecimal cambiarformato(double valor) {

        return new BigDecimal(Math.round(valor * 100.0) / 100.0);
    }

    private void crearAsiento() {
        Asiento asiento = new Asiento();
        asiento.setConcepto("Venta de Mercaderia -Factura Nro.:" + this.getSelected().getNumeroFactura());
        asiento.setFecha(new Date());
        asiento.setPeriodo(2015);
        asiento.setDocumento(1);
        asiento.setNumasiento(ejbAsientoFacade.count() + 1);
        asiento.setNumdiario(ejbAsientoFacade.count() + 1);
        asiento.setDebe(cambiarformato(this.getSelected().getTotal()));
        asiento.setHaber(cambiarformato(this.getSelected().getTotal()));
        ejbAsientoFacade.create(asiento);

        List<Asiento> listAux = ejbAsientoFacade.AsientoxNumeroAsiento(asiento.getNumasiento());
        System.out.println("lista: " + listAux.size());
        if (listAux != null && listAux.size() > 0) {
            crearTrsaciones(listAux.get(0));
        } else {
            System.out.println("mno se guado el asiento ");
        }

    }

    private void crearTrsaciones(Asiento asiento) {
        List<Transaccion> lista = new ArrayList<>();
        //primero compruebo que las cuentes existan de forma de pago y ventas
        //para la cuanta forma de pago
        Transaccion transacionPago = new Transaccion();//[ara el pago
        transacionPago.setReferencia("    ");
        transacionPago.setDebe(cambiarformato(this.getSelected().getTotal()));
        transacionPago.setHaber(cambiarformato(0));
        transacionPago.setIdcodcuenta(identificarCuentaPago());
        //System.out.println("idAsiento: " + asiento.getIdAsiento());
        lista.add(transacionPago);
        //fin de la trasnacion forma de pago

        if (this.getSelected().getSubtotalBase0() > 0) {
            //creala la trasacicon de ventas q va en el debe
            Transaccion transacionVentasCero = new Transaccion();
            transacionVentasCero.setIdcodcuenta(buscarcuenta("4.1.1.2.01"));
            transacionVentasCero.setDebe(cambiarformato(0));
            transacionVentasCero.setHaber(cambiarformato(this.getSelected().getSubtotalBase0()));
            transacionVentasCero.setReferencia("Por venta de mercadaria");
            lista.add(transacionVentasCero);
            //fin de la trsanacion ventas 

        }
        if (this.getSelected().getSubtotalBaseIva() > 0) {
            Transaccion transacionVentas12 = new Transaccion();
            transacionVentas12.setIdcodcuenta(buscarcuenta("4.1.1.1.01"));
            transacionVentas12.setDebe(cambiarformato(0));
            transacionVentas12.setHaber(cambiarformato(this.getSelected().getSubtotalBaseIva()));
            transacionVentas12.setReferencia("Por venta de mercadaria");
            lista.add(transacionVentas12);

        }

        //quie depende s.i el valor tien iva o no 
        if (getSelected().getIva() > 0) { //si tien iva se crea otra cuanta mas
            Transaccion transacionIva = new Transaccion();
            transacionIva.setHaber(cambiarformato(getSelected().getIva()));
            transacionIva.setDebe(cambiarformato(0));
            transacionIva.setIdcodcuenta(buscarcuenta("2.1.3.1.01"));
            lista.add(transacionIva);
        }

        for (Transaccion listaTransacion1 : lista) {
            listaTransacion1.setIdcodasiento(asiento);
            System.out.println("datos: " + listaTransacion1.toString());
            System.out.println("datos asiento: " + asiento.toString());

            ejbKTransacionFacade.create(listaTransacion1);

        }

    }

    public Cuenta buscarcuenta(String nombre) {
        List<Cuenta> listCuentas;
        listCuentas = ejeCuentasFacade.getCuentaxNumCuenta(nombre);
        if (listCuentas != null && listCuentas.size() > 0) {
            System.out.println("nombe cuenta: : " + listCuentas.get(0).getDescripcion());
            return listCuentas.get(0);

        } else {
            System.out.println("no hay una cuenta debe mandar a crear");
            return null;
        }

    }

    ///////////////////////////////////////////////////////
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

                            generarAsientosFactura(cabe, listaDetalle);
                            generarAsientosCostoVentas(cabe, listaDetalle);

                            guardarDetalles(cabe);
                            cabe.setDetalleFacturavList(listaDetalle);

                            //crearAsiento();
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

    public int obtenerAnio(Date date) {
        if (null == date) {
            return 0;
        } else {
            String formato = "yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return Integer.parseInt(dateFormat.format(date));
        }

    }

    private void generarAsientosFactura(CabeceraFacturav cabecera, List<DetalleFacturav> detalles) {
        BigDecimal totalDebe = new BigDecimal(cabecera.getTotal());
        BigDecimal totalHaber = new BigDecimal(cabecera.getTotal());
        //Redondear 2 decimales
        totalDebe = totalDebe.setScale(2, BigDecimal.ROUND_HALF_UP);
        totalHaber = totalHaber.setScale(2, BigDecimal.ROUND_HALF_UP);
        Cuenta cuenta;
        if (totalDebe.equals(totalHaber) && totalDebe.compareTo(BigDecimal.ZERO) > 0
                && totalHaber.compareTo(BigDecimal.ZERO) > 0) {
            List<Transaccion> tSalida = new ArrayList<>();

            //Forma de Pago
            if (cabecera.getFormaPago().getIdcodcuenta() != null) {//No es DxC o CxC
                Transaccion tPago = new Transaccion();
                tPago.setDebe(new BigDecimal(cabecera.getTotal()).setScale(2, BigDecimal.ROUND_HALF_UP));
                tPago.setHaber(BigDecimal.ZERO);
                tPago.setReferencia("Se cobró $" + cabecera.getTotal() + " por venta de Mercaderias");
                tPago.setIdcodcuenta(cabecera.getFormaPago().getIdcodcuenta());
                tSalida.add(tPago);
            }
            if (cabecera.getSubtotalBase0() != 0.0) {//Ingreso en la Cuenta Ventas con tarifa 0%
                Transaccion tPago12 = new Transaccion();
                tPago12.setDebe(BigDecimal.ZERO);
                tPago12.setHaber(new BigDecimal(cabecera.getSubtotalBase0()).setScale(2, BigDecimal.ROUND_HALF_UP));
                tPago12.setReferencia("Se vendio $" + cabecera.getSubtotalBase0() + " en mercancia sin IVA.");
                tPago12.setIdcodcuenta(getCuenta("4.1.1.2.01"));
                tSalida.add(tPago12);
            }
            if (cabecera.getSubtotalBaseIva() != 0) {//Ingreso en la Cuenta Ventas con tarifa 12%
                Transaccion tPago0 = new Transaccion();
                tPago0.setDebe(BigDecimal.ZERO);
                tPago0.setHaber(new BigDecimal(cabecera.getSubtotalBaseIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
                tPago0.setReferencia("Se vendio $" + cabecera.getSubtotalBaseIva() + " en mercaderia con IVA 12%");
                tPago0.setIdcodcuenta(getCuenta("4.1.1.1.01"));
                tSalida.add(tPago0);
            }

            if (cabecera.getSubtotalBaseIva() != 0.0) { //Transaccion IVA
                Transaccion tIva = new Transaccion();
                tIva.setDebe(BigDecimal.ZERO);
                tIva.setHaber(new BigDecimal(cabecera.getIva()).setScale(2, BigDecimal.ROUND_HALF_UP));
                tIva.setReferencia("$" + cabecera.getIva() + " de Iva por ventas");
                cuenta = getCuenta("2.1.3.1.01");
                tIva.setIdcodcuenta(cuenta);
                tSalida.add(tIva);
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
                asientoAux.setConcepto("Venta de Mercaderia - Factura #" + cabecera.getNumeroFactura());
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

    private void generarAsientosCostoVentas(CabeceraFacturav cabecera, List<DetalleFacturav> detalles) {
        for (DetalleFacturav detalle : detalles) {
            GenerarKardex kardex = new GenerarKardex(kardexFacade.query(detalle.getProducto()), detalle.getProducto());
            kardex.generarLIFO();

            int cantidad = 0;
            double costo = 0.0;
            Stack<Inventario> inventario = kardex.getInventarioLIFO();
            while (detalle.getCantidad() != cantidad) {
                Inventario i = inventario.pop();
                cantidad += i.getTotalCantidad();
                if (cantidad > detalle.getCantidad()) {
                    int residuo = cantidad - detalle.getCantidad();
                    int unidades = i.getTotalCantidad() - residuo;
                    cantidad = cantidad - residuo;
                    costo += Math.round((unidades * i.getTotalCosto()) * 100.0) / 100.0;
                } else {
                    costo += Math.round((i.getTotalCantidad() * i.getTotalCosto()) * 100.0) / 100.0;
                }
            }

            //Guardar Asiento
            List<Transaccion> tSalida = new ArrayList<>();

            if (detalle.getProducto().getImpuesto().getValor() != 0.0) {//Ingreso en la Compras tarifa  12%
                Transaccion tcosto = new Transaccion();
                tcosto.setDebe(new BigDecimal(costo).setScale(2, BigDecimal.ROUND_HALF_UP));
                tcosto.setHaber(BigDecimal.ZERO);
                tcosto.setReferencia("Se vendio $" + costo + " del producto " + detalle.getProducto().getNombre() + ", con  IVA 12%.");
                tcosto.setIdcodcuenta(getCuenta("5.1.1.1.01"));
                tSalida.add(tcosto);
            } else {//Ingreso en la Compras tarifa  0%
                Transaccion tcosto = new Transaccion();
                tcosto.setDebe(new BigDecimal(costo).setScale(2, BigDecimal.ROUND_HALF_UP));
                tcosto.setHaber(BigDecimal.ZERO);
                tcosto.setReferencia("Se vendio $" + costo + " del producto " + detalle.getProducto().getNombre() + ", sin  IVA.");
                tcosto.setIdcodcuenta(getCuenta("5.1.1.1.02"));
                tSalida.add(tcosto);
            }
            //Ingreso en la Cuenta Ventas con tarifa 12%
            Transaccion tPago = new Transaccion();
            tPago.setDebe(BigDecimal.ZERO);
            tPago.setHaber(new BigDecimal(costo).setScale(2, BigDecimal.ROUND_HALF_UP));
            tPago.setReferencia("Salió $" + costo + " en mercaderia");
            tPago.setIdcodcuenta(getCuenta("1.1.5.1.01"));
            tSalida.add(tPago);

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
                asientoAux.setConcepto("Salida de Mercaderia al precio de costo - Factura #" + cabecera.getNumeroFactura());
                asientoAux.setDocumento(cabecera.getCodigoFactura());

                for (Transaccion t : tSalida) {
                    t.setIdcodasiento(asientoAux);
                }
                asientoAux.setTransaccionList(tSalida);
                asientoFacade.create(asientoAux);
            } else {
                JsfUtil.addErrorMessage("Verifique los valores ERROR: Debe!=Haber en comprobación");
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
        GenerarFacturaVentasPDF generarPdf = new GenerarFacturaVentasPDF();
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
