package edu.uc.modulocontable.controller;

import edu.uc.modulocontable.facade.ClienteFacade;
import edu.uc.modulocontable.modelo2.Cliente;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "clienteController")
@SessionScoped
public class ClienteController extends AbstractController<Cliente> {
    
    @EJB
    private ClienteFacade ejbFacade;
    
    FacesMessage msg;
    private String tipoIdentificacion;
    private List<Cliente> listaClientes;
    
    @PostConstruct
    public void init() {
        super.setFacade(ejbFacade);
    }
    
    public ClienteController() {
        // Inform the Abstract parent controller of the concrete Cliente?cap_first Entity
        super(Cliente.class);
    }
    
    public void iniciarCliente() {
        this.setSelected(new Cliente());
        this.setItems(ejbFacade.findAll());
    }
    
    public void guardarNuevo(ActionEvent evet) {
        if (this.getSelected() != null) {
            this.getSelected().setTipoIdentificacion(getTipoIdentificacion());
          
           ejbFacade.create(this.getSelected());
            setItems(ejbFacade.findAll());
            iniciarCliente();
            
        } else {
            System.out.println("no guardo cliente");
        }
    }
    
    public void eliminarcliente(Cliente c) {
        
        if (c != null) {
            ejbFacade.remove(c);
            this.setItems(ejbFacade.findAll());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cliente Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void onRowEdit(RowEditEvent event) {
        if ((Cliente) event.getObject() != null) {
            ejbFacade.edit((Cliente) event.getObject());
            msg = new FacesMessage("Cliente Editado", "El cliente ha sido Modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Cliente", "El cliente no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }
    
    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Cliente", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void validarCedulaORuc() {
//        System.out.println("hola" +getTipoIdentificacion());
//        if (getTipoIdentificacion().equalsIgnoreCase("0")) {
//
//            if (this.getSelected().getIdentificacion() != null && this.getSelected().getIdentificacion().length() > 0) {
//                if (this.getSelected().getTipoIdentificacion().equalsIgnoreCase("Cedula")) {
//                    if (validarCedula(this.getSelected().getIdentificacion())) {
//                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La Cedula es Correcta");
//                        FacesContext.getCurrentInstance().addMessage(null, msg);
//                    } else {
//                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "La Cedula es Incorrecta");
//                        FacesContext.getCurrentInstance().addMessage(null, msg);
//                    }
//
//                } else if (getTipoIdentificacion().equalsIgnoreCase("RUC")) {
//
//                }
//            }
//        }
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

    /**
     * Sets the "items" attribute with a collection of CabeceraFacturav entities
     * that are retrieved from Cliente?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for CabeceraFacturav page
     */
    public String navigateCabeceraFacturavList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("CabeceraFacturav_items", this.getSelected().getCabeceraFacturavList());
        }
        return "/generado/nuevo/cabeceraFacturav/index";
    }
    
    private void validarRuc(String dato) {
        
    }
    
    private boolean validarCedula(String dato) {
        
        boolean cedulaCorrecta = false;
        
        try {
            
            if (dato.length() == 10) {
                int tercerDigito = Integer.parseInt(dato.substring(2, 3));
                if (tercerDigito < 6) {
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(dato.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (dato.length() - 1); i++) {
                        digito = Integer.parseInt(dato.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }
                    
                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }
        
        return cedulaCorrecta;
    }

    /**
     * @return the tipoIdentificacion
     */
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * @param tipoIdentificacion the tipoIdentificacion to set
     */
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * @return the listaClientes
     */
    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    /**
     * @param listaClientes the listaClientes to set
     */
    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }
    
}
