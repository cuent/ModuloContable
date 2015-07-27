package edu.uc.modulocontable.info.empresa;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Leer {

    public static void main(String arg[]) {

        try {
            Empresa e = new Empresa("Empresa XYZ ", "Calle 1 y Calle 2", "013938272001", "1800-23849");
            Archivo a = new Archivo();    

            List verificar = a.obtieneContenidoArchivo();
            a.agregaContenidoArchivo(e.toString());
            
            System.out.println(verificar.toString());
        } catch (IOException ex) {
            Logger.getLogger(Leer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
