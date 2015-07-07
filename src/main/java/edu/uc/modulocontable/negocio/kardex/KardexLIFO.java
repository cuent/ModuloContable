/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.negocio.kardex;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author cuent
 */
public class KardexLIFO implements Serializable {

    private Date fecha;
    private String tipo;
    private String detalle;
    private int cantidad;
    private double costo;
    private double subtotal;
    Stack<Inventario> saldo;
    private List<SalidaKardex> salidas;

    public KardexLIFO() {
    }

    public KardexLIFO(Date fecha, String tipo, String detalle, int cantidad, double costo, double subtotal, Stack<Inventario> saldo, List<SalidaKardex> salidas) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.detalle = detalle;
        this.cantidad = cantidad;
        this.costo = costo;
        this.subtotal = subtotal;
        this.saldo = saldo;
        this.salidas = salidas;
    }

    public List<SalidaKardex> getSalidas() {
        return salidas;
    }

    public void setSalidas(List<SalidaKardex> salidas) {
        this.salidas = salidas;
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

    public Stack<Inventario> getSaldo() {
        return saldo;
    }

    public void setSaldo(Stack<Inventario> saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        String saldoS = "", salidaS = "";
        for (Inventario saldo1 : saldo) {
            saldoS += saldo1.toString();
        }
        if (salidas != null) {
            for (SalidaKardex salida : salidas) {
                salidaS += salida.toString();
            }
        }
        return "KardexFIFO{" + "fecha=" + fecha + ", tipo=" + tipo + ", detalle=" + detalle + ", cantidad=" + cantidad + ", costo=" + costo + ", subtotal=" + subtotal + ", saldo=" + saldoS + ", salida=" + salidaS + '}';
    }

}
