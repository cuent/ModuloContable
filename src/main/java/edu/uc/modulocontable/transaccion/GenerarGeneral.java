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
public class GenerarGeneral {

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
    private BigDecimal totalActivosCorrientes = new BigDecimal(BigInteger.ZERO);
    private BigDecimal totalPasivos = new BigDecimal(BigInteger.ZERO);
    private BigDecimal totalPatrimonio = new BigDecimal(BigInteger.ZERO);
    private BigDecimal totalActNoCorrientes = new BigDecimal(BigInteger.ZERO);
    private BigDecimal totalActivos = new BigDecimal(BigInteger.ZERO);
    private BigDecimal ingOperacionales = new BigDecimal(BigInteger.ZERO);
    private BigDecimal gastOperacionales = new BigDecimal(BigInteger.ZERO);
    private BigDecimal utiOperacional = new BigDecimal(BigInteger.ZERO);
    private BigDecimal otros = new BigDecimal(BigInteger.ZERO);
    private BigDecimal utiEjercicio = new BigDecimal(BigInteger.ZERO);

    public GenerarGeneral() {
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

    public void generarFactura(List<Cuenta> activosCorr, List<Cuenta> activosNoCorr, List<Cuenta> pasivos, List<Cuenta> patrimonio, List<Cuenta> cuentaIng, List<Cuenta> cuentaGas, List<Cuenta> cuentaOtro, String ruta) {
        documento = new Documento(ruta);
        documento.setPageSize(PageSize.A4.rotate());
        PdfWriter writer = documento.getWriter();
        writer.setPageEvent(encabezado);
        documento.setMargins(40, 30, 30, 40);
        informacionpiePagina();
        documento.open();
        try {
            cabecera();
            detalleDocumento1(activosCorr);
            detalleDocumento2(activosNoCorr);
            totalDocumento();
            detalleDocumento3(pasivos);
            detalleDocumento4(patrimonio, cuentaIng, cuentaGas, cuentaOtro);
            totalDocumento1();
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

        titulo2.setTexto("BALANCE GENERAL");
        titulo2.getElementoRojo();
        tablePanelDerecho.addCell(titulo2.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablePanelDerecho.addCell(espacioBlanco.getElemento());

        tablePanelInicial.addCell(tablePanelDerecho);
        documento.add(tablePanelInicial);
    }

    private void detalleDocumento1(List<Cuenta> activosCorr) throws DocumentException {

        documento.add(espacioBlanco.getElemento());

        tablaHorizontal.setColumnas(5);

        Vector vector = new Vector();

        List datos1 = new ArrayList();
        datos1.add("");
        datos1.add("CORRIENTE");
        datos1.add("");
        datos1.add("");
        datos1.add("");
        vector.add(datos1);

        for (Cuenta inv : activosCorr) {

            List datos = new ArrayList();

            datos.add("");
            datos.add("");
            datos.add(inv.getDescripcion());
            datos.add("$" + String.valueOf(inv.getDiferencia()));
            setTotalActivosCorrientes(getTotalActivosCorrientes().add(inv.getDiferencia()));
            datos.add("");
            vector.add(datos);
        }

        Object[] datos = new Object[vector.size()];
        for (int k = 0; k < vector.size(); k++) {
            datos[k] = vector.get(k);
        }

        tablaHorizontal.setTitulos("ACTIVO", "", "", "", "$" + getTotalActivosCorrientes());
        tablaHorizontal.setContenidos(datos);
        tablaHorizontal.setAlineamientos(new int[]{0, 0, 1, 1, 1});
        tablaHorizontal.setTamanos(new int[]{5, 5, 5, 5, 5});
        tablaHorizontal.llenarTabla();
        tablaHorizontal.setPosicion(2);
        tablaHorizontal.setAnchoTabla(100);
        documento.add(tablaHorizontal.getTabla());
        tablaHorizontal.limpiar();

    }

    private void detalleDocumento2(List<Cuenta> activosNoCorr) throws DocumentException {

        tablaHorizontal.setColumnas(5);

        Vector vector = new Vector();

        for (Cuenta inv : activosNoCorr) {

            List datos = new ArrayList();

            datos.add("");
            datos.add("");
            datos.add(inv.getDescripcion());
            datos.add("$" + String.valueOf(inv.getDiferencia()));
            setTotalActNoCorrientes(getTotalActNoCorrientes().add(inv.getDiferencia()));
            datos.add("");
            vector.add(datos);
        }

        List datos1 = new ArrayList();
        datos1.add("");
        datos1.add("NO CORRIENTE");
        datos1.add("");
        datos1.add("");
        datos1.add("");
        vector.add(0, datos1);

        Object[] datos = new Object[vector.size()];
        for (int k = 0; k < vector.size(); k++) {
            datos[k] = vector.get(k);
        }

        tablaHorizontal.setTitulos("", "", "", "", "");
        tablaHorizontal.setContenidos(datos);
        tablaHorizontal.setAlineamientos(new int[]{0, 0, 1, 1, 1});
        tablaHorizontal.setTamanos(new int[]{5, 5, 5, 5, 5});
        tablaHorizontal.llenarTabla();
        tablaHorizontal.setPosicion(2);
        tablaHorizontal.setAnchoTabla(100);
        documento.add(tablaHorizontal.getTabla());
        tablaHorizontal.limpiar();

    }

    private void totalDocumento() throws DocumentException {

        tablaHorizontal.setColumnas(5);

        Vector vector = new Vector();

        List datos = new ArrayList();
        datos.add("");
        datos.add("");
        datos.add("");
        datos.add("");
        setTotalActivos(getTotalActivosCorrientes().add(getTotalActNoCorrientes()));
        datos.add("");
        vector.add(datos);

        Object[] datosV = new Object[vector.size()];
        for (int k = 0; k < vector.size(); k++) {
            datosV[k] = vector.get(k);
        }

        tablaHorizontal.setTitulos("TOTAL ACTIVO", "", "", "", "$" + getTotalActivos());
        tablaHorizontal.setContenidos(datosV);
        tablaHorizontal.setAlineamientos(new int[]{0, 0, 1, 1, 1});
        tablaHorizontal.setTamanos(new int[]{5, 5, 5, 5, 5});
        tablaHorizontal.llenarTabla();
        tablaHorizontal.setPosicion(2);
        tablaHorizontal.setAnchoTabla(100);
        documento.add(tablaHorizontal.getTabla());
        documento.add(espacioBlanco.getElemento());
        tablaHorizontal.limpiar();

    }

    private void detalleDocumento3(List<Cuenta> pasivos) throws DocumentException {

        documento.add(espacioBlanco.getElemento());

        tablaHorizontal.setColumnas(5);

        Vector vector = new Vector();

        if (pasivos.isEmpty()) {
            List datos = new ArrayList();

            datos.add("");
            datos.add("");
            datos.add("");
            datos.add("");
            datos.add("");
            vector.add(datos);
        }

        for (Cuenta inv : pasivos) {

            List datos = new ArrayList();

            datos.add("");
            datos.add("");
            datos.add("" + inv.getDescripcion());
            datos.add("$" + String.valueOf(inv.getDiferencia()));
            setTotalPasivos(getTotalPasivos().add(inv.getDiferencia()));
            datos.add("");
            vector.add(datos);
        }

        Object[] datos = new Object[vector.size()];
        for (int k = 0; k < vector.size(); k++) {
            datos[k] = vector.get(k);
        }

        tablaHorizontal.setTitulos("PASIVO", "", "", "", "$" + getTotalPasivos());
        tablaHorizontal.setContenidos(datos);
        tablaHorizontal.setAlineamientos(new int[]{0, 0, 1, 1, 1});
        tablaHorizontal.setTamanos(new int[]{5, 5, 5, 5, 5});
        tablaHorizontal.llenarTabla();
        tablaHorizontal.setPosicion(2);
        tablaHorizontal.setAnchoTabla(100);
        documento.add(tablaHorizontal.getTabla());
        tablaHorizontal.limpiar();
    }

    private void detalleDocumento4(List<Cuenta> patrimonio, List<Cuenta> cuentaIng, List<Cuenta> cuentaGas, List<Cuenta> cuentaOtro) throws DocumentException {
        ingresosOperacionales(cuentaIng);
        gastosOperacionales(cuentaGas);
        utiOperacional();
        otros(cuentaOtro);
        utiEjercicio();

        documento.add(espacioBlanco.getElemento());
        tablaHorizontal.setColumnas(5);

        Vector vector = new Vector();

        for (Cuenta inv : patrimonio) {

            List datos = new ArrayList();

            datos.add("");
            datos.add("");
            datos.add(inv.getDescripcion());
            datos.add("$" + String.valueOf(inv.getDiferencia()));
            setTotalPatrimonio(getTotalPatrimonio().add(inv.getDiferencia()));
            datos.add("");
            vector.add(datos);
        }

        List datos1 = new ArrayList();

        datos1.add("");
        datos1.add("");
        datos1.add("Utilidad");
        datos1.add("$" + String.valueOf(getUtiEjercicio()));
        setTotalPatrimonio(getTotalPatrimonio().add(getUtiEjercicio()));
        datos1.add("");
        vector.add(datos1);

        Object[] datos = new Object[vector.size()];
        for (int k = 0; k < vector.size(); k++) {
            datos[k] = vector.get(k);
        }

        tablaHorizontal.setTitulos("PATRIMONIO", "", "", "", "$" + getTotalPatrimonio());
        tablaHorizontal.setContenidos(datos);
        tablaHorizontal.setAlineamientos(new int[]{0, 0, 1, 1, 1});
        tablaHorizontal.setTamanos(new int[]{5, 5, 5, 5, 5});
        tablaHorizontal.llenarTabla();
        tablaHorizontal.setPosicion(2);
        tablaHorizontal.setAnchoTabla(100);
        documento.add(tablaHorizontal.getTabla());
        tablaHorizontal.limpiar();
    }

    private void totalDocumento1() throws DocumentException {

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

        tablaHorizontal.setTitulos("TOTAL PASIVO Y PATRIMONIO", "", "", "", "$" + getTotalPatrimonio().add(getTotalPasivos()));
        tablaHorizontal.setContenidos(datosV);
        tablaHorizontal.setAlineamientos(new int[]{0, 0, 1, 1, 1});
        tablaHorizontal.setTamanos(new int[]{5, 5, 5, 5, 5});
        tablaHorizontal.llenarTabla();
        tablaHorizontal.setPosicion(2);
        tablaHorizontal.setAnchoTabla(100);
        documento.add(tablaHorizontal.getTabla());
        documento.add(espacioBlanco.getElemento());
        tablaHorizontal.limpiar();

    }

    private void ingresosOperacionales(List<Cuenta> ingOper) throws DocumentException {
        if (!ingOper.isEmpty()) {
            for (Cuenta inv : ingOper) {
                if (inv.getNumcuenta().startsWith("4.1.")) {
                    setIngOperacionales(getIngOperacionales().add(inv.getDiferencia()));
                }
                if (inv.getNumcuenta().startsWith("5.1.")) {
                    setIngOperacionales(getIngOperacionales().subtract(inv.getDiferencia()));
                }
            }
        }

    }

    private void gastosOperacionales(List<Cuenta> gasOper) throws DocumentException {

        if (!gasOper.isEmpty()) {
            for (Cuenta inv : gasOper) {
                setGastOperacionales(getGastOperacionales().add(inv.getDiferencia()));
            }
        }
    }

    private void utiOperacional() throws DocumentException {
        setUtiOperacional(getIngOperacionales().subtract(getGastOperacionales()));
    }

    private void otros(List<Cuenta> otro) throws DocumentException {
        if (!otro.isEmpty()) {
            for (Cuenta inv : otro) {
                setOtros(getOtros().add(inv.getDiferencia()));
            }
        }
    }

    private void utiEjercicio() throws DocumentException {
        setUtiEjercicio(getUtiOperacional().subtract(getOtros()));
    }

    public BigDecimal getIngOperacionales() {
        return ingOperacionales;
    }

    public void setIngOperacionales(BigDecimal ingOperacionales) {
        this.ingOperacionales = ingOperacionales;
    }

    public BigDecimal getGastOperacionales() {
        return gastOperacionales;
    }

    public void setGastOperacionales(BigDecimal gastOperacionales) {
        this.gastOperacionales = gastOperacionales;
    }

    public BigDecimal getUtiOperacional() {
        return utiOperacional;
    }

    public void setUtiOperacional(BigDecimal utiOperacional) {
        this.utiOperacional = utiOperacional;
    }

    public BigDecimal getOtros() {
        return otros;
    }

    public void setOtros(BigDecimal otros) {
        this.otros = otros;
    }

    public BigDecimal getUtiEjercicio() {
        return utiEjercicio;
    }

    public void setUtiEjercicio(BigDecimal utiEjercicio) {
        this.utiEjercicio = utiEjercicio;
    }

    public BigDecimal getTotalActivosCorrientes() {
        return totalActivosCorrientes;
    }

    public void setTotalActivosCorrientes(BigDecimal totalActivosCorrientes) {
        this.totalActivosCorrientes = totalActivosCorrientes;
    }

    public BigDecimal getTotalPasivos() {
        return totalPasivos;
    }

    public void setTotalPasivos(BigDecimal totalPasivos) {
        this.totalPasivos = totalPasivos;
    }

    public BigDecimal getTotalPatrimonio() {
        return totalPatrimonio;
    }

    public void setTotalPatrimonio(BigDecimal totalPatrimonio) {
        this.totalPatrimonio = totalPatrimonio;
    }

    public BigDecimal getTotalActNoCorrientes() {
        return totalActNoCorrientes;
    }

    public void setTotalActNoCorrientes(BigDecimal totalActNoCorrientes) {
        this.totalActNoCorrientes = totalActNoCorrientes;
    }

    public BigDecimal getTotalActivos() {
        return totalActivos;
    }

    public void setTotalActivos(BigDecimal totalActivos) {
        this.totalActivos = totalActivos;
    }

}
