/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.backing;

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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "transaccionHelper")
@RequestScoped
public class TransaccionHelper {

    @ManagedProperty("#{beanTransaccion}")
    private BeanTransacciones beanTransaccion;
    private List<Cuenta> cuentas;
    private List<Asiento> asientos;
    private List<Transaccion> transacciones;
    private List<Transaccion> auxTransacciones;
    private Cuenta cuenta;

    @EJB
    private AsientoFacade asientoFacade;
    @EJB
    private CuentaFacade cuentaFacade;
    @EJB
    private TransaccionFacade transaccionFacade;
    private BigDecimal totalDebe = BigDecimal.ZERO;
    private BigDecimal totalHaber = BigDecimal.ZERO;
    private BigDecimal totalDeudor = BigDecimal.ZERO;
    private BigDecimal totalAcreedor = BigDecimal.ZERO;

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones = transaccionFacade.findAll();
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public List<Transaccion> getTransaccionesSolas() {
        return transacciones = transaccionFacade.getCuentas();
    }

    public List<Cuenta> getListaCuentas() {
        cuentas = cuentaFacade.findAll();
        transacciones = transaccionFacade.getCuentas();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Transaccion t : transacciones) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getIdcodcuenta().equals(t.getIdcodcuenta().getIdcodcuenta())) {
                    cuentasAux.add(cuenta);
                }
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public List<Cuenta> getListaCuentasCorrientes() {
        cuentas = cuentaFacade.findAll();
        transacciones = transaccionFacade.getCuentas();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Transaccion t : transacciones) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getIdcodcuenta().equals(t.getIdcodcuenta().getIdcodcuenta())) {
                    if (cuenta.getNumcuenta().startsWith("1.1.")) {
                        cuentasAux.add(cuenta);
                    }
                }
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public List<Cuenta> getListaCuentasNoCorrientes() {
        cuentas = cuentaFacade.findAll();
        transacciones = transaccionFacade.getCuentas();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Transaccion t : transacciones) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getIdcodcuenta().equals(t.getIdcodcuenta().getIdcodcuenta())) {
                    if (cuenta.getNumcuenta().startsWith("1.2.")) {
                        cuentasAux.add(cuenta);
                    }
                }
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public List<Cuenta> getListaCuentasPasivo() {
        cuentas = cuentaFacade.findAll();
        transacciones = transaccionFacade.getCuentas();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Transaccion t : transacciones) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getIdcodcuenta().equals(t.getIdcodcuenta().getIdcodcuenta())) {
                    if (cuenta.getNumcuenta().startsWith("2.1.")) {
                        cuentasAux.add(cuenta);
                    }
                }
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public List<Cuenta> getListaCuentasPatrimonio() {
        cuentas = cuentaFacade.findAll();
        transacciones = transaccionFacade.getCuentas();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Transaccion t : transacciones) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getIdcodcuenta().equals(t.getIdcodcuenta().getIdcodcuenta())) {
                    if (cuenta.getNumcuenta().startsWith("3.1.")) {
                        cuentasAux.add(cuenta);
                    }
                }
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public List<Cuenta> getListaCuentasIngresosOperacionales() {
        cuentas = cuentaFacade.findAll();
        transacciones = transaccionFacade.getCuentas();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Transaccion t : transacciones) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getIdcodcuenta().equals(t.getIdcodcuenta().getIdcodcuenta())) {
                    if (cuenta.getNumcuenta().startsWith("4.1.")) {
                        cuentasAux.add(cuenta);
                    }
                }
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public List<Cuenta> getListaCuentasGastosOperacionales() {
        cuentas = cuentaFacade.findAll();
        transacciones = transaccionFacade.getCuentas();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Transaccion t : transacciones) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getIdcodcuenta().equals(t.getIdcodcuenta().getIdcodcuenta())) {
                    if (cuenta.getNumcuenta().startsWith("5.1.")) {
                        cuentasAux.add(cuenta);
                    }
                }
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public List<Cuenta> getListaCuentasGastosNoOperacionales() {
        cuentas = cuentaFacade.findAll();
        transacciones = transaccionFacade.getCuentas();
        List<Cuenta> cuentasAux = new ArrayList<>();
        for (Transaccion t : transacciones) {
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getIdcodcuenta().equals(t.getIdcodcuenta().getIdcodcuenta())) {
                    if (cuenta.getNumcuenta().startsWith("5.2.")) {
                        cuentasAux.add(cuenta);
                    }
                }
            }
        }
        cuentas = cuentasAux;
        return cuentas;
    }

    public BigDecimal getTotalCorrientes() {
        BigDecimal diferencia = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (Cuenta c : getListaCuentasCorrientes()) {
            diferencia = c.getDiferencia();
            total = total.add(diferencia);
        }
        return total;
    }

    public BigDecimal getTotalNoCorrientes() {
        BigDecimal diferencia = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (Cuenta c : getListaCuentasNoCorrientes()) {
            diferencia = c.getDiferencia();
            total = total.add(diferencia);
        }
        return total;
    }

    public BigDecimal getTotalActivo() {
        BigDecimal total = BigDecimal.ZERO;

        total = getTotalCorrientes().add(getTotalNoCorrientes());

        return total;
    }

    public BigDecimal getTotalPasivo() {
        BigDecimal diferencia = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (Cuenta c : getListaCuentasPasivo()) {
            diferencia = c.getDiferencia();
            total = total.add(diferencia);
        }
        return total;
    }

    public BigDecimal getTotalPatrimonio() {
        BigDecimal diferencia = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (Cuenta c : getListaCuentasPatrimonio()) {
            diferencia = c.getDiferencia();
            total = total.add(diferencia);
        }
        total = total.add(getTotalUtilidadEjercicio());
        return total;
    }

    public BigDecimal getTotalPasivoPatrimonio() {
        BigDecimal total = BigDecimal.ZERO;
        total = getTotalPasivo().add(getTotalPatrimonio());
        return total;
    }

    public BigDecimal getTotalIngresosOperacionales() {
        BigDecimal diferencia = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (Cuenta c : getListaCuentasIngresosOperacionales()) {
            diferencia = c.getDiferencia();
            total = total.add(diferencia);
        }
        return total;
    }

    public BigDecimal getTotalGastosOperacionales() {
        BigDecimal diferencia = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (Cuenta c : getListaCuentasGastosOperacionales()) {
            diferencia = c.getDiferencia();
            total = total.add(diferencia);
        }
        return total;
    }

    public BigDecimal getTotalUtilidadOperacional() {
        BigDecimal total = BigDecimal.ZERO;
        total = getTotalIngresosOperacionales().subtract(getTotalGastosOperacionales());
        return total;
    }

    public BigDecimal getTotalGastosNoOperacionales() {
        BigDecimal diferencia = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for (Cuenta c : getListaCuentasGastosNoOperacionales()) {
            diferencia = c.getDiferencia();
            total = total.add(diferencia);
        }
        return total;
    }

    public BigDecimal getTotalUtilidadEjercicio() {
        BigDecimal total = BigDecimal.ZERO;
        total = getTotalUtilidadOperacional().subtract(getTotalGastosNoOperacionales());
        return total;
    }

    public void CuentaListener(ActionEvent actionEvent) {
        Integer idcodcuenta = Integer.parseInt(actionEvent.toString());

        setTransaccionesCuenta(idcodcuenta);
    }

    public void setTransaccionesCuenta(Integer idcodcuenta) {
        auxTransacciones = transaccionFacade.getCuenta(idcodcuenta);
    }

    public List<Transaccion> getTransaccionesCuenta() {
        return auxTransacciones;
    }

    public void getSumas() {
        BigDecimal auxFDebe = BigDecimal.ZERO;
        BigDecimal auxFHaber = BigDecimal.ZERO;

        transacciones = transaccionFacade.findAll();

        for (Transaccion t : transacciones) {
            auxFDebe = auxFDebe.add(t.getDebe());
            auxFHaber = auxFHaber.add(t.getHaber());
        }

        setTotalDebe(auxFDebe);
        setTotalHaber(auxFHaber);
    }

    public void getSumasTipo() {
        BigDecimal auxAcreedor = BigDecimal.ZERO;
        BigDecimal auxDeudor = BigDecimal.ZERO;
        BigDecimal diferencia = BigDecimal.ZERO;

        int i = 0;
        for (Cuenta c : getListaCuentas()) {

            diferencia = c.getTotalDebe().subtract(c.getTotalHaber());
            System.out.println(i + " " + diferencia);
            if (diferencia.doubleValue() < 0) {
                System.out.println("Acreedor");
                auxAcreedor = auxAcreedor.add(diferencia.negate());
            } else {
                System.out.println("Deudor");
                auxDeudor = auxDeudor.add(diferencia);
            }
        }
        setTotalAcreedor(auxAcreedor);
        setTotalDeudor(auxDeudor);
    }

    public BigDecimal getTotalDeudor() {
        getSumasTipo();
        return totalDeudor;
    }

    public void setTotalDeudor(BigDecimal totalDeudor) {
        this.totalDeudor = totalDeudor;
    }

    public BigDecimal getTotalAcreedor() {
        getSumasTipo();
        return totalAcreedor;
    }

    public void setTotalAcreedor(BigDecimal totalAcreedor) {
        this.totalAcreedor = totalAcreedor;
    }

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
        getSumas();
        return totalDebe;
    }

    public BigDecimal getTotalHaber() {
        getSumas();
        return totalDebe;
    }

    public BigDecimal getTotalDebe1() {
        totalDebe = BigDecimal.ZERO;
        for (Transaccion auxT : getTransacciones()) {
            totalDebe = auxT.getDebe().add(totalDebe);
        }
        return totalDebe;
    }

    public void setTotalDebe(BigDecimal totalDebe) {
        this.totalDebe = totalDebe;
    }

    public BigDecimal getTotalHaber1() {
        totalHaber = BigDecimal.ZERO;
        for (Transaccion auxT : getTransacciones()) {
            totalHaber = auxT.getDebe().add(totalHaber);
        }
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

    public BeanTransacciones getBeanTransaccion() {
        return beanTransaccion;
    }

    public void setBeanTransaccion(BeanTransacciones beanTransaccion) {
        this.beanTransaccion = beanTransaccion;
    }

}
