/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.administracion;

import edu.uc.modulocontable.facade.CabeceraFacturacFacade;
import edu.uc.modulocontable.facade.CabeceraFacturavFacade;
import edu.uc.modulocontable.general.GenerarFacturaComprasPDF;
import edu.uc.modulocontable.general.GenerarFacturaVentasPDF;
import edu.uc.modulocontable.modelo2.CabeceraFacturac;
import edu.uc.modulocontable.modelo2.CabeceraFacturav;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "listarFacturaHelper")
@SessionScoped
public class ListarFacturaHelper implements Serializable{

    @EJB
    private CabeceraFacturacFacade ejbCabeceraC;
    @EJB
    private CabeceraFacturavFacade ejbCabeceraV;
    private List<CabeceraFacturac> facturasCompras;
    private List<CabeceraFacturav> facturasVentas;
    private CabeceraFacturac selected;

    public List<CabeceraFacturac> getFacturasCompras() {
        return facturasCompras = ejbCabeceraC.findAll();
    }

    public void setFacturasCompras(List<CabeceraFacturac> facturasCompras) {
        this.facturasCompras = facturasCompras;
    }

    public List<CabeceraFacturav> getFacturasVentas() {
        return facturasVentas = ejbCabeceraV.findAll();
    }

    public void setFacturasVentas(List<CabeceraFacturav> facturasVentas) {
        this.facturasVentas = facturasVentas;
    }

    public CabeceraFacturac getSelected() {
        return selected;
    }

    public void setSelected(CabeceraFacturac selected) {
        this.selected = selected;
    }

    public void descargarFacturaCompras(CabeceraFacturac c) {
        //String ruta = "/Users/cuent/" + nombre + ".pdf";
        //CabeceraFacturac c = this.getSelected();
        String nombre = c.getPtoEmision() + "-" + c.getEstablecimiento() + "-" + c.getNumeroFactura() + ".pdf";
        String ruta = "/Users/cuent/Downloads/" + nombre;

        GenerarFacturaComprasPDF generarPdf = new GenerarFacturaComprasPDF();
        generarPdf.generarFactura(c, ruta);

    }

    public void descargarFacturaVentas(CabeceraFacturav c) {
        String nombre = c.getPtoEmision() + "-" + c.getEstablecimiento() + "-" + c.getNumeroFactura() + ".pdf";
        String ruta = "/Users/cuent/Downloads/" + nombre;

        System.out.println("ruta: " + ruta);
        GenerarFacturaVentasPDF generarPdf = new GenerarFacturaVentasPDF();
        generarPdf.generarFactura(c, ruta);

    }
}
