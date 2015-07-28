/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.backing;

import edu.uc.modulocontable.beans.backing.util.JsfUtil;
import edu.uc.modulocontable.beans.modelo.BeanAsiento;
import edu.uc.modulocontable.beans.modelo.BeanTipo;
import edu.uc.modulocontable.beans.modelo.BeanTransacciones;
import edu.uc.modulocontable.domain.entity.AsientoFacade;
import edu.uc.modulocontable.domain.entity.CuentaFacade;
import edu.uc.modulocontable.domain.entity.TransaccionFacade;
import edu.uc.modulocontable.services.ejb.Asiento;
import edu.uc.modulocontable.services.ejb.Cuenta;
import edu.uc.modulocontable.services.ejb.Transaccion;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.component.column.Column;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "asientoHelper")
@RequestScoped
public class AsientoHelper {

    @ManagedProperty("#{beanAsiento}")
    private BeanAsiento beanAsiento;
    private List<Cuenta> cuentas;
    private List<Asiento> asientos;
    private Asiento asiento;
    @EJB
    private AsientoFacade asientoFacade;
    @EJB
    private CuentaFacade cuentaFacade;
    @EJB
    private TransaccionFacade transaccionFacade;
    private BigDecimal totalDebe = BigDecimal.ZERO;
    private BigDecimal totalHaber = BigDecimal.ZERO;

    public TransaccionFacade getTransaccionFacade() {
        return transaccionFacade;
    }

    public void setTransaccionFacade(TransaccionFacade transaccionFacade) {
        this.transaccionFacade = transaccionFacade;
    }

    public List<Asiento> getAsientos() {
        return asientos = asientoFacade.findAll();
    }

    public BigDecimal getTotalDebe() {
        return totalDebe;
    }

    public void setTotalDebe(BigDecimal totalDebe) {
        this.totalDebe = totalDebe;
    }

    public BigDecimal getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(BigDecimal totalHaber) {
        this.totalHaber = totalHaber;
    }

    public List<Cuenta> getCuentas() {
        cuentas = cuentaFacade.findAll();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getCategoria().equalsIgnoreCase("DETALLE")) {
                cuentasAux.add(cuenta);
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public BeanAsiento getBeanAsiento() {
        return beanAsiento;
    }

    public void setBeanAsiento(BeanAsiento beanAsiento) {
        this.beanAsiento = beanAsiento;
    }

    public Asiento preparar() {
        asiento = new Asiento();
        return asiento;
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void verificar(ActionEvent actionEvent) {
        actualizarValores();
        if (totalDebe.equals(totalHaber) && totalDebe.compareTo(BigDecimal.ZERO) > 0
                && totalHaber.compareTo(BigDecimal.ZERO) > 0) {
            List<Transaccion> transacciones = beanAsiento.getTransacciones();
            List<Transaccion> tSalida = new ArrayList<>();

            for (Transaccion t : transacciones) {
                if (t.getIdcodcuenta() != null && (t.getDebe() != BigDecimal.ZERO || t.getHaber() != BigDecimal.ZERO)) {
                    tSalida.add(t);
                }
            }

            BigDecimal tdebe = BigDecimal.ZERO;
            BigDecimal thaber = BigDecimal.ZERO;

            for (Transaccion tSalida1 : tSalida) {
                tdebe = tdebe.add(tSalida1.getDebe().setScale(2, BigDecimal.ROUND_HALF_UP));
                thaber = thaber.add(tSalida1.getHaber().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
//
            if (thaber.equals(tdebe)) {
                List<Asiento> asientosAux = asientoFacade.findAll();
                int numAsiento = asientosAux.size() + 1;

                Asiento asientoAux = new Asiento();
                asientoAux.setIdcodasiento(numAsiento);
                asientoAux.setNumasiento(numAsiento);
                asientoAux.setNumdiario(beanAsiento.getNumDiario());
                asientoAux.setPeriodo(beanAsiento.getPeriodo());
                asientoAux.setFecha(beanAsiento.getFecha());
                asientoAux.setDebe(tdebe);
                asientoAux.setHaber(thaber);
                asientoAux.setConcepto(beanAsiento.getConcepto());
                asientoAux.setDocumento(beanAsiento.getDocumento());

                for (Transaccion t : tSalida) {
                    t.setIdcodasiento(asientoAux);
                    //transaccionFacade.create(t);
                }
                asientoAux.setTransaccionList(tSalida);
                asientoFacade.create(asientoAux);

                asientos = asientoFacade.findAll();
                JsfUtil.addSuccessMessage("Ahora puede Finalizar");
            } else {
                JsfUtil.addErrorMessage("Verifique los valores");
            }
        } else {
            JsfUtil.addErrorMessage("Verifique los valores");
        }

    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void actualizarValores() {
        List<Transaccion> transacciones = getBeanAsiento().getTransacciones();
        BigDecimal auxFDebe = BigDecimal.ZERO;
        BigDecimal auxFHaber = BigDecimal.ZERO;

        for (Transaccion t : transacciones) {
            auxFDebe = auxFDebe.add(t.getDebe().setScale(2, BigDecimal.ROUND_HALF_UP));
            auxFHaber = auxFHaber.add(t.getHaber().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        setTotalDebe(auxFDebe);
        setTotalHaber(auxFHaber);
    }

    public void debeListener(ValueChangeEvent valueChangueEvent) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = facesContext.getViewRoot();
        actualizarValores();
        BigDecimal nuevoValor = (BigDecimal) valueChangueEvent.getNewValue();
        BigDecimal viejoValor = (BigDecimal) valueChangueEvent.getOldValue();

        if (nuevoValor.compareTo(viejoValor) > 0) {
            totalDebe = totalDebe.add(nuevoValor);
        } else {
            totalDebe = totalDebe.subtract(nuevoValor);
        }

        Column ciudadInputText = (Column) uiViewRoot.findComponent("AsientoCreateForm:dtAsientos:debefooter");
        ciudadInputText.setFooterText("$" + totalDebe + "");
    }

    public void haberListener(ValueChangeEvent valueChangueEvent) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = facesContext.getViewRoot();
        actualizarValores();
        BigDecimal nuevoValor = (BigDecimal) valueChangueEvent.getNewValue();
        BigDecimal viejoValor = (BigDecimal) valueChangueEvent.getOldValue();

        if (nuevoValor.compareTo(viejoValor) > 0) {
            totalHaber = totalHaber.add(nuevoValor);
        } else {
            totalHaber = totalHaber.subtract(nuevoValor);
        }

        Column ciudadInputText = (Column) uiViewRoot.findComponent("AsientoCreateForm:dtAsientos:haberfooter");
        ciudadInputText.setFooterText("$" + totalHaber + "");
    }

}
