package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;

public class Tabla extends Elemento {
    private PdfPTable tabla;
    private int columnas;
    private int[] alineamientos;
    private int[] tamanos;
    private int posicion = 0;
    private String[] titulos;
    private Object[] contenidos;

    /**
     * @throws DocumentException
     * @throws IOException
     */
    public Tabla() {
        super();
        limpiar();
    }

    public void limpiar() {
        columnas = 1;
        tabla = new PdfPTable(columnas);
        setTitulos(new String[columnas]);
        setContenidos(new Object[columnas]);
    }

    /**
     * @param columnas
     */
    public Tabla(int columnas) {
        super();
        limpiar();
        this.columnas = columnas;
        tabla = new PdfPTable(columnas);
    }


    /**Constructor que permite ingresar datos ala tabla.
     * @param alineamientos
     * @param tamanos
     * @throws DocumentException
     * @throws IOException
     */
    public Tabla(int[] alineamientos, int[] tamanos) throws DocumentException, IOException {
        this.alineamientos = alineamientos;
        this.tamanos = tamanos;
    }

    /**Metodo que retorna el numero de columas utilizado.
     * @return
     */
    public int getColumnas() {
        return columnas;
    }


    /**
     * @return
     */
    public int[] getAlineamientos() {
        return alineamientos;
    }

    /**
     * @param alineamientos
     */
    public void setAlineamientos(int[] alineamientos) {
        this.alineamientos = alineamientos;
    }

    /**
     * @return
     */
    public int[] getTamanos() {
        return tamanos;
    }

    /**Metodo que permite definir las achuras de cada columna de la tabla.
     * @param tamanos
     * @throws DocumentException
     */
    public void setTamanos(int[] tamanos) throws DocumentException {
        this.tamanos = tamanos;
        tabla.setWidths(tamanos);
    }

    /**metodo que permite obtener la tabla.
     * @return
     */
    public PdfPTable getTabla() {
        return tabla;
    }

    /**
     * @param tabla
     */
    public void setTabla(PdfPTable tabla) {
        this.tabla = tabla;
    }

    /**Metodo que permite retornar la posicion de que esta tomando la tabla.
     * @return posicion
     */
    public int getPosicion() {
        return posicion;
    }

    /**Metodo que permite definir la posicion con la cual se pretende que tome la tabla.
     * @param posicion
     */
    public void setPosicion(int posicion) {
        tabla.setHorizontalAlignment(posicion);
    }

    /**Metodo que permite definir la anchura de la tabla.
     * @param ancho
     */
    public void setAnchoTabla(int ancho) {
        tabla.setWidthPercentage(ancho);
    }

    /**
     * @param columnas
     */
    public void setColumnas(int columnas) {
        tabla = new PdfPTable(columnas);
    }

    /**
     * @return
     */
    public String[] getTitulos() {
        return titulos;
    }

    /**Metodo que permite agragar titulos.
     * @param params
     */
    @SuppressWarnings("cast")

    public void setTitulos(String... params) {
        this.titulos = (String[]) params;
    }
    
    /**Metodo permite retonar el contenido.
     * @return
     */
    public Object[] getContenidos() {
        return contenidos;
    }

    /**Metodo que permite ingresar el contenido.
     * @param contenidos
     */
    public void setContenidos(Object[] contenidos) {
        this.contenidos = contenidos;
    }
}
