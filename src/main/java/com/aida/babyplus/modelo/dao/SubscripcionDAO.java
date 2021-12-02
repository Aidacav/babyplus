/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.modelo.dao;

import com.aida.babyplus.modelo.entidades.Subscripcion;
import java.io.Serializable;
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
public class SubscripcionDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public SubscripcionDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public Subscripcion buscarPorNombre(String nombre) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Subscripcion> cq = cb.createQuery(Subscripcion.class);
            Root<Subscripcion> rt = cq.from(Subscripcion.class);
            cq.where(cb.equal(rt.get("nombre"), nombre.toUpperCase()));
            Query q = em.createQuery(cq);
            return ((Subscripcion) q.getSingleResult());
        } catch (Exception e) {
            return null;
        } 
        finally {
            em.close();
        }
    }
}
