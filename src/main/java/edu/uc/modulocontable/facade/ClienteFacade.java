/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.facade;

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
public class ClienteFacade extends AbstractFacade<Cliente> {
    @PersistenceContext(unitName = "edu.uc_ModuloContable_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClienteFacade() {
        super(Cliente.class);
    }
    
     public List<Cliente> buscaCliente(String cedula) {
        Query query = this.em.createNamedQuery(Cliente.findByIdentificacion);
        query.setParameter("identificacion",cedula);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<Cliente> buscaTipoIdentificador(String nombre) {
        Query query = this.em.createNamedQuery(Cliente.findByTipoIdentificacion);
        query.setParameter("tipoIdentificacion",nombre);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }  
}
