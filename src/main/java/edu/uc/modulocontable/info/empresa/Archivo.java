package edu.uc.modulocontable.info.empresa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Archivo {

    private File archivo = new File("texto.txt");

    public void agregaContenidoArchivo(String contenido)
            throws FileNotFoundException, IOException {
        if (archivo == null) {
            throw new IllegalArgumentException("El archivo no debe ser nulo.");
        }
        if (!archivo.exists()) {
            throw new FileNotFoundException("el archivo no existe: " + archivo);
        }
        if (!archivo.isFile()) {
            throw new IllegalArgumentException("no debe ser un directorio: " + archivo);
        }
        if (!archivo.canWrite()) {
            throw new IllegalArgumentException("El archivo no puede ser escrito: " + archivo);
        }

        Writer output = new BufferedWriter(new FileWriter(archivo));
        try {
            output.write(contenido);
        } finally {
            output.close();
        }
    }

    public List<Empresa> obtieneContenidoArchivo() {
        String[] dat_empresa = null;
        Empresa e = new Empresa();
        List<Empresa> l = new ArrayList();
        try {
            BufferedReader entrada = new BufferedReader(new FileReader(archivo));
            try {
                String line = null;
                while ((line = entrada.readLine()) != null) {
                    dat_empresa = line.split(",");
                }
                e.setNombre(dat_empresa[0]);
                e.setDireccion(dat_empresa[1]);
                e.setRuc(dat_empresa[2]);
                e.setTelefono(dat_empresa[3]);
                l.add(e);
            } finally {
                entrada.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return l;
    }
}
