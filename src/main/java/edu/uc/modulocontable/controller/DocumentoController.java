package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.modelo2.Documento;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "documentoController")
@SessionScoped
public class DocumentoController extends AbstractController<Documento> implements Serializable {

    public DocumentoController() {
        // Inform the Abstract parent controller of the concrete Documento?cap_first Entity
        super(Documento.class);
    }

}
