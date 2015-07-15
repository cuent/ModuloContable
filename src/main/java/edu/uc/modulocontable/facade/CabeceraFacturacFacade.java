/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.facade;

import edu.uc.modulocontable.modelo2.CabeceraFacturac;
import edu.uc.modulocontable.modelo2.Cliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Juan Pablo
 */
@Stateless
public class CabeceraFacturacFacade extends AbstractFacade<CabeceraFacturac> {
    @PersistenceContext(unitName = "edu.uc_ModuloContable_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CabeceraFacturacFacade() {
        super(CabeceraFacturac.class);
    }
     public List<CabeceraFacturac> buscaxNumeroFac(String numero) {
        Query query = this.em.createNamedQuery(CabeceraFacturac.findByNumeroFactura);
        query.setParameter("numeroFactura",numero);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }  
}
