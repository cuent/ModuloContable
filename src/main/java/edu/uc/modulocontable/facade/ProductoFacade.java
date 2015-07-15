/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.facade;

import edu.uc.modulocontable.modelo2.Producto;
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
public class ProductoFacade extends AbstractFacade<Producto> {
    @PersistenceContext(unitName = "edu.uc_ModuloContable_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }
    public List<Producto> buscaProducto(int codigo) {
        Query query = this.em.createNamedQuery(Producto.findByCodigoProducto);
        query.setParameter("codigoProducto",codigo);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    public List<Producto> buscaProducto(String nombre) {
        Query query = this.em.createNamedQuery(Producto.findByNombre);
        query.setParameter("nombre",nombre);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
