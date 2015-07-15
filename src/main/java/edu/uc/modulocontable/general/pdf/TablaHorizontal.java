package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;

import java.util.ArrayList;

/**
 * Objeto para generar tabla horinzontal.
 *
 * @author juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class TablaHorizontal extends Tabla {

    public TablaHorizontal() {
        super();
    }

    /**
     * @throws DocumentException
     */
    public void llenarTabla() throws DocumentException {
        PdfPCell cell;

        getTabla().setHeaderRows(1);
        if (getTitulos() != null && getTitulos().length > 0) {
            for (int i = 0; i < getTitulos().length; i++) {
                setFont(getFont_arial_negrita());
                setTexto(getTitulos()[i]);
                cell = new PdfPCell(getElemento());
                cell.setHorizontalAlignment(1);
                getTabla().addCell(cell);
            }
        }
        if (getContenidos() != null && getContenidos().length > 0) {
            if (getContenidos()[0] instanceof String) {

                for (Object col : getContenidos()) {
                    System.out.println(col);
                    setFont(getFont_arial_normal());
                    setTexto((String) col);
                    cell = new PdfPCell(getElemento());
                    cell.setHorizontalAlignment(1);
                    cell.setBorder(0);
                    getTabla().addCell(cell);

                }
            } else {
                for (int i = 0; i < getContenidos().length; i++) {
                    int j = 0;

                    if (getContenidos()[i] instanceof ArrayList) {
                        for (String contenido : (ArrayList<String>) getContenidos()[i]) {
                            setFont(getFont_arial_normal());
                            setTexto(contenido);
                            cell = new PdfPCell(getElemento());
                            cell.setHorizontalAlignment(getAlineamientos()[j]);
                            cell.setBorder(0);
                            getTabla().addCell(cell);
                            j++;
                        }

                    }

                }
            }
        }
    }
}
