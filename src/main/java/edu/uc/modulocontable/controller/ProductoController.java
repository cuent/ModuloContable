package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.ProductoFacade;
import edu.uc.modulocontable.modelo2.Producto;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "productoController")
@SessionScoped
public class ProductoController extends AbstractController<Producto> implements Serializable {
    
    @EJB
    private ProductoFacade ejbFacade;
    private List<Producto> listaProductos;
    FacesMessage msg;

    /**
     * Initialize the concrete Producto controller bean. The AbstractController
     * requires the EJB Facade object for most operations.
     * <p>
     * In addition, this controller also requires references to controllers for
     * parent entities in order to display their information from a context
     * menu.
     */
    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
        
    }
    
    public ProductoController() {
        super(Producto.class);
    }
    
    public void iniciarNuevo() {
        this.setSelected(new Producto());
        this.setItems(ejbFacade.findAll());
    }
    
    public void guardarNuevo(ActionEvent event) {
        if (this.getSelected() != null) {
            this.save(event);
            this.setItems(ejbFacade.findAll());
            iniciarNuevo();
        }
    }

    public void eliminar(Producto producto) {
        if (producto != null) {
            ejbFacade.remove(producto);
            this.setItems(ejbFacade.findAll());
            
        }
        
    }

    public void onRowEdit(RowEditEvent event) {
        if ((Producto) event.getObject() != null) {
            ejbFacade.edit((Producto) event.getObject());
            msg = new FacesMessage("Informacion", "El Producto ha sido Modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Error", "El Producto no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Informacion", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    /**
     * @param listaProductos the listaProductos to set
     */
    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
    
}
