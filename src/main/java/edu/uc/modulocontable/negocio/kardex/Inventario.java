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
public class Inventario {

    private int totalCantidad;
    private double totalCosto;
    private double totalSubtotal;

    public Inventario() {
    }

    public Inventario(int totalCantidad, double totalCosto, double totalSubtotal) {
        this.totalCantidad = totalCantidad;
        this.totalCosto = totalCosto;
        this.totalSubtotal = totalSubtotal;
    }

    public int getTotalCantidad() {
        return totalCantidad;
    }

    public void setTotalCantidad(int totalCantidad) {
        this.totalCantidad = totalCantidad;
    }

    public double getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(double totalCosto) {
        this.totalCosto = totalCosto;
    }

    public double getTotalSubtotal() {
        return totalSubtotal;
    }

    public void setTotalSubtotal(double totalSubtotal) {
        this.totalSubtotal = totalSubtotal;
    }

    @Override
    public String toString() {
        return "Inventario{" + "totalCantidad=" + totalCantidad + ", totalCosto=" + totalCosto + ", totalSubtotal=" + totalSubtotal + '}';
    }

}
