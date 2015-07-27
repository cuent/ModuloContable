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
import edu.uc.modulocontable.services.ejb.Cuenta;
import edu.uc.modulocontable.services.ejb.Transaccion;
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
public class GenerarLibroMayor {

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

    public GenerarLibroMayor() {
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

    public void generarFactura(List<Cuenta> cuentas, String ruta) {
        documento = new Documento(ruta);
        documento.setPageSize(PageSize.A4);
        PdfWriter writer = documento.getWriter();
        writer.setPageEvent(encabezado);
        documento.setMargins(40, 30, 30, 40);
        informacionpiePagina();
        documento.open();
        try {
            cabecera();
            detalleDocumento1(cuentas);
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
        PdfPTable tablePanelInicial = new PdfPTable(2);
        tablePanelInicial.setWidthPercentage(100);
        tablePanelInicial.getDefaultCell().setBorder(0);

        PdfPTable tablePanelIzquierdo = new PdfPTable(1);
        tablePanelIzquierdo.setWidthPercentage(50);
        tablePanelIzquierdo.getDefaultCell().setBorder(0);

        PdfPTable tablePanelDerecho = new PdfPTable(1);
        tablePanelDerecho.setWidthPercentage(100);
        tablePanelDerecho.getDefaultCell().setBorder(0);

        titulo1.setTexto("Empresa ABC");
        tablePanelIzquierdo.addCell(titulo1.getElemento());

        imagen.setDir("/Users/cuent/Downloads/abc-log.jpg");
        tablePanelIzquierdo.addCell(imagen.getImage());

        tablePanelInicial.addCell(tablePanelIzquierdo);

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        titulo2.setTexto("LIBRO MAYOR");
        titulo2.getElementoRojo();
        tablePanelDerecho.addCell(titulo2.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablePanelInicial.addCell(tablePanelDerecho);
        documento.add(tablePanelInicial);
    }

    private void detalleDocumento1(List<Cuenta> cuentas) throws DocumentException {
        for (Cuenta inv : cuentas) {
            documento.add(espacioBlanco.getElemento());

            tablaHorizontal.setColumnas(2);

            Vector vector = new Vector();

            List datos4 = new ArrayList();

            datos4.add("Debe");
            datos4.add("Haber");
            vector.add(datos4);
            
            for (Transaccion tran : inv.getTransaccionList()) {
                List datos = new ArrayList();

                datos.add("$" + String.valueOf(tran.getDebe()));
                datos.add("$" + String.valueOf(tran.getHaber()));
                vector.add(datos);
            }
            
            List datos3 = new ArrayList();

            datos3.add("_________________________________________");
            datos3.add("_________________________________________");
            vector.add(datos3);
            
            List datos1 = new ArrayList();

            datos1.add("$" + String.valueOf(inv.getTotalDebe()));
            datos1.add("$" + String.valueOf(inv.getTotalHaber()));
            vector.add(datos1);
            
            List datos2 = new ArrayList();

            datos2.add(inv.getTipo());
            datos2.add("$" + String.valueOf(inv.getDiferencia()));
            vector.add(datos2);

            Object[] datosV = new Object[vector.size()];
            for (int k = 0; k < vector.size(); k++) {
                datosV[k] = vector.get(k);
            }

            tablaHorizontal.setTitulos("Cuenta:", inv.getDescripcion());
            tablaHorizontal.setContenidos(datosV);
            tablaHorizontal.setAlineamientos(new int[]{1, 1});
            tablaHorizontal.setTamanos(new int[]{5,5});
            tablaHorizontal.llenarTabla();
            tablaHorizontal.setPosicion(2);
            tablaHorizontal.setAnchoTabla(100);
            documento.add(tablaHorizontal.getTabla());
            documento.add(espacioBlanco.getElemento());
            tablaHorizontal.limpiar();
        }

    }

}
