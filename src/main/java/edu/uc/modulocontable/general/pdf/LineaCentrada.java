package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.IOException;

/**
 * Objeto para generar linea centrada.
 *
 * @author juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class LineaCentrada extends LineaNormal {

    /**
     * Metodo que permite generar una lineanormal.
     *
     * @throws DocumentException
     * @throws IOException
     */
    public LineaCentrada() {
        super();
        setLinea(new LineSeparator(0.5f, 67, BaseColor.BLACK, Element.ALIGN_RIGHT, 3.5f));
    }
    
    public void cambiarTamana(int tamano) {
        getLinea().setPercentage(tamano);
    }
    
}
