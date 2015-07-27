/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.general;

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
import edu.uc.modulocontable.modelo2.CabeceraFacturav;
import edu.uc.modulocontable.modelo2.Producto;
import edu.uc.modulocontable.negocio.kardex.Inventario;
import edu.uc.modulocontable.negocio.kardex.KardexLIFO;
import edu.uc.modulocontable.negocio.kardex.SalidaKardex;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;

/**
 *
 * @author cuent
 */
public class GenerarKardexPDF {

    private Documento documento;
    private Encabezado encabezado;
    private Imagen imagen;
    private Titulo1 titulo1;
    private Titulo2 titulo2;
    private Titulo4 titulo4;
    private EspacioBlanco espacioBlanco;
    private TablaVertical tablaVertical;
    private TablaHorizontal tablaHorizontal;
    private LineaNormal lineaNormal;
    private int cantidadEntrada = 0;
    private int cantidadSalida = 0;
    private int saldoCantidad = 0;
    private double saldoTotal = 0;

    public GenerarKardexPDF() {
        encabezado = new Encabezado();
        imagen = new Imagen();
        espacioBlanco = new EspacioBlanco();
        lineaNormal = new LineaNormal();
        //lineaCentrada = new LineaCentrada();
        tablaHorizontal = new TablaHorizontal();
        tablaVertical = new TablaVertical();

        titulo1 = new Titulo1();
        titulo2 = new Titulo2();
        //    titulo3 = new Titulo3();
        titulo4 = new Titulo4();
    }

