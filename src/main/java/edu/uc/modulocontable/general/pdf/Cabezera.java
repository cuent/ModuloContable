/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import edu.uc.modulocontable.modelo2.CabeceraFacturav;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Juan Pablo
 */
public class Cabezera extends PdfPageEventHelper {

    
    private String numeroAutorizacion;
    private String fechaAutorizacion;
    private String ambiente;
    private String emision;
    private String claveAcceso;
    private String direccionLogotipo;
    private CabeceraFacturav cabezeraFactura;

    private TablaVertical tablaVertical;
    private Imagen imagen;
    private Titulo2 titulo2;
    private Titulo3 titulo3;
    private Titulo1 titulo1;

    private EspacioBlanco espacioBlanco;
    private PdfWriter writer;

    public Cabezera() {
        
        espacioBlanco = new EspacioBlanco();
        tablaVertical = new TablaVertical();
        imagen = new Imagen();
        titulo2 = new Titulo2();
        titulo3 = new Titulo3();
        titulo1 = new Titulo1();
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        super.onStartPage(writer, document);
        try {
            addHeaderTable(document);
        } catch (DocumentException ex) {
            Logger.getLogger(Cabezera.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private float clacularTamano(String texto) {

        float res = 0, tam = texto.trim().length();
        float diferencia = ((tam * 12) / 30) - 12;

        if (tam <= 30) {
            res = 12;
        } else if (tam > 30) {
            res = 12 - diferencia + 1;
        }

        return res;
    }

    public void addHeaderTable(Document document)
            throws DocumentException {
        PdfPTable tablePanelInicial = new PdfPTable(2);
        tablePanelInicial.setWidthPercentage(100);

        PdfPTable tablePanelIzquierdo = new PdfPTable(1);
        tablePanelIzquierdo.setWidthPercentage(100);
        tablePanelIzquierdo.getDefaultCell().setBorder(0);

        PdfPTable tablePanelDerecho = new PdfPTable(1);
        tablePanelDerecho.setWidthPercentage(100);
        tablePanelDerecho.getDefaultCell().setBorder(0);

        titulo2.getElementoNegro();
        titulo3.getElementoNegro();
        
        imagen.setDir("C:\\Users\\Juan Pablo\\Pictures\\emp.png");
        tablePanelIzquierdo.addCell(imagen.getImage());

        titulo2.getElementoVerde();
        titulo2.setTexto("Empresa ABC");

    
        tablePanelIzquierdo.addCell(titulo2.getElemento(2));

     

        titulo1.setTexto("RUC: " + "01059512710001");
        tablePanelDerecho.addCell(titulo1.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        titulo2.setTexto("FACTURA");
        titulo2.getElementoRojo();
        tablePanelDerecho.addCell(titulo2.getElemento());

        titulo2.setTexto("No.: " + cabezeraFactura.getEstablecimiento() + "-" + cabezeraFactura.getPtoEmision()
                + "-" + cabezeraFactura.getNumeroFactura());
        titulo2.getElementoRojo();
        tablePanelDerecho.addCell(titulo2.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablaVertical.limpiar(1);
        int[] tamanos2 = {50};
        int[] alineamientos = {0, 0};
        tablaVertical.setTamanos(tamanos2);
        tablaVertical.setTitulos("Número de Autorización:");
        Object[] datos10 = {cabezeraFactura.getAutorizacionSri()};
        tablaVertical.setContenidos(datos10);
        tablaVertical.setAlineamientos(alineamientos);
        tablaVertical.llenarTabla(false);
        tablaVertical.setPosicion(0);
        tablaVertical.setAnchoTabla(65);
        tablePanelDerecho.addCell(tablaVertical.getTabla());

        tablaVertical.limpiar();
        Object[] datos3 = {cabezeraFactura.getFecha().toString()};
        tablaVertical.setTitulos("Fecha y Hora de Autorización: ");
        tablaVertical.setContenidos(datos3);
        tablaVertical.llenarTabla(false);
        tablaVertical.setPosicion(0);
        tablaVertical.setAnchoTabla(65);
        tablePanelDerecho.addCell(tablaVertical.getTabla());

    
        document.add(tablePanelInicial);
        document.add(espacioBlanco.getElemento());

    }

  
  

    /**
     * @return the numeroAutorizacion
     */
    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    /**
     * @param numeroAutorizacion the numeroAutorizacion to set
     */
    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    /**
     * @return the fechaAutorizacion
     */
    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    /**
     * @param fechaAutorizacion the fechaAutorizacion to set
     */
    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    /**
     * @return the ambiente
     */
    public String getAmbiente() {
        return ambiente;
    }

    /**
     * @param ambiente the ambiente to set
     */
    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    /**
     * @return the emision
     */
    public String getEmision() {
        return emision;
    }

    /**
     * @param emision the emision to set
     */
    public void setEmision(String emision) {
        this.emision = emision;
    }

    /**
     * @return the claveAcceso
     */
    public String getClaveAcceso() {
        return claveAcceso;
    }

    /**
     * @param claveAcceso the claveAcceso to set
     */
    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    /**
     * @return the direccionLogotipo
     */
    public String getDireccionLogotipo() {
        return direccionLogotipo;
    }

    /**
     * @param direccionLogotipo the direccionLogotipo to set
     */
    public void setDireccionLogotipo(String direccionLogotipo) {
        this.direccionLogotipo = direccionLogotipo;
    }

    /**
     * @return the writer
     */
    public PdfWriter getWriter() {
        return writer;
    }

    /**
     * @param writer the writer to set
     */
    public void setWriter(PdfWriter writer) {
        this.writer = writer;
    }

    /**
     * @return the cabezeraFactura
     */
    public CabeceraFacturav getCabezeraFactura() {
        return cabezeraFactura;
    }

    /**
     * @param cabezeraFactura the cabezeraFactura to set
     */
    public void setCabezeraFactura(CabeceraFacturav cabezeraFactura) {
        this.cabezeraFactura = cabezeraFactura;
    }

}
