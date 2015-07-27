/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.general;

import com.itextpdf.text.DocumentException;
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
import edu.uc.modulocontable.modelo2.DetalleFacturav;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Pablo
 */
public class GenerarFacturaVentasPDF {

    private String numeroAutorizacion = "8901234567891123456789212345678931234567894";
    private String fechaAutorizacion = "01/08/2014 21:11:01";
    private String ambiente = "Pendiente";
    private String emision = "Normal";
    private String claveAcceso = "12345678901234567890123456789012345678901234567890";
    private String direccionLogotipo;

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

    public GenerarFacturaVentasPDF() {
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

    public void generarFactura(CabeceraFacturav cabezera, String ruta) {
        documento = new Documento(ruta);
        PdfWriter writer = documento.getWriter();
        writer.setPageEvent(encabezado);
        documento.setMargins(40, 30, 30, 40);
        informacionpiePaguina();
        documento.open();
        try {
            cabezera(cabezera);
        } catch (DocumentException ex) {
            Logger.getLogger(GenerarFacturaVentasPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            informaicionCliente(cabezera);
        } catch (Exception e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.toString() + " informacion Cliente mal");
        }

        try {
            detalleDocumento(cabezera);
        } catch (Exception e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.toString() + " detalle documento");
        }

        try {
            totalDetalleDocumento(cabezera);

        } catch (Exception e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.toString() + " total detalle documento");
        }

