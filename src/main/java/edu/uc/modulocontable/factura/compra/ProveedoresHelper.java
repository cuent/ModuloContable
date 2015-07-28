package edu.uc.modulocontable.factura.compra;

import edu.uc.modulocontable.facade.AutorizacionesFacade;
import edu.uc.modulocontable.facade.ProveedoresFacade;
import edu.uc.modulocontable.modelo2.Autorizaciones;
import edu.uc.modulocontable.modelo2.Proveedores;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "proveedoresHelper")
@SessionScoped
public class ProveedoresHelper implements Serializable {

    @EJB
    private ProveedoresFacade ejbFacade;
    @EJB
    private AutorizacionesFacade ejbAutorizaciones;
    private boolean validaciones;
    private String seleccionTipoIdentificacion;
    FacesMessage msg;
    private Proveedores selected;
    private List<Proveedores> items;
    private List<Autorizaciones> autorizaciones;
    private Autorizaciones auxAutorizacion;

    public List<Autorizaciones> getAutorizaciones() {
        return autorizaciones = ejbAutorizaciones.findAll();
    }

    public void setAutorizaciones(List<Autorizaciones> autorizaciones) {
        this.autorizaciones = autorizaciones;
    }

    public boolean isValidaciones() {
        return validaciones;
    }

    public void setValidaciones(boolean validaciones) {
        this.validaciones = validaciones;
    }

    public Proveedores getSelected() {
        return selected;
    }

    public void setSelected(Proveedores selected) {
        this.selected = selected;
    }

    public List<Proveedores> getItems() {
        return items = ejbFacade.findAll();
    }

    public void setItems(List<Proveedores> items) {
        this.items = items;
    }

    public void iniciarnuevo() {
        this.setSelected(new Proveedores());
        setValidaciones(false);
    }

    public Autorizaciones getAuxAutorizacion() {
        return auxAutorizacion;
    }

    public void setAuxAutorizacion(Autorizaciones auxAutorizacion) {
        this.auxAutorizacion = auxAutorizacion;
    }

    /**
     * Initialize the concrete Proveedores controller bean. The
     * AbstractController requires the EJB Facade object for most operations.
     * <p>
     * In addition, this controller also requires references to controllers for
     * parent entities in order to display their information from a context
     * menu.
     */
    @PostConstruct
    public void init() {
        //super.setFacade(ejbFacade);
    }

    public void eliminarcliente(Proveedores c) {

        if (c != null) {
            ejbFacade.remove(c);
            this.setItems(ejbFacade.findAll());
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "Cliente Eliminado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void validarIdentificacion() {
        if (seleccionTipoIdentificacion != null) {
            switch (seleccionTipoIdentificacion) {
                case "Cedula":

                    validaciones = validacionCedulaEcuatoriana(this.getSelected().getIdentificacion());
                    if (!validaciones) {
                        setValidaciones(false);
                        FacesContext context = FacesContext.getCurrentInstance();

                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cedula Incorrecta"));
                        //context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
                    }
                    break;
                case "Ruc":
                    validaciones = validarRucEcuatoriano(this.getSelected().getIdentificacion());
                    if (!validaciones) {
                        setValidaciones(false);
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ruc Incorrecta"));
                        //context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
                    }
                    break;
                default:
                    setValidaciones(true);
                    break;

            }
        }

    }

    public void guadar(ActionEvent event) {
        if (this.getSelected() != null) {
            getSelected().setAutorizacion(auxAutorizacion);
            getSelected().setFechaCaducidadAutorizacion(auxAutorizacion.getFechaCaducidad());
            ejbFacade.create(this.getSelected());
            setValidaciones(false);
            this.setItems(ejbFacade.findAll());
            iniciarnuevo();
        }
    }

    private boolean validacionCedulaEcuatoriana(String x) {
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

    private boolean validarRucEcuatoriano(String cedula) {
        int NUM_PROVINCIAS = 24;
        boolean isValid = false;
        if (cedula == null || cedula.length() != 10) {
            isValid = false;
        }
        int prov = Integer.parseInt(cedula.substring(0, 2));

        if (!((prov > 0) && (prov <= NUM_PROVINCIAS))) {
            isValid = false;
        }

        int[] d = new int[10];
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }

        int imp = 0;
        int par = 0;

        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }

        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }

        final int suma = imp + par;

        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1)
                + "0")
                - suma;

        d10 = (d10 == 10) ? 0 : d10;

        if (d10 == d[9]) {
            isValid = true;
        } else {
            isValid = false;
        }

        return isValid;
    }

    public void tipoSeleccionado(ValueChangeEvent event) {
        seleccionTipoIdentificacion = event.getNewValue().toString();

    }

    public ProveedoresHelper() {
        // Inform the Abstract parent controller of the concrete Proveedores?cap_first Entity
        //super(Proveedores.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
    }

    public void onRowEdit(RowEditEvent event) {
        if ((Proveedores) event.getObject() != null) {
            ejbFacade.edit((Proveedores) event.getObject());
            msg = new FacesMessage("Informacion", "Proveedor Modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            msg = new FacesMessage("Error", "Proveedor no pudo ser modificado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void onRowCancel(RowEditEvent event) {
        msg = new FacesMessage("Informacion", "Modificacion Cancelada");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Sets the "items" attribute with a collection of CabeceraFacturac entities
     * that are retrieved from Proveedores?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for CabeceraFacturac page
     */
    public String navigateCabeceraFacturacList() {
        if (this.getSelected() != null) {
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("CabeceraFacturac_items", this.getSelected().getCabeceraFacturacList());
        }
        return "/generado/nuevo/cabeceraFacturac/index";
    }

    public void actualizarFecha() {
        getSelected().setFechaCaducidadAutorizacion(getSelected().getAutorizacion().getFechaCaducidad());
    }

}
