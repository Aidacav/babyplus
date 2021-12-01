/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.modelo.dao;

import com.aida.babyplus.modelo.entidades.Rol;
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
public class RolDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public RolDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    
    public Rol buscarPorDescripcion(String descripcion) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Rol> cq = cb.createQuery(Rol.class);
            Root<Rol> rt = cq.from(Rol.class);
            cq.where(cb.equal(rt.get("descripcion"), descripcion));
            Query q = em.createQuery(cq);
            return ((Rol) q.getSingleResult());
        } finally {
            em.close();
        }
    }
}