        try {
            informacionAdicional();
        } catch (Exception e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.toString() + " informacion adicional");
        }

        documento.close();

    }

    private void informaicionCliente(CabeceraFacturav cabezera) throws DocumentException {
        titulo2.getElementoNegro();
        titulo2.setTexto("INFORMACION CONTRIBUYENTE");
        titulo2.CambiarPosicion((float) 9.5);
        documento.add(titulo2.getElemento());
        lineaCentrada.cambiarTamana(70);
        documento.add(lineaCentrada.getLinea());

        tablaVertical.limpiar(4);
        // System.out.println(getBus().getInfoFactura().getGuiaRemision());

        if (cabezera.getCodigoCliente().getTipoIdentificacion().equalsIgnoreCase("CONSUMIDOR FINAL")) {
            tablaVertical.setTitulos("Razón Social:", "Fecha Emisión:");
            Object[] datos = {cabezera.getCodigoCliente().getTipoIdentificacion(), cabezera.getFecha().toString()};
            tablaVertical.setContenidos(datos);
            tablaVertical.setAlineamientos(new int[]{0, 0});
            tablaVertical.llenarTabla(false);
            tablaVertical.setTamanos(new int[]{16, 40, 14, 35});
            tablaVertical.setPosicion(2);
            tablaVertical.setAnchoTabla(100);
            documento.add(tablaVertical.getTabla());
        } else {

            tablaVertical.setTitulos("RUC/CI/Pasaporte:", "Teléfono:", "Razón Social:", "Fecha Emisión:", "Direción:",
                    "");
            Object[] datos = {
                cabezera.getCodigoCliente().getIdentificacion(), cabezera.getCodigoCliente().getTelefono(),
                cabezera.getCodigoCliente().getNombre() + " " + cabezera.getCodigoCliente().getApellido(), cabezera.getFecha().toString(),
                cabezera.getCodigoCliente().getDireccion(), ""
            };
            tablaVertical.setContenidos(datos);

            tablaVertical.setAlineamientos(new int[]{0, 0, 0, 0, 0,});
            tablaVertical.llenarTabla(false);
            tablaVertical.setTamanos(new int[]{16, 40, 14, 35});
            tablaVertical.setPosicion(2);
            tablaVertical.setAnchoTabla(100);
            documento.add(tablaVertical.getTabla());
        }
    }

    private void detalleDocumento(CabeceraFacturav bus) throws DocumentException {
        System.out.println("entro detalle");
        int i = 1;

        if (bus.getDetalleFacturavList() != null && bus.getDetalleFacturavList().size() > 0) {
            System.out.println("lliata llena");
            espacionBlanco(1);
            tablaHorizontal.setColumnas(5);
            tablaHorizontal.setTitulos("Código", "Descripción", "Cant.", "P. Unitario", "Total");

            Vector vector = new Vector();

            for (DetalleFacturav a : bus.getDetalleFacturavList()) {
                List datos1 = new ArrayList();

                datos1.add(String.valueOf(a.getProducto().getCodigoProducto()));

                datos1.add(a.getProducto().getNombre());
                datos1.add(String.valueOf(a.getCantidad()));
                datos1.add(String.valueOf(a.getPrecioUnitario()));
                datos1.add(cambiarFormato(a.getTotal()));
                // System.out.println();

                vector.add(datos1);

            }

            Object[] datos = new Object[vector.size()];
            for (int k = 0; k < vector.size(); k++) {
                datos[k] = vector.get(k);
            }

            tablaHorizontal.setContenidos(datos);
            tablaHorizontal.setAlineamientos(new int[]{0, 0, 2, 2, 2});
            tablaHorizontal.setTamanos(new int[]{10, 40, 10, 15, 15});
            tablaHorizontal.llenarTabla();
            tablaHorizontal.setPosicion(2);
            tablaHorizontal.setAnchoTabla(100);
            documento.add(tablaHorizontal.getTabla());

            espacionBlanco(1);
            documento.add(lineaNormal.getLinea());

        }

    }

    private void totalDetalleDocumento(CabeceraFacturav cabezera) throws DocumentException {
        tablaVertical.limpiar();

        int[] alineamientos = {0, 2};
        int[] tamanos = {35, 17};
        Vector datos = new Vector();
        Vector<String> titulos = new Vector<String>();
        datos.add(cabezera.getSubtotalBaseIva());
        titulos.add("SubTotal 12%:");
        datos.add(cabezera.getSubtotalBase0());
        titulos.add("SubTotal 0%:");
        datos.add(cabezera.getDescuento());
        titulos.add("Descuento:");
        datos.add(cabezera.getIva());
        titulos.add("IVA:");
        datos.add(cabezera.getTotal());
        titulos.add("Valor Total:");

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

    private void informacionAdicional() {

    }

    public String cambiarFormato(Object obj) {
        NumberFormat nf = NumberFormat.getInstance();
        nf = NumberFormat.getCurrencyInstance(Locale.US);
        String numero = nf.format(obj).trim();
        return numero.replace('$', ' ');
    }

    private void espacionBlanco(int espacios) {
        try {
            for (int i = 0; i < espacios; i++) {
                documento.add(espacioBlanco.getElemento());
            }
        } catch (DocumentException e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.toString());
        }
    }

    private void cabezera(CabeceraFacturav c) throws DocumentException {
        Archivo a = new Archivo();
        Empresa empresa = a.obtieneContenidoArchivo().get(0);

        PdfPTable tablePanelInicial = new PdfPTable(2);
        tablePanelInicial.setWidthPercentage(100);
        tablePanelInicial.getDefaultCell().setBorder(0);

        PdfPTable tablePanelIzquierdo = new PdfPTable(1);
        tablePanelIzquierdo.setWidthPercentage(100);
        tablePanelIzquierdo.getDefaultCell().setBorder(0);

        PdfPTable tablePanelDerecho = new PdfPTable(1);
        tablePanelDerecho.setWidthPercentage(100);
        tablePanelDerecho.getDefaultCell().setBorder(0);

        imagen.setDir("/Users/cuent/Downloads/abc-logo.jpg");
        tablePanelIzquierdo.addCell(imagen.getImage());

        titulo1.setTexto(empresa.getNombre());
        tablePanelIzquierdo.addCell(titulo1.getElemento());

//        tablaVertical.limpiar();
//        tablaVertical.setTitulos("Dir. Matriz:", "Dir. Sucursal:");
//        Object[] datos1 = { bus.getInfoTributaria().getDirMatriz(), bus.getInfoFactura().getDirEstablecimiento() };
//        tablaVertical.setContenidos(datos1);
//        tablaVertical.setAlineamientos(new int[] { 0, 0 });
//        tablaVertical.llenarTabla(false);
//        tablaVertical.setPosicion(2);
//        tablaVertical.setTamanos(new int[] { 25, 75 });
//        tablaVertical.setAnchoTabla(100);
//        tablePanelIzquierdo.addCell(tablaVertical.getTabla());
//        tablaVertical.limpiar();
//        tablaVertical.setTitulos("Contribuyente Especial Nro.:", "Obligado a llever contabilidad:");
//        Object[] datos2 = {
//            bus.getInfoFactura().getContribuyenteEspecial(), bus.getInfoFactura().getObligadoContabilidad() };
//        tablaVertical.setContenidos(datos2);
//        tablaVertical.setAlineamientos(new int[] { 0, 0 });
//        tablaVertical.llenarTabla(false);
//        tablaVertical.setPosicion(2);
//        tablaVertical.setTamanos(new int[] { 60, 40 });
//        tablaVertical.setAnchoTabla(100);
//        tablePanelIzquierdo.addCell(tablaVertical.getTabla());
        tablePanelInicial.addCell(tablePanelIzquierdo);

        titulo2.setTexto("RUC: " + empresa.getRuc());
        tablePanelDerecho.addCell(titulo2.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        titulo2.setTexto("FACTURA");
        titulo2.getElementoRojo();
        tablePanelDerecho.addCell(titulo2.getElemento());

        titulo2.setTexto("No.: " + c.getEstablecimiento() + "-" + c.getPtoEmision()
                + "-" + c.getNumeroFactura());
        titulo2.getElementoRojo();
        tablePanelDerecho.addCell(titulo2.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablaVertical.limpiar(1);
        int[] tamanos2 = {50};
        int[] alineamientos = {0, 0};
        tablaVertical.setTamanos(tamanos2);
        tablaVertical.setTitulos("Número de Autorización:");
        Object[] datos10 = {c.getAutorizacionSri().getNumeroAutorizacion()};
        tablaVertical.setContenidos(datos10);
        tablaVertical.setAlineamientos(alineamientos);
        tablaVertical.llenarTabla(false);
        tablaVertical.setPosicion(0);
        tablaVertical.setAnchoTabla(65);
        tablePanelDerecho.addCell(tablaVertical.getTabla());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablaVertical.limpiar();
        Object[] datos3 = {empresa.getDireccion(), empresa.getTelefono(), c.getFecha().toString(), ambiente, emision};
        tablaVertical.setTitulos("Dirección:", "Télefono:", "Fecha y Hora de Autorización:", "Ambiente:", "Emision:");
        tablaVertical.setContenidos(datos3);
        tablaVertical.llenarTabla(false);
        tablaVertical.setPosicion(0);
        tablaVertical.setAnchoTabla(65);
        tablePanelDerecho.addCell(tablaVertical.getTabla());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());
        tablePanelDerecho.addCell(espacioBlanco.getElemento());

//        titulo3.setTexto("Clave de Acceso:");
//        titulo3.getElementoNegro();
//        tablePanelDerecho.addCell(titulo3.getElemento());
        tablePanelDerecho.addCell(espacioBlanco.getElemento());

//        codigoBarra.setTexto(getClaveAcceso());
//        tablePanelDerecho.addCell(codigoBarra.getCodigoBarra(documento.getContentbyte()));
        tablePanelInicial.addCell(tablePanelDerecho);
        documento.add(tablePanelInicial);
    }

    public void informacionpiePaguina() {
        encabezado.setTextofondo("");
        encabezado.generarMarcaAgua();
        java.util.Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        encabezado.setEncabezado("Creado el: " + formateador.format(ahora));
        documento.getWriter().setPageEvent(encabezado);
    }
}
