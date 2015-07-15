package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;

import java.io.IOException;

/** Objeto para generar codigo de barra.
 *
 * @author juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class CodigoBarra extends Elemento {
    private Image imagen;
    private Barcode128 codigoBarra;

    /**Metodo para para crear el codigo de Barra.
     *
     * @throws DocumentException
     * @throws IOException
     */
    public CodigoBarra(){
        super();
        codigoBarra = new Barcode128();
    }

    /**Metodo que genera un codigo en barra en forma de uma imagen.
     * @param pdfContemtByte
     * @return
     */
    public Image getCodigoBarra(PdfContentByte pdfContemtByte) {
        codigoBarra.setCode(getTexto());
        imagen = codigoBarra.createImageWithBarcode(pdfContemtByte, null, null);
        imagen.setAbsolutePosition(60, 300);
        return imagen;
    }

    /**Metodo que permite ingresar un codigo128 ya establecido.
     * @param codigoBarra
     */
    public void setCodigoBarra(Barcode128 codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    /**Metodo que permite ingresar la posicion del codigo de barra, en la posicion de X.
     * @param PosX
     * @param PosY
     */
    public void SetPosicionY(int PosX, int PosY) {
        imagen.setAbsolutePosition(PosX, PosY);
    }
}
