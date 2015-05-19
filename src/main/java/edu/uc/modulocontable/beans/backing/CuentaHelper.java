/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.backing;

import edu.uc.modulocontable.beans.backing.util.JsfUtil;
import edu.uc.modulocontable.beans.modelo.BeanCuenta;
import edu.uc.modulocontable.beans.modelo.BeanTipo;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import edu.uc.modulocontable.domain.entity.CuentaFacade;
import edu.uc.modulocontable.domain.entity.TipoFacade;
import edu.uc.modulocontable.services.ejb.Cuenta;
import edu.uc.modulocontable.services.ejb.Tipo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "cuentaHelper")
@RequestScoped
public class CuentaHelper {

    @EJB
    private CuentaFacade cuentaFacade;
    @EJB
    private TipoFacade tipoFacade;
    private List<Cuenta> cuentas;
    private List<Cuenta> cuentasDetalle;
    private List<Tipo> tipos;
    private List<String> categorias;
    private String valor;
    @ManagedProperty("#{beanCuenta}")
    private BeanCuenta beanCuenta;
    private final static String[] V_CATEGORIAS;
    private boolean isGroup;

    public List<Cuenta> getCuentasDetalle() {
        return cuentasDetalle;
    }

    public void setCuentasDetalle(List<Cuenta> cuentasDetalle) {
        this.cuentasDetalle = cuentasDetalle;
    }

    public boolean isIsGroup() {
        return isGroup;
    }

    public void setIsGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    static {
        V_CATEGORIAS = new String[2];
        V_CATEGORIAS[0] = "GRUPO";
        V_CATEGORIAS[1] = "DETALLE";
    }

    public BeanCuenta getBeanCuenta() {
        return beanCuenta;
    }

    public void setBeanCuenta(BeanCuenta beanCuenta) {
        this.beanCuenta = beanCuenta;
    }

    public TipoFacade getTipoFacade() {
        return tipoFacade;
    }

    public void setTipoFacade(TipoFacade tipoFacade) {
        this.tipoFacade = tipoFacade;
    }

    public List<Tipo> getTipos() {
        return tipos = getTipoFacade().findAll();
    }

    public void setTipos(List<Tipo> tipos) {
        this.tipos = tipos;
    }

    public CuentaFacade getCuentaFacade() {
        return cuentaFacade;
    }

    public void setCuentaFacade(CuentaFacade cuentaFacade) {
        this.cuentaFacade = cuentaFacade;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void cambiaValores(ActionEvent ev) {
        System.out.println("Valores almacenados en cada ModelBean");
        System.out.println("Valor alcance request: " + getValor());
    }

    @PostConstruct
    public void init() {
        cuentas = getCuentaFacade().findAll();
        cuentasDetalle = getCuentaFacade().findAll();

        List<Cuenta> auxCuentasDetalle = new ArrayList<>();
        for (Cuenta cuentasDetalle1 : cuentasDetalle) {
            if (!cuentasDetalle1.getCategoria().equalsIgnoreCase("DETALLE")) {
                auxCuentasDetalle.add(cuentasDetalle1);
            }
        }
        cuentasDetalle = auxCuentasDetalle;
//Ojo revisar: Visualizar dinamicamente las cuentas
//        for (Cuenta cuenta : cuentas) {
//            System.out.println("Padre-->" + cuenta);
//            List<Cuenta> hijos = cuenta.getCuentaList();
//            for (Cuenta hijo : hijos) {
//                System.out.println("*\t\tHijo-->" + hijo);
//            }
//        }
    }

    /**
     * Creates a new instance of CuentaHelper
     */
    public CuentaHelper() {
    }

    public void onRowEdit(RowEditEvent event) {
        Cuenta cuenta = (Cuenta) event.getObject();
        cuentaFacade.edit(cuenta);
        cuentas = cuentaFacade.findAll();
        String mensaje = ResourceBundle.getBundle("/mensajes").getString("cuentaEdicionExitosa");
        JsfUtil.addSuccessMessage(mensaje);
    }

    public void onRowCancel(RowEditEvent event) {
        String mensaje = ResourceBundle.getBundle("/mensajes").getString("cuentaCancelarExitosa");
        JsfUtil.addSuccessMessage(mensaje);
    }

    public void onRowDelete(Cuenta cuenta) {
        try {
            cuentaFacade.remove(cuenta);
            String mensaje = ResourceBundle.getBundle("/mensajes").getString("cuentaEliminarExitosa");
            cuentas = cuentaFacade.findAll();

//cuentas.remove(cuenta);
            JsfUtil.addSuccessMessage(mensaje);
        } catch (Exception e) {
            String mensaje = ResourceBundle.getBundle("/mensajes").getString("cuentaEliminarError");
            JsfUtil.addErrorMessage(mensaje);
        }
    }

    public void addMessage() {
        String summary = isGroup ? "Seleccione el grupo al que pertenece la "
                + "cuenta" : "La cuenta no pertenece a ningun grupo";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary));
    }

    public void createCuenta(ActionEvent ev) {

        Cuenta cuenta = new Cuenta();

        String numCuenta = getBeanCuenta().getNumCuenta();
        String descCuenta = getBeanCuenta().getDescCuenta();
        String categoriaCuenta = getBeanCuenta().getCategoriaCuenta();
        Tipo tipo = getBeanCuenta().getTipo();
        BigDecimal saldoInicial = getBeanCuenta().getSaldoInicial();
        BigDecimal saldoFinal = getBeanCuenta().getSaldoFinal();

        if (isGroup) {
            Cuenta cuentaPadre = getBeanCuenta().getCuentaPadre();
            cuenta.setIdcodcuentapadre(cuentaPadre);
        }

        cuenta.setNumcuenta(numCuenta);
        cuenta.setDescripcion(descCuenta);
        cuenta.setCategoria(categoriaCuenta);
        cuenta.setIdtipo(tipo);
        cuenta.setSaldoinicial(saldoInicial);
        cuenta.setSaldofinal(saldoFinal);

        cuentaFacade.create(cuenta);
        cuentas = cuentaFacade.findAll();

        String mensaje = ResourceBundle.getBundle("/mensajes").getString("cuentaCreadaExitosa");
        JsfUtil.addSuccessMessage(mensaje);
    }

//    public void addMessage(String summary) {
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, "");
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        facesContext.addMessage(null, message);
//        facesContext.renderResponse();
//    }
    public List<String> getCategorias() {
        return categorias = Arrays.asList(V_CATEGORIAS);
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }
}
