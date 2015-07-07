/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.domain.entity;

import edu.uc.modulocontable.services.ejb.Transaccion;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cuent
 */
@Stateless
public class TransaccionFacade extends AbstractFacade<Transaccion> {
    @PersistenceContext(unitName = "edu.uc_ModuloContable_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransaccionFacade() {
        super(Transaccion.class);
    }
    
    public List<Transaccion> getCuentas(){
        Query query = this.em.createNamedQuery(Transaccion.groupByCuentas);
        return query.getResultList();
    }
    
    public List<Transaccion> getCuenta(Integer idcodcuenta){
        Query query = this.em.createNamedQuery(Transaccion.findByCuenta);
        query.setParameter("idcodcuenta", idcodcuenta);
        return query.getResultList();
    }
    
}
