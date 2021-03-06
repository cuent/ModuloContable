/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.transaccion;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.uc.modulocontable.general.pdf.Documento;
import edu.uc.modulocontable.general.pdf.Encabezado;
import edu.uc.modulocontable.general.pdf.EspacioBlanco;
import edu.uc.modulocontable.general.pdf.Imagen;
import edu.uc.modulocontable.general.pdf.LineaCentrada;
import edu.uc.modulocontable.general.pdf.LineaNormal;
import edu.uc.modulocontable.general.pdf.TablaHorizontal;
import edu.uc.modulocontable.general.pdf.TablaVertical;
import edu.uc.modulocontable.general.pdf.Titulo1;
import edu.uc.modulocontable.general.pdf.Titulo2;
import edu.uc.modulocontable.general.pdf.Titulo3;
import edu.uc.modulocontable.general.pdf.Titulo4;
import edu.uc.modulocontable.info.empresa.Archivo;
import edu.uc.modulocontable.info.empresa.Empresa;
import edu.uc.modulocontable.services.ejb.Cuenta;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Juan Pablo
 */
public class GenerarComprobacion {

    private Encabezado encabezado;

    private LineaNormal lineaNormal;
    private LineaCentrada lineaCentrada;
    private TablaHorizontal tablaHorizontal;
    private TablaVertical tablaVertical;
    private Titulo1 titulo1;
    private Titulo2 titulo2;
    private Imagen imagen;
    private Titulo3 titulo3;
    private Titulo4 titulo4;
    private EspacioBlanco espacioBlanco;
    private Documento documento;
    private BigDecimal debe = new BigDecimal(BigInteger.ZERO);
    private BigDecimal haber = new BigDecimal(BigInteger.ZERO);
    private BigDecimal deudor = new BigDecimal(BigInteger.ZERO);
    private BigDecimal acreedor = new BigDecimal(BigInteger.ZERO);

    public GenerarComprobacion() {
        encabezado = new Encabezado();
        imagen = new Imagen();
        espacioBlanco = new EspacioBlanco();
        lineaNormal = new LineaNormal();
        lineaCentrada = new LineaCentrada();
        tablaHorizontal = new TablaHorizontal();
        tablaVertical = new TablaVertical();

        titulo1 = new Titulo1();
        titulo2 = new Titulo2();
        titulo3 = new Titulo3();
        titulo4 = new Titulo4();

    }

    public void generarFactura(List<Cuenta> cuenta, String ruta) {
        documento = new Documento(ruta);
        documento.setPageSize(PageSize.A4.rotate());
        PdfWriter writer = documento.getWriter();
        writer.setPageEvent(encabezado);
        documento.setMargins(40, 30, 30, 40);
        informacionpiePagina();
        documento.open();
        try {
            cabecera();
            cabeceraTabla();
            detalleDocumento(cuenta);
            totalDetalleDocumento();
        } catch (DocumentException ex) {

        }
        documento.close();

    }

    public void informacionpiePagina() {
        encabezado.setTextofondo("");
        encabezado.generarMarcaAgua();
        java.util.Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        encabezado.setEncabezado("Fecha de creación: " + formateador.format(ahora));
        documento.getWriter().setPageEvent(encabezado);
    }

