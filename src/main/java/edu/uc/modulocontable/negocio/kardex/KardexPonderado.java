/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.negocio.kardex;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author cuent
 */
public class KardexPonderado implements Serializable {

    private Date fecha;
    private String tipo;
    private String detalle;
    private int cantidad;
    private double costo;
    private double subtotal;
    private Inventario inventario;

    public KardexPonderado() {
    }

    public KardexPonderado(Date fecha, String tipo, String detalle, int cantidad, double costo, double subtotal, Inventario inventario) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.costo = costo;
        this.subtotal = subtotal;
        this.inventario = inventario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    @Override
    public String toString() {
        return "KardexPonderado{" + "fecha=" + fecha + ", tipo=" + tipo + ", detalle=" + detalle + ", cantidad=" + cantidad + ", costo=" + costo + ", subtotal=" + subtotal + ", inventario=" + inventario + '}';
    }



}
