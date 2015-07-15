package edu.uc.modulocontable.controller;


import edu.uc.modulocontable.facade.DocumentoFacade;
import edu.uc.modulocontable.modelo2.Documento;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "documentoController")
@SessionScoped
public class DocumentoController extends AbstractController<Documento> {

    @EJB
    private DocumentoFacade ejbFacade;

    /**
     * Initialize the concrete Documento controller bean. The AbstractController
     * requires the EJB Facade object for most operations.
     */
    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }

    public DocumentoController() {
        // Inform the Abstract parent controller of the concrete Documento?cap_first Entity
        super(Documento.class);
    }

}
