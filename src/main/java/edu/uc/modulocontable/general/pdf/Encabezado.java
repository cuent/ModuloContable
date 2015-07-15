package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Objeto que permite ingresar un pie de paguina al documeneto, asi como tambien
 * el numero de paguina.
 *
 * @author Juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class Encabezado extends PdfPageEventHelper {

    private String encabezado;
    private PdfTemplate total;
    private String textofondo;
    protected Phrase watermark;
 

    public Encabezado() {
        super();
    }

    /**
     * Crea el objecto PdfTemplate el cual contiene el numero total de paginas
     * en el documento.
     *
     * @param writer
     * @param document
     */
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);

      
    }

   

   
    /**
     * Esta es el metodo a llamar cuando ocurra el evento onEndPage, es en este
     * evento donde crearemos el encabeazado de la pagina con los elementos
     * indicados.
     *
     * @param writer
     * @param document
     *
     */
    
  

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        Elemento e = new Elemento();
    
        e.limpiar();
        try {
            table.setWidths(new int[]{24, 24, 2});
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            table.getDefaultCell().setBorder(Rectangle.BOTTOM);
            e.setTexto(getEncabezado());
            table.addCell(e.getElemento());
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            e.setTexto(String.format("Pagina %1d de ", writer.getPageNumber()));
            table.addCell(e.getElemento());
            PdfPCell cell = new PdfPCell(Image.getInstance(total));
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
            table.writeSelectedRows(0, -1, 34, 40, writer.getDirectContent());
        } catch (Exception de) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, de.toString());
        }
    }

    /**
     * Realiza el conteo de paginas al momento de cerrar el documento.
     *
     * @param writer
     * @param document
     */
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        Elemento e = new Elemento();
        e.setTexto((writer.getPageNumber() - 1) + "");
        ColumnText.showTextAligned(total, 1, e.getElemento(), 2, 6, 0);
    }

    /**
     * Metodo que permite obtener el texto del encabezado.
     *
     * @return
     */
    public String getEncabezado() {
        return encabezado;
    }

    /**
     * Metodo que permite ingresar el texto en el encabezado.
     *
     * @param encabezado
     */
    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    /**
     * Metodo permite contruir la marca de agua.
     *
     */
    public void generarMarcaAgua() {
        watermark = new Phrase("", new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.LIGHT_GRAY));
    }

    /**
     * Metodo que permite a�adir una marcado de agua al documento.
     *
     * @param writer
     */
   

    /**
     * @return
     */
    public String getTextofondo() {
        return textofondo;
    }

    /**
     * @param textofondo
     */
    public void setTextofondo(String textofondo) {
        this.textofondo = textofondo;
    }
}
