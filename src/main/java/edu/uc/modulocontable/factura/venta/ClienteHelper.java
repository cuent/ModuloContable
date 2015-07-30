/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.factura.venta;

import edu.uc.modulocontable.facade.ClienteFacade;
import edu.uc.modulocontable.modelo2.Cliente;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "clienteHelper")
@SessionScoped
public class ClienteHelper implements Serializable {

    @EJB
    private ClienteFacade ejbFacade;
    FacesMessage msg;
    private String tipoIdentificacion;
    private List<Cliente> items;
    private Cliente selected;
    private boolean isValid = false;

    public void iniciarCliente() {
        this.setSelected(new Cliente());
        this.setItems(ejbFacade.findAll());
    }

    public List<Cliente> getItems() {
        return items;
    }

    public void setItems(List<Cliente> items) {
        this.items = items;
    }

    public Cliente getSelected() {
        return selected;
    }

    public void setSelected(Cliente selected) {
        this.selected = selected;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void validarCedulaORuc() {
        System.out.println("hola " + getTipoIdentificacion());
        if (getTipoIdentificacion().equalsIgnoreCase("Cedula")) {

            if (this.getSelected().getIdentificacion() != null && this.getSelected().getIdentificacion().length() > 0) {
                if (getTipoIdentificacion().equalsIgnoreCase("Cedula")) {
                    if (validarCedula(this.getSelected().getIdentificacion())) {
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "La Cedula es Correcta");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        isValid = true;
                    } else {
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informacion", "La Cedula es Incorrecta");
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                        isValid = false;
                    }

                } else if (getTipoIdentificacion().equalsIgnoreCase("RUC")) {
                    isValid = true;
                } else {
                    isValid = true;
                }
            }
        } else {
            isValid = true;
        }
    }

    public void guardarNuevo(ActionEvent evet) {
        if (isValid) {
            if (this.getSelected() != null) {
                this.getSelected().setTipoIdentificacion(getTipoIdentificacion());

                ejbFacade.create(this.getSelected());
                setItems(ejbFacade.findAll());
                iniciarCliente();

            } else {
                System.out.println("no guardo cliente");
            }
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

    public void eliminarcliente(Cliente c) {

        if (c != null) {
            ejbFacade.remove(c);
            this.setItems(ejbFacade.findAll());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cliente Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public boolean validarCedula(String x) {
        int suma = 0;
        if (x.length() == 9) {
            System.out.println("Ingrese su cedula de 10 digitos");
            return false;
        } else {
            int a[] = new int[x.length() / 2];
            int b[] = new int[(x.length() / 2)];
            int c = 0;
            int d = 1;
            for (int i = 0; i < x.length() / 2; i++) {
                a[i] = Integer.parseInt(String.valueOf(x.charAt(c)));
                c = c + 2;
                if (i < (x.length() / 2) - 1) {
                    b[i] = Integer.parseInt(String.valueOf(x.charAt(d)));
                    d = d + 2;
                }
            }

            for (int i = 0; i < a.length; i++) {
                a[i] = a[i] * 2;
                if (a[i] > 9) {
                    a[i] = a[i] - 9;
                }
                suma = suma + a[i] + b[i];
            }
            int aux = suma / 10;
            int dec = (aux + 1) * 10;
            if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length() - 1)))) {
                return true;
            } else if (suma % 10 == 0 && x.charAt(x.length() - 1) == '0') {
                return true;
            } else {
                return false;
            }

        }
    }
}
