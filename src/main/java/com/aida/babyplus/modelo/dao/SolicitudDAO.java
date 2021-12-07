/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.modelo.dao;

import com.aida.babyplus.modelo.entidades.Rol;
import com.aida.babyplus.modelo.entidades.Solicitud;
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
public class SolicitudDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public SolicitudDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    public Solicitud guardar(Solicitud nuevaSolicitud) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(nuevaSolicitud);
            em.getTransaction().commit();
            return nuevaSolicitud;
        } finally {
            em.close();
        }
    }
}
