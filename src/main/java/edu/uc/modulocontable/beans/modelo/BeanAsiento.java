/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.beans.modelo;

import edu.uc.modulocontable.domain.entity.AsientoFacade;
import edu.uc.modulocontable.services.ejb.Asiento;
import edu.uc.modulocontable.services.ejb.Cuenta;
import edu.uc.modulocontable.services.ejb.Transaccion;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author cuent
 */
@ManagedBean(name = "beanAsiento")
@ViewScoped
public class BeanAsiento implements Serializable{

    private int numDiario;
    private int periodo;
    private Date fecha;
    private String concepto;
    private int documento;
    private List<Transaccion> transacciones;
    
    @PostConstruct
    public void init() {
        transacciones=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Transaccion t=new Transaccion();
            Cuenta c= new Cuenta();
            c.setDescripcion("");
            t.setDebe(BigDecimal.ZERO);
            t.setHaber(BigDecimal.ZERO);
            t.setIdcodcuenta(c);
            t.setReferencia("");
            transacciones.add(t);
        }
        
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }

    public int getDocumento() {
        return documento;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getNumDiario() {
        return numDiario;
    }

    public void setNumDiario(int numDiario) {
        this.numDiario = numDiario;
    }


}
