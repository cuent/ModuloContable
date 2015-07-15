package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Objeto para generar tabla horinzontal.
 *
 * @author juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class TablaVertical extends Tabla {

    /**
     * Constructor que permite inicializar la tabla.
     */
    public TablaVertical() {
        super(2);
    }

    /**
     * Metodo que genera la Tabla de forma Vertical.
     *
     * @param f si f es true entonces dibujara un linea al final de la celda
     * @throws DocumentException
     *
     */
  

    public void llenarTabla(boolean f) throws DocumentException {
        PdfPCell cell;
      
        for (int i = 0; i < getTitulos().length; i++) {
            setFont(getFont_arial_negrita());
            setTexto(getTitulos()[i]);
            cell = new PdfPCell(getElemento());
            cell.setHorizontalAlignment(getAlineamientos()[0]);
            cell.setBorder(0);
            if (i == getTitulos().length - 2 && f) {
                cell.setBorderWidthBottom(1);
            }
            getTabla().addCell(cell);
            setFont(getFont_arial_normal());
            setTexto((getContenidos()[i] == null) ? "" : getContenidos()[i].toString().trim());
            cell = new PdfPCell(getElemento());
            cell.setHorizontalAlignment(getAlineamientos()[1]);
            cell.setBorder(0);
            if (i == getContenidos().length - 2 && f) {
                cell.setBorderWidthBottom(1);
            }
            getTabla().addCell(cell);
        }
    }

    /**
     * Metodo que permite limpiar.
     */
    public void limpiar() {
        super.limpiar();
        setTabla(new PdfPTable(2));
    }

    /**
     * @param columnas
     */
    public void limpiar(int columnas) {
        super.limpiar();
        setTabla(new PdfPTable(columnas));
    }
}
