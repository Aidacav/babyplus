/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aida.babyplus.modelo.dao;

import com.aida.babyplus.modelo.entidades.Cita;
import com.aida.babyplus.modelo.entidades.Cliente;
import com.aida.babyplus.modelo.entidades.Solicitud;
import com.aida.babyplus.modelo.entidades.Valoracion;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
public class CitaDAO implements Serializable {

    private EntityManagerFactory emf = null;
    
    public CitaDAO() {
        this.emf = Persistence.createEntityManagerFactory("babyplusPU");
    }
    
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }
    
    public Cita buscarPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cita> cq = cb.createQuery(Cita.class);
            Root<Cita> rt = cq.from(Cita.class);
            cq.where(cb.equal(rt.get("cita"), id));
            Query q = em.createQuery(cq);
            return ((Cita) q.getSingleResult());
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    } 

    public List<Solicitud> buscarSolicitudesDeCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Solicitud> cq = cb.createQuery(Solicitud.class);
            Root<Solicitud> rt = cq.from(Solicitud.class);
            cq.where(cb.equal(rt.get("cliente").get("usuario"), id));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } finally {
            em.close();
        }
    }
    
    public List<Cita> buscarCitasPorIdSolicitudes(Set<Integer> idsSolicitudes) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cita> cq = cb.createQuery(Cita.class);
            Root<Cita> rt = cq.from(Cita.class);
            cq.where(rt.get("solicitud").get("id").in(idsSolicitudes));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } finally {
            em.close();
        }
    }

    public Valoracion guardarValoracion(Valoracion valoracion) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(valoracion);
            em.getTransaction().commit();
            return valoracion;
        } finally {
            em.close();
        }
    }
}
