/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.negocio.kardex;

import edu.uc.modulocontable.facade.KardexFacade;
import edu.uc.modulocontable.facade.ProductoFacade;
import edu.uc.modulocontable.modelo2.Kardex;
import edu.uc.modulocontable.modelo2.Producto;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "kardexBeen")
@RequestScoped
public class KardexBean {

    @EJB
    private ProductoFacade productoFacade;
    @EJB
    private KardexFacade kardexFacade;
    private Producto producto;
    private List<Producto> productos;

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
        GenerarKardex kardex = new GenerarKardex(kardexFacade.query(producto));
        //Metodo FIFO
        kardex.generarFIFO();
        List<KardexFIFO> fifo = kardex.getInventarioTotalFIFO();
        for (KardexFIFO fifo1 : fifo) {
            System.out.println(fifo1);
        }
        System.out.println("\n\n\n\n ******************************************  \n\n\n\n");
        //Metodo LIFO
        kardex.generarLIFO();
        List<KardexLIFO> lifo = kardex.getInventarioTotalLIFO();
        for (KardexLIFO lifo1 : lifo) {
            System.out.println(lifo1);
        }
        System.out.println("\n\n\n\n ******************************************  \n\n\n\n");
        //Metodo Poderado
        kardex.generarPonderado();
        List<KardexPonderado> ponderado = kardex.getInventarioTotalPonderado();
        for (KardexPonderado ponderado1 : ponderado) {
            System.out.println(ponderado1);
        }
    }
}
