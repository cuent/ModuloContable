/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.info.empresa;

import edu.uc.modulocontable.modelo2.Documento;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "empresaHelper")
@SessionScoped
public class EmpresaHelper implements Serializable {

    private List<Empresa> items;
    private Archivo archivo;
    FacesMessage msg;
    private Empresa selected;

    @PostConstruct
    public void init() {
        //FileWriter escribir = null;
        setSelected(new Empresa());
        archivo = new Archivo();
        //File file = new File("texto.txt");
        //escribir = new FileWriter(file, true);
        //Empresa e = new Empresa("Empresa XYZ ", "Calle 1 y Calle 2", "013938272001", "1800-23849");
        //archivo.agregaContenidoArchivo(e.toString());
        items = archivo.obtieneContenidoArchivo();

    }

    public Empresa getSelected() {
        return selected;
    }

    public void setSelected(Empresa selected) {
        this.selected = selected;
    }

    public List<Empresa> getItems() {
        return items;
    }

    public void setItems(List<Empresa> items) {
        this.items = items;
    }

    public void onRowEdit(RowEditEvent event) {
        if ((Empresa) event.getObject() != null) {
            try {
                Empresa e = (Empresa) event.getObject();
                archivo.agregaContenidoArchivo(e.toString());
                msg = new FacesMessage("Informacion", "Empresa Modificada");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (IOException ex) {
                Logger.getLogger(EmpresaHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            msg = new FacesMessage("Error", "La Empresa no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Informacion", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
