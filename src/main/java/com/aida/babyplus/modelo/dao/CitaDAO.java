package com.aida.babyplus.modelo.dao;

import com.aida.babyplus.modelo.entidades.Cita;
import com.aida.babyplus.modelo.entidades.EstadoCita;
import com.aida.babyplus.modelo.entidades.EstadoSolicitud;
import com.aida.babyplus.modelo.entidades.Solicitud;
import com.aida.babyplus.modelo.entidades.Valoracion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
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
    
    public Cita buscarCitaPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cita.class, id);
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    public Solicitud buscarSolicitudPorId(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Solicitud.class, id);
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
            List<String> estadosPermitidos = new LinkedList<String>(){{
                add("ENVIADA");
                add("RECHAZADA");
            }};
            List<Predicate> predicados = new ArrayList<>();
            
            predicados.add(cb.equal(rt.get("cliente").get("usuario"), id));
            predicados.add(rt.get("estado").get("nombre").in(estadosPermitidos));

            cq.where(predicados.toArray(new Predicate[0]));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } finally {
            em.close();
        }
    }
    
    public List<Solicitud> buscarSolicitudesDeProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Solicitud> cq = cb.createQuery(Solicitud.class);
            Root<Solicitud> rt = cq.from(Solicitud.class);
            List<Predicate> predicados = new ArrayList<>();
            
            predicados.add(cb.equal(rt.get("proveedor").get("usuario"), id));
            predicados.add(cb.equal(rt.get("estado").get("nombre"), "ENVIADA"));

            cq.where(predicados.toArray(new Predicate[0]));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } finally {
            em.close();
        }
    }
    
    public List<Cita> buscarCitasPorIdCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cita> cq = cb.createQuery(Cita.class);
            Root<Cita> rt = cq.from(Cita.class);
            cq.where(cb.equal(rt.get("solicitud").get("cliente").get("usuario"), id));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } finally {
            em.close();
        }
    }
    
    public List<Cita> buscarCitasPorIdProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Cita> cq = cb.createQuery(Cita.class);
            Root<Cita> rt = cq.from(Cita.class);
            cq.where(cb.equal(rt.get("solicitud").get("proveedor").get("usuario"), id));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } catch (Exception e) {
            return new LinkedList<>();
        } finally {
            em.close();
        }
    }
    
    public void cambiarEstadoSolicitud(Integer id, String nuevoEstado) {
        EntityManager em = getEntityManager();
        try {
            Solicitud solicitud = em.find(Solicitud.class, id);
            if(solicitud != null) {
                em.getTransaction().begin();
                solicitud.setEstado(new EstadoSolicitud(nuevoEstado));
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
    
    public void cambiarEstadoCita(Integer id, String nuevoEstado) {
        EntityManager em = getEntityManager();
        try {
            Cita cita = em.find(Cita.class, id);
            if(cita != null) {
                em.getTransaction().begin();
                cita.setEstado(new EstadoCita(nuevoEstado));
                em.getTransaction().commit();
            }
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

    public Cita guardarCita(Cita cita) {
       EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cita);
            em.getTransaction().commit();
            return cita;
        } finally {
            em.close();
        }
    }

    public void finalizarCita(Integer id, String nuevoEstado, String notas) {
        EntityManager em = getEntityManager();
        try {
            Cita cita = em.find(Cita.class, id);
            if(cita != null) {
                em.getTransaction().begin();
                cita.setEstado(new EstadoCita(nuevoEstado));
                cita.setNotas(notas);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}
