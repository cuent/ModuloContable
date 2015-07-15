package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import java.util.logging.Level;
import java.util.logging.Logger;

/** Objeto para generar el documento PDF.
 *
 * @author ovelez omar.velez@sicsas.com
 *
 */
public class Documento extends Document {

    private PdfWriter writer;
    private PdfContentByte contentbyte;

    /**Metodo para crear el objeto.
     *
     * @param fileName
     */
    public Documento(String fileName) {
        super(PageSize.A4, 36, 36, 36, 36);
        try {
            writer = PdfWriter.getInstance(this, new FileOutputStream(fileName));
        } catch (Exception e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.toString());
        }
    }

    /**Metodo que retorna el objeto write.
     * @return
     */
    public PdfWriter getWriter() {
        return writer;
    }

    /**Metodo permite ingrese el write del documento pdf.
     * @param writer
     */
    public void setWriter(PdfWriter writer) {
        this.writer = writer;
    }

    /**Metodo que retorna el contentbay de un documento.
     * @return contentbyte
     */
    public PdfContentByte getContentbyte() {
        contentbyte = getWriter().getDirectContent();
        return contentbyte;
    }

    /**
     * @param contentbyte
     */
    public void setContentbyte(PdfContentByte contentbyte) {
        this.contentbyte = contentbyte;
    }

}