    public void generarFactura(List<KardexLIFO> inventario, Producto producto, String ruta) {
        documento = new Documento(ruta);
        documento.setPageSize(PageSize.A4.rotate());
        PdfWriter writer = documento.getWriter();
        writer.setPageEvent(encabezado);
        documento.setMargins(40, 30, 30, 40);
        informacionpiePaguina();
        documento.open();
        try {
            cabecera(producto);
            cabeceraTabla();
            detalleDocumento(inventario);
            totalDetalleDocumento();
            comprobarLIFO();
        } catch (DocumentException ex) {
            Logger.getLogger(GenerarFacturaVentasPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        documento.close();

    }

    public void informacionpiePaguina() {
        encabezado.setTextofondo("");
        encabezado.generarMarcaAgua();
        java.util.Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        encabezado.setEncabezado("Fecha de creación: " + formateador.format(ahora));
        documento.getWriter().setPageEvent(encabezado);
    }

    private void cabecera(Producto producto) throws DocumentException {
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

        titulo2.setTexto("KARDEX LIFO");
        titulo2.getElementoRojo();
        tablePanelDerecho.addCell(titulo2.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablaVertical.limpiar();
        int[] alineamientos = {0, 0, 0};
        Object[] datos = {producto.getCodigoProducto(), producto.getNombre(), producto.getStock()};
        tablaVertical.setTitulos("Codigo", "Producto:", "Stock:");
        tablaVertical.setContenidos(datos);
        tablaVertical.setAlineamientos(alineamientos);
        tablaVertical.llenarTabla(false);
        tablaVertical.setPosicion(0);
        tablaVertical.setAnchoTabla(65);
        tablePanelDerecho.addCell(tablaVertical.getTabla());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablePanelInicial.addCell(tablePanelDerecho);
        documento.add(tablePanelInicial);
    }

    private void detalleDocumento(List<KardexLIFO> inventario) throws DocumentException {
        String sCantidad = "", sCosto = "", sSubtotal = "";
        String sTotalCantidad = "", sTotalCosto = "", sTotalSubtotal = "";

        if (!inventario.isEmpty()) {
            documento.add(espacioBlanco.getElemento());

            tablaHorizontal.setColumnas(11);
            tablaHorizontal.setTitulos("Fecha", "Detalle", "# Unidades", "PU", "Costo Total", "# Unidades", "PU", "Costo Total", "# Unidades", "PU", "Costo Total");

            Vector vector = new Vector();
            for (KardexLIFO inv : inventario) {
                sCantidad = "";
                sCosto = "";
                sSubtotal = "";
                sTotalCantidad = "";
                sTotalCosto = "";
                sTotalSubtotal = "";

                List datos = new ArrayList();

                if (inv.getTipo().equalsIgnoreCase("entrada")) {
                    setCantidadEntrada(getCantidadEntrada() + inv.getCantidad());

                } else {
                    List<SalidaKardex> s = inv.getSalidas();
                    for (SalidaKardex s1 : s) {
                        setCantidadSalida(getCantidadSalida() + s1.getCantidad());
                    }
                }

                datos.add(inv.getFecha().toString());
                datos.add(inv.getDetalle());
                if (inv.getTipo().equalsIgnoreCase("entrada")) {
                    datos.add(String.valueOf(inv.getCantidad()));
                    datos.add(String.valueOf(inv.getCosto()));
                    datos.add(String.valueOf(inv.getSubtotal()));
                } else {
                    datos.add("");
                    datos.add("");
                    datos.add("");
                }

                if (inv.getTipo().equalsIgnoreCase("salida")) {
                    List<SalidaKardex> salida = inv.getSalidas();
                    for (SalidaKardex s : salida) {
                        sCantidad += s.getCantidad() + "\n";
                        sCosto += s.getCosto() + "\n";
                        sSubtotal += s.getSubtotal() + "\n";
                    }
                    datos.add(sCantidad);
                    datos.add(sCosto);
                    datos.add(sSubtotal);
                } else {
                    datos.add("");
                    datos.add("");
                    datos.add("");
                }

                setSaldoCantidad(0);
                setSaldoTotal(0.0);
                Stack<Inventario> saldos = inv.getSaldo();
                for (Inventario s : saldos) {
                    setSaldoCantidad(getSaldoCantidad() + s.getTotalCantidad());
                    setSaldoTotal(getSaldoTotal() + s.getTotalSubtotal());

                    sTotalCantidad += s.getTotalCantidad() + "\n";
                    sTotalCosto += s.getTotalCosto() + "\n";
                    sTotalSubtotal += s.getTotalSubtotal() + "\n";
                }
                datos.add(sTotalCantidad);
                datos.add(sTotalCosto);
                datos.add(sTotalSubtotal);

                vector.add(datos);

            }

            Object[] datos = new Object[vector.size()];
            for (int k = 0; k < vector.size(); k++) {
                datos[k] = vector.get(k);
            }

            tablaHorizontal.setContenidos(datos);
            tablaHorizontal.setAlineamientos(new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1});
            tablaHorizontal.setTamanos(new int[]{13, 15, 5, 5, 5, 5, 5, 5, 5, 5, 5});
            tablaHorizontal.llenarTabla();
            tablaHorizontal.setPosicion(2);
            tablaHorizontal.setAnchoTabla(100);
            documento.add(tablaHorizontal.getTabla());

            documento.add(espacioBlanco.getElemento());

            documento.add(lineaNormal.getLinea());
        }

    }

    private void totalDetalleDocumento() throws DocumentException {
        PdfPTable tablePanelInicial = new PdfPTable(11);
        tablePanelInicial.setWidthPercentage(100);
        tablePanelInicial.getDefaultCell().setBorder(0);

        titulo4.setTexto("");
        tablePanelInicial.addCell(titulo4.getElemento());

        titulo4.setTexto("");
        tablePanelInicial.addCell(titulo4.getElemento());

        titulo4.setTexto("");
        tablePanelInicial.addCell(titulo4.getElemento());

        PdfPCell cell = new PdfPCell(new Paragraph("Total", FontFactory.getFont("arial", 9, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        cell.setBorder(0);
        tablePanelInicial.addCell(cell);

        cell = new PdfPCell(new Paragraph(String.valueOf(getCantidadEntrada()), FontFactory.getFont("arial", 8, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        cell.setBorder(0);
        tablePanelInicial.addCell(cell);

        titulo4.setTexto("");
        tablePanelInicial.addCell(titulo4.getElemento());

        cell = new PdfPCell(new Paragraph(String.valueOf(getCantidadSalida()), FontFactory.getFont("arial", 8, Font.BOLD)));
        cell.setHorizontalAlignment(2);
        cell.setBorder(0);
        tablePanelInicial.addCell(cell);

        titulo4.setTexto("");
        tablePanelInicial.addCell(titulo4.getElemento());

        titulo4.setTexto("");
        tablePanelInicial.addCell(titulo4.getElemento());

        titulo4.setTexto(String.valueOf(getSaldoCantidad()));
        tablePanelInicial.addCell(titulo4.getElemento());

        cell = new PdfPCell(new Paragraph("$ " + String.valueOf(getSaldoTotal()), FontFactory.getFont("arial", 8, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        cell.setBorder(0);
        tablePanelInicial.addCell(cell);

        documento.add(tablePanelInicial);
        documento.add(espacioBlanco.getElemento());
        documento.add(lineaNormal.getLinea());

    }

    private void cabeceraTabla() throws DocumentException {
        PdfPTable tablePanelInicial = new PdfPTable(4);
        tablePanelInicial.setWidthPercentage(100);
        tablePanelInicial.setWidths(new int[]{28, 15, 15, 15});
        tablePanelInicial.getDefaultCell().setBorder(5);
        tablePanelInicial.setHorizontalAlignment(2);

        PdfPCell cell = new PdfPCell(new Paragraph("", FontFactory.getFont("arial", 10, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        cell.setBorder(0);
        tablePanelInicial.addCell(cell);

        cell = new PdfPCell(new Paragraph("Entrada", FontFactory.getFont("arial", 10, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        tablePanelInicial.addCell(cell);

        cell = new PdfPCell(new Paragraph("Salida", FontFactory.getFont("arial", 10, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        tablePanelInicial.addCell(cell);

        cell = new PdfPCell(new Paragraph("Saldo", FontFactory.getFont("arial", 10, Font.BOLD)));
        cell.setHorizontalAlignment(1);
        tablePanelInicial.addCell(cell);

        documento.add(tablePanelInicial);
    }

    private void comprobarLIFO() throws DocumentException {

        PdfPTable tablePanelInicial = new PdfPTable(1);
        tablePanelInicial.setWidthPercentage(100);
        tablePanelInicial.getDefaultCell().setBorder(0);
        tablePanelInicial.setHorizontalAlignment(2);

        PdfPCell cell = new PdfPCell(new Paragraph("Comprobación", FontFactory.getFont("arial", 10, Font.BOLD)));
        cell.setHorizontalAlignment(2);
        cell.setBorder(0);
        tablePanelInicial.addCell(cell);

        documento.add(espacioBlanco.getElemento());
        documento.add(espacioBlanco.getElemento());

        documento.add(tablePanelInicial);

        documento.add(espacioBlanco.getElemento());
        tablaVertical.limpiar();
        int[] alineamientos = {0, 2};
        int[] tamanos = {20, 17};
        Vector datos = new Vector();
        Vector<String> titulos = new Vector<String>();
        datos.add(0);
        titulos.add("Inv. inicial");
        datos.add(getCantidadEntrada());
        titulos.add("(+) U. entrada");
        datos.add(getCantidadSalida());
        titulos.add("(-) U. salida");
        datos.add(getSaldoCantidad());
        titulos.add("(=) Stock");

        String[] anArray = new String[titulos.size()];
        tablaVertical.setTitulos(titulos.toArray(anArray));
        tablaVertical.setContenidos(datos.toArray());
        tablaVertical.setAlineamientos(alineamientos);
        tablaVertical.llenarTabla(true);
        tablaVertical.setPosicion(2);
        tablaVertical.setAnchoTabla(42);
        tablaVertical.setTamanos(tamanos);

        documento.add(tablaVertical.getTabla());
    }

    public int getCantidadEntrada() {
        return cantidadEntrada;
    }

    public void setCantidadEntrada(int cantidadEntrada) {
        this.cantidadEntrada = cantidadEntrada;
    }

    public int getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(int cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
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
}
