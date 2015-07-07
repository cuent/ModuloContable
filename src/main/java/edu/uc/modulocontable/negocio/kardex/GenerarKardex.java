/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.negocio.kardex;

import edu.uc.modulocontable.modelo2.Kardex;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author cuent
 */
public class GenerarKardex {

    private List<Kardex> movimientos;
    private Stack<Inventario> inventarioLIFO = new Stack<>();
    private Queue<Inventario> inventarioFIFO = new LinkedList<>();
    private Inventario inventarioPonderado = new Inventario(0, 0, 0);
    private List<KardexFIFO> inventarioTotalFIFO = new ArrayList();
    private List<KardexLIFO> inventarioTotalLIFO = new ArrayList();
    private List<KardexPonderado> inventarioTotalPonderado = new ArrayList();

    public GenerarKardex(List<Kardex> movimientos) {
        this.movimientos = movimientos;
    }

    public Inventario getInventarioPonderado() {
        return inventarioPonderado;
    }

    public void setInventarioPonderado(Inventario inventarioPonderado) {
        this.inventarioPonderado = inventarioPonderado;
    }

    public List<KardexPonderado> getInventarioTotalPonderado() {
        return inventarioTotalPonderado;
    }

    public void setInventarioTotalPonderado(List<KardexPonderado> inventarioTotalPonderado) {
        this.inventarioTotalPonderado = inventarioTotalPonderado;
    }

    public Queue<Inventario> getInventarioFIFO() {
        return inventarioFIFO;
    }

    public List<KardexLIFO> getInventarioTotalLIFO() {
        return inventarioTotalLIFO;
    }

    public void setInventarioTotalLIFO(List<KardexLIFO> inventarioTotalLIFO) {
        this.inventarioTotalLIFO = inventarioTotalLIFO;
    }

    public void setInventarioFIFO(Queue<Inventario> inventarioFIFO) {
        this.inventarioFIFO = inventarioFIFO;
    }

    public Stack<Inventario> getInventarioLIFO() {
        return inventarioLIFO;
    }

    public void setInventarioLIFO(Stack<Inventario> inventarioLIFO) {
        this.inventarioLIFO = inventarioLIFO;
    }

