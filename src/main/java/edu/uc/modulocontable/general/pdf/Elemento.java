package edu.uc.modulocontable.general.pdf;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Objeto para definir el elemento base para la creacion de reportes del SRI.
 *
 * @author juan pablo japa juan.japal@hotmail.com
 *
 */
public class Elemento {
    private String texto;
    private Font font;
    private BaseFont bf;
    private Font font_arial_normal;
    private Font font_arial_negrita;


    /**Metodo para crear un elemento.
     *
     * @throws DocumentException
     * @throws IOException
     */
    public Elemento() {
        super();
        try {
            inicializarfuntes();
            limpiar();
        } catch (Exception e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.toString());
        }

    }

    /**Metodo para inicializar las variables de la fuentes.
     *
     * @throws DocumentException
     * @throws IOException
     */
    public void inicializarfuntes() throws DocumentException, IOException {
      

        font_arial_normal = FontFactory.getFont("arial", 8, Font.NORMAL);
        font_arial_negrita = FontFactory.getFont("arial", 8, Font.BOLD);
    }

    /**Metodo que permite restablecer el el tipo de letra.
     *
     */
    public void limpiar() {
        setTexto(null);
        setFont(font_arial_normal);
    }

    /**Metodo que que devuelve un Paragraph con el texto dado.
     * @return Paragraph
     */
    public Paragraph getElemento() {
        return new Paragraph(getTexto(), font);
    }

    /**Metodo que que devuelve un Paragraph con el texto dado con una determinado alineamiento.
     * @param aling
     * @return Paragraph
     */
    public Paragraph getElemento(int aling) {
        Paragraph parrafo = new Paragraph(texto, font);
        parrafo.setAlignment(Element.ALIGN_CENTER);
        return parrafo;
    }

    /**Metodo que retorna un String.
     * @return
     */
    public String getTexto() {
        return (texto == null) ? "" : texto.trim();
    }

    /**Metodo Permite ingresar un texto al Elemento.
     * @param texto
     */
    public void setTexto(String texto) {
        if (texto == null)
            this.texto = "";
        else
            this.texto = texto;
    }

    /**Metodo retorna en font.
     * @return font
     */
    public Font getFont() {
        return font;
    }

    /**Metodo que permite ingresar un Font especifico.
     * @param font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     *
     * @return
     */
    public BaseFont getBf() {
        return bf;
    }

    /**
     *
     * @param bf
     */
    public void setBf(BaseFont bf) {
        this.bf = bf;
    }


    /**
     *
     * @return
     */
    public Font getFont_arial_normal() {
        return font_arial_normal;
    }

    /**
     *
     * @param font_arial_normal
     */
    public void setFont_arial_normal(Font font_arial_normal) {
        this.font_arial_normal = font_arial_normal;
    }

    /**
     *
     * @return
     */
    public Font getFont_arial_negrita() {
        return font_arial_negrita;
    }

    /**
     *
     * @param font_arial_negrita
     */
    public void setFont_arial_negrita(Font font_arial_negrita) {
        this.font_arial_negrita = font_arial_negrita;
    }
}
