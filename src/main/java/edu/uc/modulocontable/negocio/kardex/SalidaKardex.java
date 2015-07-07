/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.negocio.kardex;

/**
 *
 * @author cuent
 */
public class SalidaKardex {

    private int cantidad;
    private double costo;
    private double subtotal;

    public SalidaKardex(int cantidad, double costo, double subtotal) {
        this.cantidad = cantidad;
        this.costo = costo;
        this.subtotal = subtotal;
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

    @Override
    public String toString() {
        return "SalidaKardex{" + "cantidad=" + cantidad + ", costo=" + costo + ", subtotal=" + subtotal + '}';
    }

}