    private void cabecera() throws DocumentException {
        Archivo a = new Archivo();
        Empresa empresa = a.obtieneContenidoArchivo().get(0);

        PdfPTable tablePanelInicial = new PdfPTable(2);
        tablePanelInicial.setWidthPercentage(100);
        tablePanelInicial.getDefaultCell().setBorder(0);

        PdfPTable tablePanelIzquierdo = new PdfPTable(1);
        tablePanelIzquierdo.setWidthPercentage(50);
        tablePanelIzquierdo.getDefaultCell().setBorder(0);

        PdfPTable tablePanelDerecho = new PdfPTable(1);
        tablePanelDerecho.setWidthPercentage(100);
        tablePanelDerecho.getDefaultCell().setBorder(0);

        titulo1.setTexto(empresa.getNombre());
        tablePanelIzquierdo.addCell(titulo1.getElemento());

        imagen.setDir("/Users/cuent/Downloads/abc-log.jpg");
        tablePanelIzquierdo.addCell(imagen.getImage());

        tablePanelInicial.addCell(tablePanelIzquierdo);

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        titulo2.setTexto("BALANCE DE COMPROBACIÓN");
        titulo2.getElementoRojo();
        tablePanelDerecho.addCell(titulo2.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablePanelInicial.addCell(tablePanelDerecho);
        documento.add(tablePanelInicial);
    }

    private void detalleDocumento(List<Cuenta> cuenta) throws DocumentException {

        if (!cuenta.isEmpty()) {
            documento.add(espacioBlanco.getElemento());

            tablaHorizontal.setColumnas(5);
            tablaHorizontal.setTitulos("Cuenta", "Debe", "Haber", "Deudor", "Acreedor");

            Vector vector = new Vector();
            for (Cuenta inv : cuenta) {

                List datos = new ArrayList();

                datos.add(inv.getDescripcion());
                datos.add("$" + String.valueOf(inv.getTotalDebe()));
                setDebe(getDebe().add(inv.getTotalDebe()));
                datos.add("$" + String.valueOf(inv.getTotalHaber()));
                setHaber(getHaber().add(inv.getTotalHaber()));
                datos.add("$" + String.valueOf(inv.getDeudor()));
                setDeudor(getDeudor().add(inv.getDeudor()));
                datos.add("$" + String.valueOf(inv.getAcreedor()));
                setAcreedor(getAcreedor().add(inv.getAcreedor()));

                vector.add(datos);

            }

            Object[] datos = new Object[vector.size()];
            for (int k = 0; k < vector.size(); k++) {
                datos[k] = vector.get(k);
            }

            tablaHorizontal.setContenidos(datos);
            tablaHorizontal.setAlineamientos(new int[]{0, 0, 1, 1, 1});
            tablaHorizontal.setTamanos(new int[]{10, 5, 5, 5, 5});
            tablaHorizontal.llenarTabla();
            tablaHorizontal.setPosicion(2);
            tablaHorizontal.setAnchoTabla(100);
            documento.add(tablaHorizontal.getTabla());
            tablaHorizontal.limpiar();
        }

    }

    private void totalDetalleDocumento() throws DocumentException {
        tablaHorizontal.setColumnas(5);

        Vector vector = new Vector();

        List datos = new ArrayList();

        datos.add("");
        datos.add("");
        datos.add("");
        datos.add("");
        datos.add("");

        vector.add(datos);

        Object[] datosV = new Object[vector.size()];
        for (int k = 0; k < vector.size(); k++) {
            datosV[k] = vector.get(k);
        }

        tablaHorizontal.setTitulos("Totales", "$" + getDebe(), "$" + getHaber(), "$" + getDeudor(), "$" + getAcreedor());
        tablaHorizontal.setContenidos(datosV);
        tablaHorizontal.setAlineamientos(new int[]{0, 0, 1, 1, 1});
        tablaHorizontal.setTamanos(new int[]{10, 5, 5, 5, 5});
        tablaHorizontal.llenarTabla();
        tablaHorizontal.setPosicion(2);
        tablaHorizontal.setAnchoTabla(100);
        documento.add(tablaHorizontal.getTabla());
        documento.add(espacioBlanco.getElemento());
        tablaHorizontal.limpiar();
    }

    private void cabeceraTabla() throws DocumentException {
        PdfPTable tablePanelInicial = new PdfPTable(5);
        tablePanelInicial.setWidthPercentage(100);
        tablePanelInicial.setWidths(new int[]{10, 5, 5, 5, 5});
        tablePanelInicial.getDefaultCell().setBorder(5);
        tablePanelInicial.setHorizontalAlignment(2);

        PdfPCell cell = new PdfPCell(new Paragraph("", FontFactory.getFont("arial", 10, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        cell.setBorder(0);
        tablePanelInicial.addCell(cell);

        cell = new PdfPCell(new Paragraph("Suma", FontFactory.getFont("arial", 10, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        tablePanelInicial.addCell(cell);

        cell = new PdfPCell(new Paragraph("Saldos", FontFactory.getFont("arial", 10, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        tablePanelInicial.addCell(cell);

        documento.add(tablePanelInicial);
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public BigDecimal getDeudor() {
        return deudor;
    }

    public void setDeudor(BigDecimal deudor) {
        this.deudor = deudor;
    }

    public BigDecimal getAcreedor() {
        return acreedor;
    }

    public void setAcreedor(BigDecimal acreedor) {
        this.acreedor = acreedor;
    }

}
