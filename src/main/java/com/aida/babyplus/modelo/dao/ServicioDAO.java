package com.aida.babyplus.modelo.dao;

import com.aida.babyplus.modelo.entidades.Servicio;
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
public class ServicioDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public ServicioDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public List<Servicio> buscarTodos() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Servicio> cq = cb.createQuery(Servicio.class);
            Root<Servicio> rt = cq.from(Servicio.class);
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
