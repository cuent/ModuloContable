package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.IOException;

/** Objeto para generar codigo de barra.
 *
 * @author juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class LineaNormal extends Elemento {

    private LineSeparator linea;

    /**Metodo que permite ingresar una linea.
     *
     * @throws DocumentException
     * @throws IOException
     */
    public LineaNormal()  {
        super();
        linea = new LineSeparator(0.5f,100, null, Element.ALIGN_RIGHT, 2);
    }

    /**Metodo Permite ingresar una LineSeparator ya definida.
     * @param linea
     */
    public void setLinea(LineSeparator linea) {
        this.linea = linea;
    }

    /**Metodo que devuelve un LineSeparator.
     * @return linea
     */
    public LineSeparator getLinea() {
        return linea;
    }
}
