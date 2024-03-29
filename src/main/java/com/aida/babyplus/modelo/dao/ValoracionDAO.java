package com.aida.babyplus.modelo.dao;

import com.aida.babyplus.modelo.entidades.Valoracion;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Aida
 */
public class ValoracionDAO implements Serializable {

    private EntityManagerFactory emf = null;

    public ValoracionDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }

    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }
    
    public List<Valoracion> buscarValoracionesDeProveedor(Integer idProveedor) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Valoracion> cq = cb.createQuery(Valoracion.class);
            Root<Valoracion> rt = cq.from(Valoracion.class);
            cq.where(cb.equal(rt.get("cita1").get("solicitud").get("proveedor").get("usuario"), idProveedor));
            cq.orderBy(cb.desc(rt.get("fecha")));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } 
        finally {
            em.close();
        }
    }
}
