/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uc.modulocontable.domain.entity;

import edu.uc.modulocontable.services.ejb.Asiento;
import edu.uc.modulocontable.services.ejb.Cuenta;
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
public class CuentaFacade extends AbstractFacade<Cuenta> {

    @PersistenceContext(unitName = "edu.uc_ModuloContable_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CuentaFacade() {
        super(Cuenta.class);
    }

    public Long countCuentasPadreByID(Integer idcuentapadre) {
        Query q = em.createQuery("SELECT count(c) FROM Cuenta c WHERE c.idcodcuentapadre.idcodcuenta=:padre")
                .setParameter("padre", idcuentapadre);
        List<Long> resultado = q.getResultList();
//        Query q1 = em.createQuery("SELECT t FROM Transaccion t GROUP BY t.idcodcuenta");
//        q1.getResultList();
        return resultado.get(0);
    }

    public Long countCuentasPadreByID() {
        Query q = em.createQuery("SELECT count(c) FROM Cuenta c WHERE c.idcodcuentapadre is null");
        List<Long> resultado = q.getResultList();
        return resultado.get(0);
    }

    public List<Cuenta> getCuentaxNumCuentaLikeYCategoria(String numCuenta, String categoria) {
        Query query = this.em.createNamedQuery(Cuenta.findByNumeroCategoria);
        query.setParameter("numcuenta", numCuenta + '%');
        query.setParameter("categoria", categoria);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cuenta> getCuentaxNumCuenta(String numCuenta) {
        Query query = this.em.createNamedQuery(Cuenta.findByNumcuenta);
        query.setParameter("numcuenta", numCuenta);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cuenta> getCuentaxNumbreCuentaYCategoria(String nombre, String categoria) {
        Query query = this.em.createNamedQuery(Cuenta.findByNumeroCategoria);
        query.setParameter("numcuenta", nombre + '%');
        query.setParameter("categoria", categoria);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cuenta> getCuentasActivosPasivosDetalle() {
        String categoria="DETALLE";
        String tipoA="ACTIVO";
        String tipoP="PASIVO";
        Query query = em.createQuery("SELECT c FROM Cuenta c WHERE c.categoria=:categoria AND c.idtipo.nombretipo=:tipoA OR c.idtipo.nombretipo=:tipoP")
                .setParameter("categoria", categoria)
                .setParameter("tipoA", tipoA)
                .setParameter("tipoP", tipoP);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
