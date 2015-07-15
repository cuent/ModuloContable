package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;

import java.io.IOException;

/** Objeto para generar codigo de barra.
 *
 * @author juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class Titulo4 extends Elemento {

    /**
     * @throws DocumentException
     * @throws IOException
     */
    public Titulo4() {
        super();
        limpiar();
        setFont(new Font(getFont_arial_negrita().getBaseFont(), 8, Font.BOLD));
    }
}
