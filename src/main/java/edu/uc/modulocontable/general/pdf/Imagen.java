package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**Objeto para ingresar una imagen en el pdf.
 *
 * @author juan Pablo Japa juan.japal@hotmail.com
 *
 */
public class Imagen extends Elemento {

    private int posicion = 0;
    private String dir;
    private Image image;

    /**Metodo que permite ingrtesar una imagen al documento pdf.
     *
     * @param dir
     * @param posicion
     * @throws DocumentException
     * @throws IOException
     */
    public Imagen(String dir, int posicion) {
        this.posicion = posicion;
        this.dir = dir;
    }

    public Imagen() {
        super();
    }

    /**Metodo que permite retornar la posicion de la imagen.
     * @return posicion
     */
    public int getPosicion() {
        return posicion;
    }

    /**Metodo permite ingresar la posicion de la imagen.
     * @param posicion
     * @pdesaram posicion
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    /**Metodo para retorna la imagen ya contruida.
     *
     * @return imagen
     */
    public Image getImage() {

        generarImagen();
        return image;
    }

    /**Metodo que permite ingresar una imagen.
     * @param image
     */
    public void setImage(Image image) {

        this.image = image;
    }

    /** Metodo que permite generar imagen con los parametros establacidos.
     *
     */
    public void generarImagen() {
        try {
            image = Image.getInstance(getDir());
            image.scaleAbsolute(0, 20);
            image.scaleToFit(300, 240);
            image.setAbsolutePosition(500, 660);
        } catch (Exception e) {
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.toString());
        }
    }

    /**
     * @return dir
     */
    public String getDir() {
        return dir;
    }

    /**
     * @param dir
     */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /**Metodo Permite definir la pocion de la imagen en la cordenada Y.
     * @param PosX
     * @param PosY
     */

    public void setPosicion(int PosX, int PosY) {
        getImage().setAbsolutePosition(PosX, PosY);

    }

    /**Metodo que permite definir el tamano de la imagen.
     * @param TamX
     * @param TamY
     */
    public void setdimencion(int TamX, int TamY) {
        image.scaleToFit(TamX, TamY);
    }
}
