package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;

import java.io.IOException;

/** Objeto para generar codigo de barra.
 *
 * @author juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class Titulo1 extends Elemento {

    /**
     * @throws DocumentException
     * @throws IOException
     */
    public Titulo1() {
        super();
        limpiar();
        setFont(new Font(getBf(), 13, Font.BOLD));
    }

    public void getElementoRojo() {
        setFont(new Font(getFont_arial_negrita().getBaseFont(), 13, Font.BOLD, BaseColor.RED));
    }
}
