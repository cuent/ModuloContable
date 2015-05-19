/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.modelo;

import edu.uc.modulocontable.services.ejb.Cuenta;
import edu.uc.modulocontable.services.ejb.Tipo;
import java.math.BigDecimal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "beanCuenta")
@RequestScoped
public class BeanCuenta {

    private String numCuenta = "#";
    private String descCuenta = "descripcion";
    private String categoriaCuenta;
    private Tipo tipo;
    private Cuenta cuentaPadre;
    private BigDecimal saldoInicial = new BigDecimal(0);
    private BigDecimal saldoFinal = new BigDecimal(0);

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public Cuenta getCuentaPadre() {
        return cuentaPadre;
    }

    public void setCuentaPadre(Cuenta cuentaPadre) {
        this.cuentaPadre = cuentaPadre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getCategoriaCuenta() {
        return categoriaCuenta;
    }

    public void setCategoriaCuenta(String categoriaCuenta) {
        this.categoriaCuenta = categoriaCuenta;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public String getDescCuenta() {
        return descCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public void setDescCuenta(String descCuenta) {
        this.descCuenta = descCuenta;
    }

    /**
     * Creates a new instance of BeanCuenta
     */
    public BeanCuenta() {
    }

}
