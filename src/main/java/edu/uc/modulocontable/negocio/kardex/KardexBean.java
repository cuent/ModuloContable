/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.negocio.kardex;

import edu.uc.modulocontable.beans.backing.util.JsfUtil;
import edu.uc.modulocontable.facade.KardexFacade;
import edu.uc.modulocontable.facade.ProductoFacade;
import edu.uc.modulocontable.modelo2.Producto;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "kardexBeen")
@SessionScoped
public class KardexBean implements Serializable {

    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private KardexFacade kardexFacade;
    private Producto producto;
    private List<Producto> productos;
    private List<KardexLIFO> inventario;
    private int cantidadEntrada = 0;
    private int cantidadSalida = 0;
    private int saldoCantidad = 0;
    private double saldoTotal = 0;
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getSaldoCantidad() {
        return saldoCantidad;
    }

    public void setSaldoCantidad(int saldoCantidad) {
        this.saldoCantidad = saldoCantidad;
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public int getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(int cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }

    public int getCantidadEntrada() {
        return cantidadEntrada;
    }

    public void setCantidadEntrada(int cantidadEntrada) {
        this.cantidadEntrada = cantidadEntrada;
    }

    public List<KardexLIFO> getInventario() {
//        GenerarKardex kardex = new GenerarKardex(kardexFacade.query(producto), producto);
//        kardex.generarLIFO();
//        return kardex.getInventarioTotalLIFO();
        return inventario;
    }

    public void setInventario(List<KardexLIFO> inventario) {
        this.inventario = inventario;
    }

    public ProductoFacade getProductoFacade() {
        return productoFacade;
    }

    public void setProductoFacade(ProductoFacade productoFacade) {
        this.productoFacade = productoFacade;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getProductos() {
        return productos = productoFacade.findAll();
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public void test() {
        GenerarKardex kardex = new GenerarKardex(kardexFacade.query(producto), producto);
//        //Metodo FIFO
//        kardex.generarFIFO();
//        List<KardexFIFO> fifo = kardex.getInventarioTotalFIFO();
//        for (KardexFIFO fifo1 : fifo) {
//            System.out.println(fifo1);
//        }
//        System.out.println("\n\n\n\n ******************************************  \n\n\n\n");
        //Metodo LIFO
        kardex.generarLIFO();
        List<KardexLIFO> lifo = kardex.getInventarioTotalLIFO();
        setInventario(lifo);
        kardex.descargarFactura();

        setCantidadEntrada(0);
        setCantidadSalida(0);
        setSaldoCantidad(0);
        setSaldoTotal(0);

        for (KardexLIFO lifo1 : lifo) {
            System.out.println(lifo1);
            if (lifo1.getTipo().equalsIgnoreCase("entrada")) {
                setCantidadEntrada(getCantidadEntrada() + lifo1.getCantidad());
            } else {
                List<SalidaKardex> s = lifo1.getSalidas();
                for (SalidaKardex s1 : s) {
                    setCantidadSalida(getCantidadSalida() + s1.getCantidad());
                }
            }
        }
        Stack<Inventario> saldo = kardex.getInventarioLIFO();
        for (Inventario saldo1 : saldo) {
            setSaldoCantidad(getSaldoCantidad() + saldo1.getTotalCantidad());
            setSaldoTotal(getSaldoTotal() + saldo1.getTotalSubtotal());
        }
//        System.out.println("\n\n\n\n ******************************************  \n\n\n\n");
//        //Metodo Poderado
//        kardex.generarPonderado();
//        List<KardexPonderado> ponderado = kardex.getInventarioTotalPonderado();
//        for (KardexPonderado ponderado1 : ponderado) {
//            System.out.println(ponderado1);
//        }
        if (lifo.isEmpty()) {
            String mensaje = ResourceBundle.getBundle("/mensajes").getString("inventarioVacio");
            JsfUtil.addErrorMessage(mensaje);
        } else {
            String toUrl = "/pages/reporte_kardex.xhtml";
            FacesContext ctx = FacesContext.getCurrentInstance();

            ExternalContext extContext = ctx.getExternalContext();
            String url = extContext.encodeActionURL(ctx.getApplication().
                    getViewHandler().getActionURL(ctx, toUrl));

            try {
                extContext.redirect(url);
            } catch (IOException ex) {
                Logger.getLogger(KardexBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void testFecha() {
        GenerarKardex kardex = new GenerarKardex(kardexFacade.query(producto, this.getFecha()), producto);
//        //Metodo FIFO
//        kardex.generarFIFO();
//        List<KardexFIFO> fifo = kardex.getInventarioTotalFIFO();
//        for (KardexFIFO fifo1 : fifo) {
//            System.out.println(fifo1);
//        }
//        System.out.println("\n\n\n\n ******************************************  \n\n\n\n");
        //Metodo LIFO
        kardex.generarLIFO();
        List<KardexLIFO> lifo = kardex.getInventarioTotalLIFO();
        setInventario(lifo);
        kardex.descargarFactura();

        setCantidadEntrada(0);
        setCantidadSalida(0);
        setSaldoCantidad(0);
        setSaldoTotal(0);

        for (KardexLIFO lifo1 : lifo) {
            System.out.println(lifo1);
            if (lifo1.getTipo().equalsIgnoreCase("entrada")) {
                setCantidadEntrada(getCantidadEntrada() + lifo1.getCantidad());
            } else {
                List<SalidaKardex> s = lifo1.getSalidas();
                for (SalidaKardex s1 : s) {
                    setCantidadSalida(getCantidadSalida() + s1.getCantidad());
                }
            }
        }
        Stack<Inventario> saldo = kardex.getInventarioLIFO();
        for (Inventario saldo1 : saldo) {
            setSaldoCantidad(getSaldoCantidad() + saldo1.getTotalCantidad());
            setSaldoTotal(getSaldoTotal() + saldo1.getTotalSubtotal());
        }
//        System.out.println("\n\n\n\n ******************************************  \n\n\n\n");
//        //Metodo Poderado
//        kardex.generarPonderado();
//        List<KardexPonderado> ponderado = kardex.getInventarioTotalPonderado();
//        for (KardexPonderado ponderado1 : ponderado) {
//            System.out.println(ponderado1);
//        }
        if (lifo.isEmpty()) {
            String mensaje = ResourceBundle.getBundle("/mensajes").getString("inventarioVacio");
            JsfUtil.addErrorMessage(mensaje);
        } else {
            String toUrl = "/pages/reporte_kardex.xhtml";
            FacesContext ctx = FacesContext.getCurrentInstance();

            ExternalContext extContext = ctx.getExternalContext();
            String url = extContext.encodeActionURL(ctx.getApplication().
                    getViewHandler().getActionURL(ctx, toUrl));

            try {
                extContext.redirect(url);
            } catch (IOException ex) {
                Logger.getLogger(KardexBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void regresarPagina() {
        String toUrl = "/pages/kardex.xhtml";
        FacesContext ctx = FacesContext.getCurrentInstance();

        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().
                getViewHandler().getActionURL(ctx, toUrl));

        try {
            extContext.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(KardexBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