    public List<Kardex> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Kardex> movimientos) {
        this.movimientos = movimientos;
    }

    public List<KardexFIFO> getInventarioTotalFIFO() {
        return inventarioTotalFIFO;
    }

    public void setInventarioTotalFIFO(List<KardexFIFO> inventarioTotalFIFO) {
        this.inventarioTotalFIFO = inventarioTotalFIFO;
    }

    public void generarLIFO() {
        KardexLIFO fifo = null;
        Inventario inventario;
        for (Kardex movimiento : movimientos) {
            List<SalidaKardex> salidas = new ArrayList();
            if (movimiento.getTipo().equalsIgnoreCase("entrada")) {
                inventarioLIFO.push(new Inventario(movimiento.getCantidad(), movimiento.getCosto(), movimiento.getSubtotal()));
                Stack<Inventario> i = cambiarPila(inventarioLIFO);
                fifo = new KardexLIFO(movimiento.getFecha(), movimiento.getTipo(), movimiento.getDetalle(), movimiento.getCantidad(), movimiento.getCosto(), movimiento.getSubtotal(), i, null);
            } else if (movimiento.getTipo().equalsIgnoreCase("salida")) {
                inventario = inventarioLIFO.pop();
                if (movimiento.getCantidad() == inventario.getTotalCantidad()) {
                    salidas.add(new SalidaKardex(inventario.getTotalCantidad(), inventario.getTotalCosto(), inventario.getTotalSubtotal()));
                } else if (movimiento.getCantidad() < inventario.getTotalCantidad()) {
                    inventario.setTotalCantidad(inventario.getTotalCantidad() - movimiento.getCantidad());
                    inventario.setTotalSubtotal(inventario.getTotalCosto() * inventario.getTotalCantidad());
                    salidas.add(new SalidaKardex(movimiento.getCantidad(), inventario.getTotalCosto(), movimiento.getCantidad() * inventario.getTotalCosto()));
                    inventarioLIFO.add(0, inventario);
                } else if (movimiento.getCantidad() > inventario.getTotalCantidad()) {
                    int unidades = movimiento.getCantidad() - inventario.getTotalCantidad();
                    salidas.add(new SalidaKardex(inventario.getTotalCantidad(), inventario.getTotalCosto(), inventario.getTotalSubtotal()));
                    while (true) {
                        if (unidades > 0) {
                            inventario = inventarioLIFO.pop();
                            if (unidades <= inventario.getTotalCantidad()) {
                                salidas.add(new SalidaKardex(unidades, inventario.getTotalCosto(), unidades * inventario.getTotalCosto()));
                            } else {
                                salidas.add(new SalidaKardex(inventario.getTotalCantidad(), inventario.getTotalCosto(), inventario.getTotalSubtotal()));
                            }
                            unidades -= inventario.getTotalCantidad();
                        } else if (unidades < 0) {
                            unidades *= -1;
                            inventario.setTotalCantidad(unidades);
                            inventario.setTotalSubtotal(inventario.getTotalCosto() * inventario.getTotalCantidad());
                            inventarioLIFO.add(0, inventario);
                            break;
                        } else if (unidades == 0) {
                            break;
                        }
                    }
                }
                Stack<Inventario> i = cambiarPila(inventarioLIFO);
                fifo = new KardexLIFO(movimiento.getFecha(), movimiento.getTipo(), movimiento.getDetalle(), movimiento.getCantidad(), movimiento.getCosto(), movimiento.getSubtotal(), i, salidas);
            }
            inventarioTotalLIFO.add(fifo);
        }
    }

    public void generarFIFO() {
        KardexFIFO fifo = null;
        Inventario inventario;
        for (Kardex movimiento : movimientos) {
            List<SalidaKardex> salidas = new ArrayList();
            if (movimiento.getTipo().equalsIgnoreCase("entrada")) {
                inventarioFIFO.add(new Inventario(movimiento.getCantidad(), movimiento.getCosto(), movimiento.getSubtotal()));
                Queue<Inventario> i = cambiarCola(inventarioFIFO);
                fifo = new KardexFIFO(movimiento.getFecha(), movimiento.getTipo(), movimiento.getDetalle(), movimiento.getCantidad(), movimiento.getCosto(), movimiento.getSubtotal(), i, null);
            } else if (movimiento.getTipo().equalsIgnoreCase("salida")) {
                inventario = inventarioFIFO.poll();
                if (movimiento.getCantidad() == inventario.getTotalCantidad()) {
                    salidas.add(new SalidaKardex(inventario.getTotalCantidad(), inventario.getTotalCosto(), inventario.getTotalSubtotal()));
                } else if (movimiento.getCantidad() < inventario.getTotalCantidad()) {
                    inventario.setTotalCantidad(inventario.getTotalCantidad() - movimiento.getCantidad());
                    inventario.setTotalSubtotal(inventario.getTotalCosto() * inventario.getTotalCantidad());
                    salidas.add(new SalidaKardex(movimiento.getCantidad(), inventario.getTotalCosto(), movimiento.getCantidad() * inventario.getTotalCosto()));
                    inventarioFIFO.add(inventario);
                } else if (movimiento.getCantidad() > inventario.getTotalCantidad()) {
                    int unidades = movimiento.getCantidad() - inventario.getTotalCantidad();
                    salidas.add(new SalidaKardex(inventario.getTotalCantidad(), inventario.getTotalCosto(), inventario.getTotalSubtotal()));
                    while (true) {
                        if (unidades > 0) {
                            inventario = inventarioFIFO.poll();
                            if (unidades <= inventario.getTotalCantidad()) {
                                salidas.add(new SalidaKardex(unidades, inventario.getTotalCosto(), unidades * inventario.getTotalCosto()));
                            } else {
                                salidas.add(new SalidaKardex(inventario.getTotalCantidad(), inventario.getTotalCosto(), inventario.getTotalSubtotal()));
                            }
                            unidades -= inventario.getTotalCantidad();
                        } else if (unidades < 0) {
                            unidades *= -1;
                            inventario.setTotalCantidad(unidades);
                            inventario.setTotalSubtotal(inventario.getTotalCosto() * inventario.getTotalCantidad());
                            inventarioFIFO.add(inventario);
                            break;
                        } else if (unidades == 0) {
                            break;
                        }
                    }
                }
                Queue<Inventario> i = cambiarCola(inventarioFIFO);
                fifo = new KardexFIFO(movimiento.getFecha(), movimiento.getTipo(), movimiento.getDetalle(), movimiento.getCantidad(), movimiento.getCosto(), movimiento.getSubtotal(), i, salidas);
            }
            inventarioTotalFIFO.add(fifo);
        }
    }

    private Stack<Inventario> cambiarPila(Stack<Inventario> i) {
        Stack<Inventario> inv = new Stack<>();
        Inventario inventarioAux;
        for (Inventario i1 : i) {
            inventarioAux = new Inventario(i1.getTotalCantidad(), i1.getTotalCosto(), i1.getTotalSubtotal());
            inv.push(inventarioAux);
        }
        return inv;
    }

    private Queue<Inventario> cambiarCola(Queue<Inventario> i) {
        Queue<Inventario> inv = new LinkedList<>();
        Inventario inventarioAux;
        for (Inventario i1 : i) {
            inventarioAux = new Inventario(i1.getTotalCantidad(), i1.getTotalCosto(), i1.getTotalSubtotal());
            inv.add(inventarioAux);
        }
        return inv;
    }

    public void generarPonderado() {
        KardexPonderado ponderado = null;
        for (Kardex movimiento : movimientos) {
            if (movimiento.getTipo().equalsIgnoreCase("entrada")) {
                inventarioPonderado.setTotalCantidad(inventarioPonderado.getTotalCantidad() + movimiento.getCantidad());
                inventarioPonderado.setTotalSubtotal(inventarioPonderado.getTotalSubtotal() + movimiento.getSubtotal());
                inventarioPonderado.setTotalCosto(inventarioPonderado.getTotalSubtotal() / inventarioPonderado.getTotalCantidad());
                Inventario i = new Inventario(inventarioPonderado.getTotalCantidad(), inventarioPonderado.getTotalCosto(), inventarioPonderado.getTotalSubtotal());
                ponderado = new KardexPonderado(movimiento.getFecha(), movimiento.getTipo(), movimiento.getDetalle(), movimiento.getCantidad(), movimiento.getCosto(), movimiento.getSubtotal(), i);

            } else if (movimiento.getTipo().equalsIgnoreCase("salida")) {
                movimiento.setCosto(inventarioPonderado.getTotalCosto());
                movimiento.setSubtotal(movimiento.getCantidad() * movimiento.getCosto());
                inventarioPonderado.setTotalCantidad(inventarioPonderado.getTotalCantidad() - movimiento.getCantidad());
                inventarioPonderado.setTotalSubtotal(inventarioPonderado.getTotalSubtotal() - movimiento.getSubtotal());
                inventarioPonderado.setTotalCosto(inventarioPonderado.getTotalSubtotal() / inventarioPonderado.getTotalCantidad());
                Inventario i = new Inventario(inventarioPonderado.getTotalCantidad(), inventarioPonderado.getTotalCosto(), inventarioPonderado.getTotalSubtotal());
                ponderado = new KardexPonderado(movimiento.getFecha(), movimiento.getTipo(), movimiento.getDetalle(), movimiento.getCantidad(), movimiento.getCosto(), movimiento.getSubtotal(), i);

            }
            inventarioTotalPonderado.add(ponderado);
        }
    }

}
