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
public class Titulo3 extends Elemento {

    /**
     * @throws DocumentException
     * @throws IOException
     */
    public Titulo3() {
        super();
        limpiar();
        setFont(new Font(getBf(), 9, Font.BOLD));
    }
    public void getElementoRojo() {
        setFont(new Font(getFont_arial_negrita().getBaseFont(), 9, Font.BOLD,BaseColor.RED));
    }
    public void getElementoNegro() {
        setFont(new Font(getFont_arial_negrita().getBaseFont(), 11, Font.BOLD,BaseColor.BLACK));
    }
    
      public void getElementoVerde() {
        setFont(new Font(getFont_arial_negrita().getBaseFont(), 11, Font.BOLD,new BaseColor(29,127, 32)));
    }
}
