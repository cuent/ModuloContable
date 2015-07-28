/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.facade;

import edu.uc.modulocontable.modelo2.Kardex;
import edu.uc.modulocontable.modelo2.Producto;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan Pablo
 */
@Stateless
public class KardexFacade extends AbstractFacade<Kardex> {

    @PersistenceContext(unitName = "edu.uc_ModuloContable_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KardexFacade() {
        super(Kardex.class);
    }

    public List<Kardex> query(Producto p) {
        Query q = em.createQuery("SELECT c FROM Kardex c WHERE c.codigoProducto.codigoProducto=:codProducto ORDER BY c.fecha")
                .setParameter("codProducto", p.getCodigoProducto());
        List<Kardex> resultado = q.getResultList();
        return resultado;
    }

    public List<Kardex> query(Producto p, Date f) {
        Query q = em.createQuery("SELECT c FROM Kardex c WHERE c.codigoProducto.codigoProducto=:codProducto AND c.fecha<=:fecha ORDER BY c.fecha")
                .setParameter("codProducto", p.getCodigoProducto())
                .setParameter("fecha", f);
        List<Kardex> resultado = q.getResultList();
        return resultado;
    }

}
