/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.domain.entity;

import edu.uc.modulocontable.services.ejb.Asiento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cuent
 */
@Stateless
public class AsientoFacade extends AbstractFacade<Asiento> {
    @PersistenceContext(unitName = "edu.uc_ModuloContable_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsientoFacade() {
        super(Asiento.class);
    }
    
    public void test(){
        Query q=em.createQuery("SELECT a FROM Asiento a");
        Query q1=em.createQuery("SELECT c FROM Asiento c LEFT OUTER JOIN FETCH c.transaccionList");
        List<Asiento> asientos=q.getResultList();
        
    }
    public List<Asiento> AsientoxNumeroAsiento(int numero) {

        Query query = this.em.createNamedQuery(Asiento.findByNumasiento);
        query.setParameter("numasiento", numero);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
