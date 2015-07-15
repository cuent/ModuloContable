package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;

import java.io.IOException;

/**
 * Objeto para generar codigo de barra.
 *
 * @author juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class Titulo2 extends Elemento {

    /**
     * @throws DocumentException
     * @throws IOException
     */
    public Titulo2() {
        super();
        limpiar();
        setFont(new Font(getBf(), 10, Font.BOLD));
    }

    public void getElementoRojo() {
        setFont(new Font(getFont_arial_negrita().getBaseFont(), 10, Font.BOLD,new BaseColor(29,127, 32)));
    }

    public void CambiarPosicion(float tamano) {
        getFont().setSize(tamano);
    }

    public void getElementoNegro() {
        setFont(new Font(getFont_arial_negrita().getBaseFont(), 10, Font.BOLD, BaseColor.BLACK));
    }
    public void getElementoVerde() {
        setFont(new Font(getFont_arial_negrita().getBaseFont(), 11, Font.BOLD,new BaseColor(29,127, 32)));
    }
}
