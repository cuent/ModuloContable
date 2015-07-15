package edu.uc.modulocontable.general.pdf;

import com.itextpdf.text.Font;

/** Objeto para poner un espacio en blanco.
 *
 * @author omar velez omar.velez@sicsas.com
 *
 */
public class EspacioBlanco extends Elemento {

    public EspacioBlanco() {
        super();
        limpiar();
        setFont(new Font(getBf(), 6, Font.NORMAL));
    }

    /**Metodo que retorna un String.
     * @return
     */
    public String getTexto() {
        return " ";
    }
}
